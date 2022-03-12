(ns mindra.examples.diagrams
  (:require

   [mindra.core :refer [diagram->svg]]
   [mindra.diagrams :as md]
   [mindra.examples.common :as c]))

(def default-svg-path "diagram.svg")

(defn write-svg
  ([svg] (write-svg default-svg-path svg))
  ([file-path svg]
   (spit file-path svg)
   (println (str "Written to " file-path))))

(defn draw-basic
  ([] (draw-basic default-svg-path))
  ([file-path]
   (let [tiny-circle (->> (md/circle 2.5)
                          (md/fill-color 0 100 0 100))
         little-circle (->> (md/circle 5)
                            (md/fill-color 100 0 0 100))
         big-circle (->> (md/circle 10)
                         (md/fill-color 0 0 100 100))
         tiny-square (md/rectangle 2.5 2.5)
         little-square (md/rectangle 5 5)
         big-square (md/rectangle 10 10)
         circles (md/cat 1 0 [tiny-circle little-circle big-circle])
         squares (md/hcat [tiny-square little-square big-square])
         structure (md/beside 1 0 circles squares)
         svg (diagram->svg (->> structure
                                (md/rotate -45)
                                (md/background-frame 2 255 255 255)))]
     (write-svg file-path svg))))

(defn draw-circle
  ([] (draw-circle default-svg-path))
  ([file-path]
   (let [svg (diagram->svg (->> (md/circle 50)
                                (md/fill-color 200 0 0 250)
                                (md/line-color 0 200 0 250)
                                (md/line-width-g 1)
                                (md/show-origin)))]
     (write-svg file-path svg))))

(defn draw-circle-pattern
  ([] (draw-circle-pattern default-svg-path))
  ([file-path]
   (let [width 500
         height 500
         svg (as-> (c/circle-pattern 0 0 200) $
               (map (fn [[x y r]]
                      (->> (md/circle r)
                           (md/line-color 100 10 0 100)
                           (md/translate x y))) $)
               (apply md/superimpose $)
               (diagram->svg $ :width 500 :height 500))]
     (write-svg file-path svg))))

(defn draw-hilbert-pattern
  ([] (draw-hilbert-pattern 6))
  ([n] (draw-hilbert-pattern n default-svg-path))
  ([n file-path]
   (let [width 500
         height 500
         svg (as-> (c/hilbert-pattern 0 0 width 0 0 height n) $
               (apply md/line $)
               (md/line-color 100 10 0 200 $)
               (md/translate (/ (- width) 2) (/ (- height) 2) $)
               (diagram->svg $ :window {:width (+ width 20) :height (+ height 20)}))]
     (write-svg file-path svg))))

(defn draw-line-pattern
  ([] (draw-line-pattern default-svg-path))
  ([file-path]
   (let [width 500
         height 500
         svg (as-> (c/line-pattern 0 0 500 100) $
               (map (fn [[p1 p2 _p3 p4 p5 _p6]]
                      (md/superimpose
                       (md/line p1 p2)
                       (md/line p1 p5)
                       (md/line p2 p4))) $)
               (apply md/superimpose $)
               (md/translate (/ (- width) 2) (/ (- height) 2) $)
               (md/line-color 100 10 0 200 $)
               (diagram->svg $ :window {:width (+ width 50) :height (+ height 50)}))]
     (write-svg file-path svg))))

(defn draw-square-pattern
  ([] (draw-square-pattern default-svg-path))
  ([file-path]
   (let [width 500
         height 500
         svg (as-> (c/square-pattern 0 0 (/ width 2.0)) $
               (map (fn [[x y n]]
                      (->> (md/rectangle n n)
                           (md/translate x y)
                           (md/line-color 0 0 200 200))) $)
               (apply md/superimpose $)
               (diagram->svg $ :window {:width (+ width 50) :height (+ height 50)}))]
     (write-svg file-path svg))))

(defn draw-tree-pattern
  ([] (draw-tree-pattern default-svg-path))
  ([file-path]
   (let [width 500
         height 500
         svg (as-> (c/tree-pattern 0 -400 200 50 50) $
               (map (fn [[p0 p1 p2 p3]]
                      (md/superimpose
                       (md/line p0 p1)
                       (md/line p1 p3)
                       (md/line p1 p2))) $)
               (apply md/superimpose $)
               (md/line-color 100 10 0 200 $)
               (diagram->svg $ :window {:width (+ width 50) :height (+ height 50)}))]
     (write-svg file-path svg))))

(defn draw-rectangle
  ([] (draw-rectangle default-svg-path))
  ([file-path]
   (let [svg (diagram->svg (->> (md/rectangle 100 50)
                                (md/fill-color 0 0 200 250)
                                (md/line-color 0 200 0 250)
                                (md/line-width-g 1)
                                (md/dashing-g [5 1] 0)
                                (md/rotate 15)
                                (md/show-origin)
                                (md/show-envelope)))]
     (write-svg file-path svg))))

(defn draw-cubic-spline
  ([] (draw-cubic-spline false))
  ([closed?] (draw-cubic-spline default-svg-path closed?))
  ([file-path closed?]
   (let [svg (diagram->svg (->> [[0 0] [5 5] [10 0] [15 15]]
                                (md/cubic-spline closed?)
                                (md/line-color 150 0 0 200)))]
     (write-svg file-path svg))))

(defn draw-bspline
  ([] (draw-bspline false))
  ([closed?] (draw-bspline default-svg-path closed?))
  ([file-path closed?]
   (let [svg (diagram->svg (->> [[0 0] [5 5] [10 0] [15 15] [20 0] [30 30]]
                                (md/bspline)
                                (md/line-color 150 0 0 200)))]
     (write-svg file-path svg))))

(defn draw-plot
  ([] (draw-plot default-svg-path
                 (for [x (range (* -3 Math/PI) (* 3 Math/PI) 0.4)
                       :let [y (* 5 (Math/sin x))]]
                   [x y])))
  ([points] (draw-plot default-svg-path points))
  ([file-path points]
   (let [marker (->> (md/circle 0.2)
                     (md/line-color 0 100 0 200)
                     (md/fill-color 80 0 0 250)
                     (md/line-width-g 0.1))
         markers (repeat (count points) marker)
         svg (diagram->svg (->> points
                                (md/position markers)
                                (md/superimpose (md/cubic-spline false points))))]
     (write-svg file-path svg))))
