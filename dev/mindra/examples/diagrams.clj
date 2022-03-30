(ns mindra.examples.diagrams
  (:require
   [mindra.core :as m]
   [mindra.diagrams :as md]
   [mindra.examples.common :as c]))

(defn write-svg
  "Writes a diagram to a SVG file."
  [file-path diagram]
  (spit file-path (m/diagram->svg diagram)))

(defn construct-basic
  "Constructs a basic diagram with shapes and texts.

  To create a svg file: (write-svg file-path (construct-basic))."
  []
  (let [circles (md/hsep 2
                         [(md/text "Circles")
                          (md/hsep 1 (map md/circle (range 1 3 0.5)))])
        squares (md/hsep 2
                         [(md/text "Squares")
                          (md/hsep 1 (map md/square (range 1 3 0.5)))])
        hrules (md/hsep 1 (map md/hrule (range 1 3 0.25)))
        vrules (md/hsep 1 (map md/vrule (range 1 3 0.25)))
        lines (md/hsep 2
                       [(md/text "Lines")
                        (md/vsep 1 [hrules vrules])])
        ellipses (md/hsep 2
                          [(md/text "Ellipses")
                           (md/hsep 1 (map md/ellipse (range 0.0 1.0 0.2)))])
        shapes (md/vsep 2 [circles squares lines ellipses])
        large-text (->> (md/text "Large Text")
                        (md/font-size 3))
        combined (md/vsep 2 (map md/center-x [shapes large-text]))]
    (->> combined
         md/center-xy
         (md/pad 1.25 1))))

(defn construct-arrows
  "Constructs a diagram with various styles of arrows.

  To create a svg file: (write-svg file-path (construct-arrows))."
  []
  (let [c1 (md/named "c1" (md/circle 1))
        c2 (md/named "c2" (md/circle 1))
        a0 (md/arrow-connect "c1" "c2" (md/hsep 10 [c1 c2]))
        a1 (md/arrow-connect-outside "c1" "c2" (md/hsep 10 [c1 c2]))
        a2 (md/arrow-connect-perim "c1" "c2" 30 200 (md/hsep 10 [c1 c2]))
        a3 (->> (md/construct-arrow-options
                 :head-type "spike"
                 :tail-type "quill"
                 :head-color {:red 200}
                 :tail-color {:green 200}
                 :shaft-points [[0 0] [1 2] [3 4]])
                (md/arrow-between [0 0] [12 0])
                (md/line-color 0 0 200 200))
        a4 (->> (md/construct-arrow-options
                 :head-type "thorn"
                 :tail-type "block"
                 :head-color {:red 200}
                 :tail-color {:green 200}
                 :shaft-points [[0 0] [3 0] [8 2]])
                (md/arrow-between [0 0] [12 0])
                (md/line-color 0 100 100 200)
                (md/dashing-g [0.25 0.5] 0))
        a5 (->> (md/construct-arrow-options
                 :head-type "tri"
                 :tail-type "tri"
                 :head-color {:red 200}
                 :tail-color {:red 200})
                (md/arrow-at [0 0] [12 0])
                (md/line-color 0 100 100 200))
        a6 (->> (md/superimpose'
                 (md/arrow-at [0 0] [0 4])
                 (md/arrow-at [0 0] [4 0])
                 (md/arrow-at [0 0] [4 4])
                 (md/arrow-at [0 0] [-4 0])
                 (md/arrow-at [0 0] [0 -4])
                 (md/arrow-at [0 0] [-4 -4])
                 (md/arrow-at [0 0] [-4 4])
                 (md/arrow-at [0 0] [4 -4]))
                (md/translate 6 0))]
    (->> (md/vsep' 2 a0 a1 a2 a3 a4 a5 a6)
         (md/background-frame 2 255 255 255))))

(defn construct-tower
  "Construct a tower-like structure with basic shapes.

  To create a svg file: (write-svg file-path (construct-tower))."
  []
  (let [tiny-circle (->> (md/circle 2.5)
                         (md/fill-color 0 100 0 100))
        little-circle (->> (md/circle 5)
                           (md/fill-color 100 0 0 100))
        big-circle (->> (md/circle 10)
                        (md/fill-color 0 0 100 100))
        tiny-square (md/rectangle 2.5 2.5)
        little-square (md/rectangle 5 5)
        big-rounded-square (md/rounded-rectangle 10 10 1)
        circles (md/cat 1 0 [tiny-circle little-circle big-circle])
        squares (md/hcat [tiny-square little-square big-rounded-square])
        structure (md/beside 1 0 circles squares)]
    (->> structure
         (md/rotate -45)
         (md/background-frame 2 255 255 255))))

(defn construct-circle
  "Constructs a circle with fill-color, line-color and shows the origin.

  To create a svg file: (write-svg file-path (construct-circle))."
  []
  (->> (md/circle 50)
       (md/fill-color 200 0 0 250)
       (md/line-color 0 200 0 250)
       (md/line-width-g 1)
       (md/show-origin)))

(defn construct-circle-pattern
  "Constructs a circle pattern.

  To create a svg file: (write-svg file-path (construct-circle-pattern))."
  []
  (->> (c/circle-pattern 0 0 200)
       (map (fn [[x y r]]
              (->> (md/circle r)
                   (md/line-color 100 10 0 100)
                   (md/translate x y))))
       md/superimpose))

(defn construct-hilbert-pattern
  "Constructs a hilbert-curve of given order (defaults to 6).

  To create a svg file: (write-svg file-path (construct-hilbert-pattern))."
  ([] (construct-hilbert-pattern 6))
  ([n]
   (let [width 500
         height 500]
     (->> (c/hilbert-pattern 0 0 width 0 0 height n)
          md/line
          (md/line-color 100 10 0 200)
          (md/translate (/ (- width) 2) (/ (- height) 2))))))

(defn construct-line-pattern
  "Constructs a pattern with lines.

  To create a svg file: (write-svg file-path (construct-line-pattern))."
  []
  (let [width 500
        height 500]
    (->> (c/line-pattern 0 0 500 100)
         (map (fn [[p1 p2 _p3 p4 p5 _p6]]
                (md/superimpose'
                 (md/line' p1 p2)
                 (md/line' p1 p5)
                 (md/line' p2 p4))))
         md/superimpose
         (md/translate (/ (- width) 2) (/ (- height) 2))
         (md/line-color 100 10 0 200))))

(defn construct-square-pattern
  "Constructs a pattern made of squares.

  To create a svg file: (write-svg file-path (construct-square-pattern))."
  []
  (let [width 500]
    (->> (c/square-pattern 0 0 (/ width 2.0))
         (map (fn [[x y n]]
                (->> (md/rectangle n n)
                     (md/translate x y)
                     (md/line-color 0 0 200 200))))
         md/superimpose)))

(defn construct-tree-pattern
  "Constructs a tree-like pattern.

  To create a svg file: (write-svg file-path (construct-tree-pattern))."
  []
  (->> (c/tree-pattern 0 -400 200 50 50)
       (map (fn [[p0 p1 p2 p3]]
              (md/superimpose'
               (md/line' p0 p1)
               (md/line' p1 p3)
               (md/line' p1 p2))))
       md/superimpose
       (md/line-color 100 10 0 200)))

(defn construct-rectangle
  "Constructs a styled rectangle, showing its origin and envelope.

  To create a svg file: (write-svg file-path (construct-rectangle))."
  []
  (->> (md/rectangle 100 50)
       (md/fill-color 0 0 200 250)
       (md/line-color 0 200 0 250)
       (md/line-width-g 1)
       (md/dashing-g [5 1] 0)
       (md/rotate 15)
       (md/show-origin)
       (md/show-envelope)))

(defn construct-polygons
  "Constructs a digram with some polygons.

  To create a svg file: (write-svg file-path (construct-polygons))."
  []
  (let [ps (md/vsep' 1
                     (md/polygon-regular 5 2)
                     (md/polygon-regular 10 2)
                     (md/polygon-sides [20 30 80 60] [1 2 3 4])
                     (md/polygon-polar [20 60 90 120] [1 1 1 1]))]
    (md/hsep' 1 ps (md/reflect-x ps))))

(defn construct-cubic-spline
  "Constructs a cubic-spline curve.

  To create a svg file: (write-svg file-path (construct-cubic-spline))."
  ([] (construct-cubic-spline false))
  ([closed?]
   (->> [[0 0] [5 5] [10 0] [15 15]]
        (md/cubic-spline closed?)
        (md/line-color 150 0 0 200))))

(defn construct-bspline
  "Constructs a cubic-b-spline curve.

  To create a svg file: (write-svg file-path (construct-bspline))."
  []
  (->> [[0 0] [5 5] [10 0] [15 15] [20 0] [30 30]]
       (md/bspline)
       (md/line-color 150 0 0 200)))

(defn construct-sine
  "Consturcts a sinusoidal plot using colored circles.

  To create a svg file: (write-svg file-path (construct-sine))."
  []
  (let [points (for [x (range (* -3 Math/PI) (* 3 Math/PI) 0.4)
                     :let [y (* 5 (Math/sin x))]]
                 [x y])
        marker (->> (md/circle 0.2)
                    (md/line-color 0 100 0 200)
                    (md/fill-color 80 0 0 250)
                    (md/line-width-g 0.1))
        markers (repeat (count points) marker)]
    (->> points
         (md/position markers)
         (md/superimpose' (md/cubic-spline false points)))))
