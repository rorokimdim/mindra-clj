(ns mindra.examples.gloss
  (:require

   [mindra.core :refer [gloss-draw gloss-play]]
   [mindra.gloss :as mg]
   [mindra.examples.common :as c]))

(defn draw-basic []
  (let [circles (mg/pictures
                 (mg/translate -200 200 (mg/circle 20))
                 (mg/translate -150 200 (mg/circle-solid 20))
                 (mg/translate -100 200 (mg/thick-circle 20 8))
                 (mg/translate -50 200 (mg/color 0 255 0 255 (mg/circle 20)))
                 (mg/translate 0 200 (mg/color 0 255 0 255 (mg/circle-solid 20)))
                 (mg/translate 50 200 (mg/color 0 255 0 255 (mg/thick-circle 20 8))))
        rectangles (mg/pictures
                    (mg/translate -200 150 (mg/rectangle-wire 20 20))
                    (mg/translate -150 150 (mg/rectangle-solid 20 20))
                    (mg/translate -100 150 (mg/rectangle-upper-solid 20 20))
                    (mg/translate -50 150 (mg/rectangle-upper-wire 20 20))
                    (mg/translate 0 150 (mg/color 0 0 255 255 (mg/rectangle-wire 20 20)))
                    (mg/translate 50 150 (mg/color 255 0 0 255 (mg/rectangle-solid 20 20))))
        arcs (mg/pictures
              (mg/translate -200 100 (mg/arc 0 90 20))
              (mg/translate -150 100 (mg/arc-solid 0 90 20))
              (mg/translate -100 100 (mg/thick-arc 0 90 20 2))
              (mg/translate -50 100 (mg/sector-wire 30 90 20))
              (mg/translate 0 100 (mg/color 0 0 100 100 (mg/sector-wire 180 270 20)))
              (mg/translate 50 100 (mg/color 0 0 100 100 (mg/arc-solid 270 360 20))))
        lines (mg/pictures
               (mg/translate -200 50 (mg/line [0 0] [0 20]))
               (mg/translate -150 50 (mg/line [0 0] [10 10] [20 0]))
               (mg/translate -100 50 (mg/line-loop [0 0] [10 10] [20 0]))
               (mg/translate -50 50 (mg/color 100 100 100 100 (mg/line [0 0] [0 20])))
               (mg/translate 0 50 (mg/color 100 0 0 100 (mg/line [0 0] [10 10] [20 0])))
               (mg/translate 50 50 (mg/color 100 100 0 100 (mg/line [0 0] [20 0]))))
        polygons (mg/pictures
                  (mg/translate -200 -50 (mg/polygon [0 0] [0 40] [40 40] [40 0]))
                  (mg/translate -150 -50 (mg/rotate 30 (mg/polygon [0 0] [0 40] [40 40] [40 0])))
                  (mg/translate -50 -50 (mg/color
                                         91 121 100 200
                                         (apply mg/polygon (for [x (range 200)]
                                                             [x (mod (* x x) 40)])))))
        texts (mg/pictures
               (mg/translate -200 -100 (mg/scale 0.125 0.125 (mg/text "HELLO")))
               (mg/translate -100 -100 (mg/scale
                                        0.125 0.125
                                        (mg/color 91 200 100 100 (mg/text "GLOSS")))))
        images (mg/pictures
                (mg/translate 0 -250 (mg/bitmap "images/dumbmanex.com/grass.png"))
                (mg/translate 0 -120 (mg/bitmap-section
                                      10 10 60 60
                                      "images/dumbmanex.com/snow-baller.png")))]
    (gloss-draw (mg/pictures circles
                             rectangles
                             arcs
                             lines
                             polygons
                             texts
                             images)
                :window {:width 512 :height 512})))

(defn draw-hilbert-pattern
  ([] (draw-hilbert-pattern 6))
  ([n]
   (let [width 500
         height 500]
     (as-> (c/hilbert-pattern 0 0 width 0 0 height n) $
       (apply mg/line $)
       (mg/translate (/ (- width) 2) (/ (- height) 2) $)
       (gloss-draw $ :window {:width (+ width 20) :height (+ height 20)})))))

(defn draw-circle-pattern []
  (let [width 500
        height 500]
    (as-> (c/circle-pattern 0 0 200) $
      (map (fn [[x y r]] (mg/translate x y (mg/circle r))) $)
      (apply mg/pictures $)
      (mg/color 100 10 0 100 $)
      (gloss-draw $ :window {:width width :height height}))))

(defn draw-line-pattern []
  (let [width 500
        height 500]
    (as-> (c/line-pattern 0 0 500 100) $
      (map (fn [[p1 p2 _p3 p4 p5 _p6]]
             (mg/pictures
              (mg/line p1 p2)
              (mg/line p1 p5)
              (mg/line p2 p4))) $)
      (apply mg/pictures $)
      (mg/translate (/ (- width) 2) (/ (- height) 2) $)
      (mg/color 100 10 0 200 $)
      (gloss-draw $ :window {:width (+ width 50) :height (+ height 50)}))))

(defn draw-square-pattern []
  (let [width 500
        height 500]
    (as-> (c/square-pattern 0 0 (/ width 2.0)) $
      (map (fn [[x y n]] (mg/translate x y (mg/rectangle-wire n n))) $)
      (apply mg/pictures $)
      (gloss-draw $ :window {:width (+ width 50) :height (+ height 50)}))))

(defn draw-tree-pattern []
  (let [width 500
        height 500]
    (as-> (c/tree-pattern 0 -400 200 50 50) $
      (map (fn [[p0 p1 p2 p3]]
             (mg/pictures
              (mg/line p0 p1)
              (mg/line p1 p3)
              (mg/line p1 p2))) $)
      (apply mg/pictures $)
      (mg/color 100 10 0 200 $)
      (gloss-draw $ :window {:width (+ width 50) :height (+ height 50)}))))

(defn play-with-events []
  (let [world (atom {:radius 10 :x 0 :y 0})
        on-step (fn [world _seconds]
                  (swap! world update-in [:radius]
                         (fn [r] (mod (inc r) 200))))
        on-event (fn [world event]
                   (let [ename (:name event)
                         eargs (:args event)]
                     (if (= ename "EventMotion")
                       (swap! world assoc :x (first eargs) :y (second eargs))
                       (println event))))
        world->picture (fn [world]
                         (let [w @world]
                           (->> (:radius w)
                                (mg/circle-solid)
                                (mg/translate (:x w) (:y w))
                                (mg/color 0 0 255 100))))]
    (gloss-play world
                :on-step on-step
                :on-event on-event
                :world->picture world->picture)))

(defn play-snow-baller []
  (let [snow-baller-img "images/dumbmanex.com/snow-baller.png"
        grass-img "images/dumbmanex.com/grass.png"
        world (atom {:width 70
                     :height 65
                     :i 0
                     :n 11})
        sprite-map {0 [17 8]
                    1 [94 6]
                    2 [169 5]
                    3 [243 6]
                    4 [318 2]
                    5 [388 4]
                    6 [456 0]
                    7 [522 7]
                    8 [599 10]
                    9 [673 10]
                    10 [748 10]}
        on-step (fn [w _seconds]
                  (let [w' @w
                        i (:i w')
                        n (:n w')
                        ni (mod (inc i) n)]
                    (swap! w assoc :i ni)))
        world->picture (fn [world]
                         (let [w @world
                               i (:i w)
                               [x y] (sprite-map i)
                               width (:width w)
                               height (:height w)]
                           (mg/pictures
                            (mg/translate
                             100 -180
                             (mg/bitmap-section
                              x y width height
                              snow-baller-img))
                            (mg/translate
                             100 -250
                             (mg/bitmap-section
                              0 0 800 100
                              grass-img)))))]
    (gloss-play world
                :steps-per-second 2
                :on-step on-step
                :no-event true
                :world->picture world->picture)))

(defn play-hilbert-pattern []
  (let [max-n 7
        width 500
        height 500
        memoized-hilbert-pattern (memoize c/hilbert-pattern)
        on-step (fn [world _seconds]
                  (let [w @world
                        n (:n w)
                        delta (cond
                            (>= n max-n) -1
                            (<= n 1) 1
                            :else (:delta w))]
                    (swap! world assoc
                           :n (+ n delta)
                           :delta delta)))
        world->picture (fn [world]
                         (->> (memoized-hilbert-pattern 0 0 width 0 0 height (:n @world))
                              (apply mg/line)
                              (mg/color 100 20 20 100)
                              (mg/translate (/ (- width) 2) (/ (- height) 2))))]
    (gloss-play (atom {:n 1 :delta 1})
                :steps-per-second 5
                :no-event true
                :on-step on-step
                :world->picture world->picture
                :window {:width (+ width 20) :height (+ height 20)})))
