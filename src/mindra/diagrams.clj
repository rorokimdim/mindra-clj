(ns mindra.diagrams
  (:refer-clojure :exclude [cat])
  (:require [clojure.string :as s]
            [mindra.common :refer [sep vstr]]))

(defn construct-arrow-options
  [& {:keys [head-type
             tail-type
             head-gap
             tail-gap
             head-color
             tail-color
             shaft-points]
      :or {head-type "dart"
           tail-type "noTail"
           head-gap 0
           tail-gap 0
           head-color {:red 0 :green 0 :blue 0 :alpha 255}
           tail-color {:red 0 :green 0 :blue 0 :alpha 255}
           shaft-points []}}]
  (sep "ArrowOpts"
       head-type
       tail-type
       head-gap
       tail-gap
       (:red head-color 0) (:green head-color 0) (:blue head-color 0) (:alpha head-color 255)
       (:red tail-color 0) (:green tail-color 0) (:blue tail-color 0) (:alpha tail-color 255)
       (vstr shaft-points)))

(defn align-x
  [n diagram]
  (sep "AlignX" n diagram))

(defn arrow-at
  ([point vector]
   (sep "ArrowAt" point vector))
  ([point vector options]
   (sep "ArrowAt" point vector options)))

(defn arrow-between
  ([point-a point-b]
   (sep "ArrowBetween" point-a point-b))
  ([point-a point-b options]
   (sep "ArrowBetween" point-a point-b options)))

(defn arrow-connect
  ([n1 n2 diagram]
   (sep "ArrowConnect" (pr-str n1) (pr-str n2) diagram))
  ([n1 n2 diagram options]
   (sep "ArrowConnect" (pr-str n1) (pr-str n2) diagram options)))

(defn arrow-connect-outside
  ([n1 n2 diagram]
   (sep "ArrowConnectOutside" (pr-str n1) (pr-str n2) diagram))
  ([n1 n2 diagram options]
   (sep "ArrowConnectOutside" (pr-str n1) (pr-str n2) diagram options)))

(defn arrow-connect-perim
  ([n1 n2 a1 a2 diagram]
   (sep "ArrowConnectPerim" (pr-str n1) (pr-str n2) a1 a2 diagram))
  ([n1 n2 a1 a2 diagram options]
   (sep "ArrowConnectPerim" (pr-str n1) (pr-str n2) a1 a2 diagram options)))

(defn background
  [r g b diagram]
  (sep "Background" r g b diagram))

(defn background-frame
  [n r g b diagram]
  (sep "BackgroundFrame" n r g b diagram))

(defn beside
  "Places two diagrams beside each other along given x-y vector.

  See https://diagrams.github.io/haddock/diagrams-lib/Diagrams-TwoD-Combinators.html"
  [x y diagram-a diagram-b]
  (sep "Beside" x y diagram-a diagram-b))

(defn bspline
  "A uniform cubic B-spline with given control points."
  [points]
  (sep "BSpline" (vstr points)))

(defn bspline'
  "Variadic version of bspline."
  [& points]
  (bspline points))

(defn cat
  "Positions diagrams so that their local origins lie along given x-y vector.

  See https://diagrams.github.io/haddock/diagrams-lib/Diagrams-TwoD-Combinators.html"
  [x y diagrams]
  (sep "Cat" x y "[" (apply sep diagrams) "]"))

(defn cat'
  "Variadic version of cat."
  [x y & diagrams]
  (cat x y diagrams))

(defn center-x
  [diagram]
  (sep "CenterX" diagram))

(defn center-xy
  [diagram]
  (sep "CenterXY" diagram))

(defn circle
  "A circle with the given radius."
  [radius]
  (sep "Circle" radius))

(defn cubic-spline
  "A cubic spline from a list of points.

  The first argument specifies whether the path should be closed."
  [closed? points]
  (sep "CubicSpline" closed? (vstr points)))

(defn cubic-spline'
  "Variadic version of cubic-spline."
  [closed? & points]
  (cubic-spline closed? points))

(defn dashing-g
  "Sets global line-dashing style of a diagram.

  NS is list of alternating lengths of on and off portions of the stroke.
  N is offset into the dash pattern at which the stroke should start.

  See https://hackage.haskell.org/package/diagrams-lib/docs/Diagrams-Attributes.html"
  [ns n diagram]
  (sep "DashingG" (vstr ns) n diagram))

(defn dashing-l
  "Sets local line-dashing style of a diagram.

  NS is list of alternating lengths of on and off portions of the stroke.
  N is offset into the dash pattern at which the stroke should start.

  See https://hackage.haskell.org/package/diagrams-lib/docs/Diagrams-Attributes.html"
  [ns n diagram]
  (sep "DashingL" (vstr ns) n diagram))

(defn dashing-n
  "Sets normalized line-dashing style of a diagram.

  NS is list of alternating lengths of on and off portions of the stroke.
  N is offset into the dash pattern at which the stroke should start.

  See https://hackage.haskell.org/package/diagrams-lib/docs/Diagrams-Attributes.html"
  [ns n diagram]
  (sep "DashingN" (vstr ns) n diagram))

(defn ellipse [eccentricity]
  (sep "Ellipse" eccentricity))

(defn ellipse-xy [radius-x radius-y]
  (sep "EllipseXY" radius-x radius-y))

(defn fill-color [r g b a diagram]
  (sep "FillColor" r g b a diagram))

(defn font-size [n diagram]
  (sep "FontSize" n diagram))

(defn hcat
  "Horizontally concatenates given diagrams.

  See https://diagrams.github.io/haddock/diagrams-lib/Diagrams-TwoD-Combinators.html"
  [diagrams]
  (sep "HCat" (str "[" (apply sep diagrams) "]")))

(defn hcat'
  "Variadic version of hcat."
  [& diagrams]
  (hcat diagrams))

(defn hrule [n]
  (sep "HRule" n))

(defn hsep
  "Horizontally separates given diagrams with given pad (separation).

  See https://diagrams.github.io/haddock/diagrams-lib/Diagrams-TwoD-Combinators.html"
  [pad diagrams]
  (sep "HSep" pad (str "[" (apply sep diagrams) "]")))

(defn hsep'
  "Horizontally separates given diagrams with given pad (separation).

  See https://diagrams.github.io/haddock/diagrams-lib/Diagrams-TwoD-Combinators.html"
  [pad & diagrams]
  (hsep pad diagrams))

(defn image
  [file-path width height]
  (sep "Image" (pr-str file-path) width height))

(defn line
  "A line along a list of points."
  [points]
  (sep "Line" (vstr points)))

(defn line'
  "A line along a list of points."
  [& points]
  (line points))

(defn line-color
  "Sets line color of a diagram."
  [r g b a diagram]
  (sep "LineColor" r g b a diagram))

(defn line-width-g
  "Sets global line-width of a diagram.

  See https://hackage.haskell.org/package/diagrams-lib/docs/Diagrams-Attributes.html"
  [w diagram]
  (sep "LineWidthG" w diagram))

(defn line-width-l
  "Sets local line-width of a diagram.

  See https://hackage.haskell.org/package/diagrams-lib/docs/Diagrams-Attributes.html"
  [w diagram]
  (sep "LineWidthL" w diagram))

(defn line-width-n
  "Sets normalized line-width of a diagram.

  See https://hackage.haskell.org/package/diagrams-lib/docs/Diagrams-Attributes.html"
  [w diagram]
  (sep "LineWidthN" w diagram))

(defn named
  [s diagram]
  (sep "Named" (pr-str s) diagram))

(defn pad
  "A diagram padded by give x and y factors."
  [pad-x pad-y diagram]
  (sep "Pad" pad-x pad-y diagram))

(defn position
  "Positions given diagrams at given points."
  [diagrams points]
  (sep "Position" (vstr points) "[" (apply sep diagrams) "]"))

(defn rectangle
  "A rectangle centered about the origin."
  [width height]
  (sep "Rectangle" width height))

(defn show-envelope
  "Shows envelope of diagram with a red cubic spline.

  Useful for debugging purposes."
  [diagram]
  (sep "ShowEnvelope" diagram))

(defn show-origin
  "Shows origin of diagram as a red dot.

  Useful for debugging purposes."
  [diagram]
  (sep "ShowOrigin" diagram))

(defn square
  [n]
  (sep "Rectangle" n n))

(defn superimpose [diagrams]
  "Superimposes given diagrams on top of each other."
  (str "[" (apply sep diagrams) "]"))

(defn superimpose'
  "Variadic version of superimpose."
  [& diagrams]
  (superimpose diagrams))

(defn rotate
  "A diagram rotated anticlockwise by the given angle (in degrees)."
  [angle diagram]
  (sep "Rotate" angle diagram))

(defn scale
  "A diagram scaled by the given x and y factors."
  [x y diagram]
  (sep "Scale" x y diagram))

(defn text
  [s]
  (pr-str s))

(defn translate
  "A diagram translated by the given x and y coordinates."
  [x y diagram]
  (sep "Translate" x y diagram))

(defn vcat
  "Vertically concatenates given diagrams.

  See https://diagrams.github.io/haddock/diagrams-lib/Diagrams-TwoD-Combinators.html"
  [diagrams]
  (sep "VCat" (str "[" (apply sep diagrams) "]")))

(defn vcat'
  "Variadic version of vcat."
  [& diagrams]
  (vcat diagrams))

(defn vrule [n]
  (sep "VRule" n))

(defn vsep
  "Vertically separates given diagrams with given pad (separation).

  See https://diagrams.github.io/haddock/diagrams-lib/Diagrams-TwoD-Combinators.html"
  [pad diagrams]
  (sep "VSep" pad (str "[" (apply sep diagrams) "]")))

(defn vsep'
  "Variadic version of vsep."
  [pad & diagrams]
  (vsep pad diagrams))
