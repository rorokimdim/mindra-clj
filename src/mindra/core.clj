(ns mindra.core
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.string :as s]

            [babashka.process :as bp]))

(def MINDRA-MODE-GLOSS-STATIC 0)
(def MINDRA-MODE-GLOSS-INTERACTIVE 1)

(defn print-and-raise-mindra-failure [tag body]
  (println body)
  (throw (AssertionError.
          (str "Unexpected message received from mindra, with tag '" tag "'."))))

(defn read-message [^java.io.BufferedReader reader]
  (let [line (.readLine reader)]
    (if (nil? line)
      {}
      (if (empty? line)
        (read-message reader)
        (let [cleaned-line (s/replace line "\\n" "\n")
              splits (s/split cleaned-line #"\s+" 2)
              tag (first splits)
              body (second splits)]
          {:tag tag :body body})))))

(defn write-message
  ([^java.io.BufferedWriter writer tag] (write-message writer tag nil))
  ([^java.io.BufferedWriter writer tag body]
   (if (nil? body)
     (.write writer ^String tag)
     (.write writer (str tag " " body)))
   (.write writer (str \newline \newline))
   (.flush writer)))

(defn default-exit-event? [event]
  (and (= (:name event) "EventKey")
       (= (first (:args event)) :specialKeyEsc)))

(defn parse-event [message-body]
  (let [splits (s/split message-body #"\s+")
        event-name (first splits)
        event-args (map edn/read-string (rest splits))]
    {:name event-name
     :args event-args}))

(defn handle-event [writer configuration world event]
  (if ((:exit-event? configuration) event)
    (write-message writer "SHUTDOWN")
    (do
      ((:on-event configuration) world event)
      (write-message writer "OK"))))

(defn handle-step [writer configuration world message-body]
  (let [seconds (Float/parseFloat message-body)]
    ((:on-step configuration) world seconds)
    (write-message writer "OK")))

(defn handle-picture-request [writer configuration world]
  (let [picture ((:world->picture configuration) world)]
    (write-message writer "PICTURE" picture)))

(defn handle-svg-request [writer configuration svg]
  (write-message writer "SVG" svg))

(defn handle-svg-init-request [writer configuration]
  (let [width (:width configuration)
        height (:height configuration)]
    (write-message writer
                   "INIT"
                   (s/join
                    " "
                    (list "Diagrams" "SVG" width height)))))

(defn handle-gloss-init-request [writer configuration]
  (let [mode (:mode configuration MINDRA-MODE-GLOSS-STATIC)
        window (:window configuration)
        color (:background-color configuration)
        steps-per-second (:steps-per-second configuration 50)
        no-event (:no-event configuration)
        no-step (:no-step configuration)]
    (write-message writer
                   "INIT"
                   (s/join
                    " "
                    (list "Gloss"
                          "Mode"
                          mode
                          "Window"
                          (:width window 512)
                          (:height window 512)
                          (:x window 10)
                          (:y window 10)
                          (str "\"" (:title window "Mindra") "\"")
                          "Color"
                          (:red color 255)
                          (:green color 255)
                          (:blue color 255)
                          (:alpha color 255)
                          "StepsPerSecond"
                          steps-per-second
                          (when no-event "NoEvent")
                          (when no-step "NoStep"))))))

(defn handle-shutdown [writer configuration world]
  ((:on-exit configuration) world)
  (write-message writer "OK"))

(defn handle-gloss-ready [writer configuration world message-body]
  (case message-body
    "INIT" (handle-gloss-init-request writer configuration)
    "PICTURE" (handle-picture-request writer configuration world)
    (throw (AssertionError. (str "Unexpected READY message received: " message-body)))))

(defn handle-svg-ready [writer configuration svg message-body]
  (case message-body
    "INIT" (handle-svg-init-request writer configuration)
    "SVG" (handle-svg-request writer configuration svg)
    (throw (AssertionError. (str "Unexpected READY message received: " message-body)))))

(defn start-mindra-or-fail [path]
  (try
    (bp/process [path] {:shutdown bp/destroy})
    (catch java.io.IOException _
      (do
        (println (str "😱 Failed to start mindra from path '" path "'"))
        (println (str \newline
                      "Please install mindra and provide the correct path to it using :mindra-path option -- defaults to 'mindra'."))
        (System/exit 1)))))

(defn diagram->svg [diagram & {:keys [mindra-path
                                      width
                                      height]
                               :or {mindra-path "mindra"
                                    width 512
                                    height 512}}]
  (let [configuration {:mindra-path mindra-path
                       :width width
                       :height height}
        p (start-mindra-or-fail mindra-path)
        reader (io/reader (:out p))
        writer (io/writer (:in p))]
    (loop [message (read-message reader)]
      (let [tag (:tag message)
            message-body (:body message)]
        (case tag
          nil true
          "READY" (handle-svg-ready writer configuration diagram message-body)
          "SVG" true
          (print-and-raise-mindra-failure tag message-body))
        (if (and (not= tag "SVG") (.isAlive ^java.lang.Process (:proc p)))
          (recur (read-message reader))
          message-body)))))

(defn gloss-draw [picture
                  & {:as provided-opts}]
  (let [default-configuration {:mindra-path "mindra"
                               :window {:width 512
                                        :height 512
                                        :x 10
                                        :y 10
                                        :title "Mindra"}
                               :background-color {:red 255
                                                  :green 255
                                                  :blue 255
                                                  :alpha 255}
                               :world->picture (constantly picture)
                               :mode MINDRA-MODE-GLOSS-STATIC
                               :no-event false
                               :no-step false}
        configuration (merge default-configuration provided-opts)
        p (start-mindra-or-fail (:mindra-path configuration))
        reader (io/reader (:out p))
        writer (io/writer (:in p))
        world (atom {})]
    (loop [message (read-message reader)]
      (let [tag (:tag message)
            message-body (:body message)]
        (case tag
          nil true
          "READY" (handle-gloss-ready writer configuration world message-body)
          (print-and-raise-mindra-failure tag message-body))
        (when (and (not= message-body "PHOTO") (.isAlive ^java.lang.Process (:proc p)))
          (recur (read-message reader)))))))

(defn gloss-play [world & {:as provided-opts}]
  (let [default-configuration {:mindra-path "mindra"
                               :on-step (constantly true)
                               :on-event #(println %2)
                               :on-exit (constantly true)
                               :exit-event? default-exit-event?
                               :world->picture (constantly (str "RectangleWire 100.0 100.0"))
                               :window {:width 512
                                        :height 512
                                        :x 10
                                        :y 10
                                        :title "Mindra"}
                               :background-color {:red 255
                                                  :green 255
                                                  :blue 255
                                                  :alpha 255}
                               :steps-per-second 50
                               :mode MINDRA-MODE-GLOSS-INTERACTIVE}
        configuration (merge default-configuration provided-opts)
        p (start-mindra-or-fail (:mindra-path configuration))
        reader (io/reader (:out p))
        writer (io/writer (:in p))]
    (loop [message (read-message reader)]
      (let [tag (:tag message)
            message-body (:body message)]
        (case tag
          nil true
          "READY" (handle-gloss-ready writer configuration world message-body)
          "STEP" (handle-step writer configuration world message-body)
          "EVENT" (handle-event writer configuration world (parse-event message-body))
          "SHUTDOWN" (handle-shutdown writer configuration world)
          (do
            (write-message writer "SHUTDOWN")
            (handle-shutdown writer configuration world)
            (print-and-raise-mindra-failure tag message-body)))
        (when (and (not= tag "SHUTDOWN") (.isAlive ^java.lang.Process (:proc p)))
          (recur (read-message reader)))))))
