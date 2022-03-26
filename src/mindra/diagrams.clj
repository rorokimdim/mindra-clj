(ns mindra.diagrams
  (:refer-clojure :exclude [cat])
  (:require [mindra.common :refer [sep vstr]]))

;;
;; Please see https://diagrams.github.io/doc/manual.html
;;
;; Most function names closely match the names used by diagrams.
;;
;; See https://github.com/rorokimdim/mindra/blob/master/src/Mindra/Diagrams/Parser/SVG.hs
;; for how minda will parse the constructed string.
;;

(defn construct-arrow-options
  "Constructs arrow options for styling an arrow diagram.

  head-type: A shape to place at the head of the arrow. Must be one of
             [tri, dart, halfDart, spike, thorn, lineHead, noHead]

  tail-type: A shape to place at the tail of the arrow. Must be one of
             [tri, dart, halfDart, spike, thorn, lineTail, noTail, quill, block]

  head-gap: Distance to leave between the head and the target point.
  tail-gap: Distance to leave between the starting point and the tail.
  head-color: Color of head as a map with keys :red, :green, :blue and :alpha
  tail-color: Color of head as a map with keys :red, :green, :blue and :alpha
  shapt-points: Optional list of points to use to draw the shaft -- a cubic spline
                of the points will be used instead of the default straight line.

  See https://diagrams.github.io/doc/arrow.html"
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

(defn arrow-at
  "An arrow starting at given point with the length and direction of given vector.

  See https://hackage.haskell.org/package/diagrams-lib/docs/Diagrams-TwoD-Arrow.html"
  ([point vector]
   (sep "ArrowAt" point vector))
  ([point vector options]
   (sep "ArrowAt" point vector options)))

(defn arrow-between
  "An arrow from point-a to point-b.

  See https://hackage.haskell.org/package/diagrams-lib/docs/Diagrams-TwoD-Arrow.html"
  ([point-a point-b]
   (sep "ArrowBetween" point-a point-b))
  ([point-a point-b options]
   (sep "ArrowBetween" point-a point-b options)))

(defn arrow-connect
  "An arrow connecting origins of diagrams named n1 and n2 in given diagram.

  See https://hackage.haskell.org/package/diagrams-lib/docs/Diagrams-TwoD-Arrow.html"
  ([n1 n2 diagram]
   (sep "ArrowConnect" (pr-str n1) (pr-str n2) diagram))
  ([n1 n2 diagram options]
   (sep "ArrowConnect" (pr-str n1) (pr-str n2) diagram options)))

(defn arrow-connect-outside
  "An arrow connecting boundaries of diagrams named n1 and n2 in given diagram.

  See https://hackage.haskell.org/package/diagrams-lib/docs/Diagrams-TwoD-Arrow.html"
  ([n1 n2 diagram]
   (sep "ArrowConnectOutside" (pr-str n1) (pr-str n2) diagram))
  ([n1 n2 diagram options]
   (sep "ArrowConnectOutside" (pr-str n1) (pr-str n2) diagram options)))

(defn arrow-connect-perim
  "An arrow connecting perimeter of diagrams named n1 and n2 at angles a1 and a2 (in degrees) in given diagram.

  See https://hackage.haskell.org/package/diagrams-lib/docs/Diagrams-TwoD-Arrow.html"
  ([n1 n2 a1 a2 diagram]
   (sep "ArrowConnectPerim" (pr-str n1) (pr-str n2) a1 a2 diagram))
  ([n1 n2 a1 a2 diagram options]
   (sep "ArrowConnectPerim" (pr-str n1) (pr-str n2) a1 a2 diagram options)))

;;
;; Functions for setting diagram attributes
;;

(defn background
  "Sets background color of a diagram.

  Superimposes the diagram on top of a bounding rectangle of the given color.

  See https://hackage.haskell.org/package/diagrams-lib/docs/Diagrams-TwoD.html"
  [r g b diagram]
  (sep "Background" r g b diagram))

(defn background-frame
  "Similar to background but makes the background rectangle larger than the diagram by given size.

  See https://hackage.haskell.org/package/diagrams-lib/docs/Diagrams-TwoD.html"
  [n r g b diagram]
  (sep "BackgroundFrame" n r g b diagram))

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

(defn fill-color
  "Sets the fill-color of a diagram to given red, green, blue, alpha values."
  [r g b a diagram]
  (sep "FillColor" r g b a diagram))

(defn font-size
  "Sets the font-size of text in a diagram."
  [n diagram]
  (sep "FontSize" n diagram))

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

;;
;; Alignment and combinator functions
;;

(defn align-left
  "Moves the local origin horizontally to the left edge of the envelope.

  See https://hackage.haskell.org/package/diagrams-lib/docs/Diagrams-TwoD-Align.html"
  [n diagram]
  (sep "AlignLeft" n diagram))

(defn align-right
  "Moves the local origin horizontally to the right edge of the envelope.

  See https://hackage.haskell.org/package/diagrams-lib/docs/Diagrams-TwoD-Align.html"
  [n diagram]
  (sep "AlignRight" n diagram))

(defn align-top
  "Moves the local origin vertically to the top edge of the envelope.

  See https://hackage.haskell.org/package/diagrams-lib/docs/Diagrams-TwoD-Align.html"
  [n diagram]
  (sep "AlignTop" n diagram))

(defn align-bottom
  "Moves the local origin vertically to the bottom edge of the envelope.

  See https://hackage.haskell.org/package/diagrams-lib/docs/Diagrams-TwoD-Align.html"
  [n diagram]
  (sep "AlignBottom" n diagram))

(defn align-x
  "Moves the local origin horizontally by given distance.

  Negative values move the local origin to the left edge of the boundary.
  Positive values move the local origin to the right edge of the boundary.

  See https://hackage.haskell.org/package/diagrams-lib/docs/Diagrams-TwoD-Align.html"
  [n diagram]
  (sep "AlignX" n diagram))

(defn align-y
  "Moves the local origin vertically by given distance.

  Negative values move the local origin to the bottom edge of the boundary.
  Positive values move the local origin to the top edge of the boundary.

  See https://hackage.haskell.org/package/diagrams-lib/docs/Diagrams-TwoD-Align.html"
  [n diagram]
  (sep "AlignY" n diagram))

(defn center-x
  "Centers the local origin along the x-axis.

  See https://hackage.haskell.org/package/diagrams-lib/docs/Diagrams-TwoD-Align.html"
  [diagram]
  (sep "CenterX" diagram))

(defn center-y
  "Centers the local origin along the y-axis.

  See https://hackage.haskell.org/package/diagrams-lib/docs/Diagrams-TwoD-Align.html"
  [diagram]
  (sep "CenterY" diagram))

(defn center-xy
  "Centers the local origin along both x-axis and y-axis.

  See https://hackage.haskell.org/package/diagrams-lib/docs/Diagrams-TwoD-Align.html"
  [diagram]
  (sep "CenterXY" diagram))

(defn beside
  "Places two diagrams beside each other along given x-y vector.

  See https://diagrams.github.io/haddock/diagrams-lib/Diagrams-Combinators.html#v:beside"
  [x y diagram-a diagram-b]
  (sep "Beside" x y diagram-a diagram-b))

(defn cat
  "Positions diagrams so that their local origins lie along given x-y vector.

  See https://diagrams.github.io/haddock/diagrams-lib/Diagrams-TwoD-Combinators.html"
  [x y diagrams]
  (sep "Cat" x y "[" (apply sep diagrams) "]"))

(defn cat'
  "Variadic version of cat."
  [x y & diagrams]
  (cat x y diagrams))

(defn hcat
  "Horizontally concatenates given diagrams.

  See https://diagrams.github.io/haddock/diagrams-lib/Diagrams-TwoD-Combinators.html"
  [diagrams]
  (sep "HCat" (str "[" (apply sep diagrams) "]")))

(defn hcat'
  "Variadic version of hcat."
  [& diagrams]
  (hcat diagrams))

(defn vcat
  "Vertically concatenates given diagrams.

  See https://diagrams.github.io/haddock/diagrams-lib/Diagrams-TwoD-Combinators.html"
  [diagrams]
  (sep "VCat" (str "[" (apply sep diagrams) "]")))

(defn vcat'
  "Variadic version of vcat."
  [& diagrams]
  (vcat diagrams))

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

(defn vsep
  "Vertically separates given diagrams with given pad (separation).

  See https://diagrams.github.io/haddock/diagrams-lib/Diagrams-TwoD-Combinators.html"
  [pad diagrams]
  (sep "VSep" pad (str "[" (apply sep diagrams) "]")))

(defn vsep'
  "Variadic version of vsep."
  [pad & diagrams]
  (vsep pad diagrams))

(defn superimpose
  "Superimposes given diagrams on top of each other."
  [diagrams]
  (str "[" (apply sep diagrams) "]"))

(defn superimpose'
  "Variadic version of superimpose."
  [& diagrams]
  (superimpose diagrams))

(defn pad
  "A diagram padded by give x and y factors."
  [pad-x pad-y diagram]
  (sep "Pad" pad-x pad-y diagram))

(defn position
  "Positions given diagrams at given points."
  [diagrams points]
  (sep "Position" (vstr points) "[" (apply sep diagrams) "]"))

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

;;
;; Geometric shapes
;;

(defn circle
  "A circle with the given radius."
  [radius]
  (sep "Circle" radius))

(defn ellipse
  "An ellipse with given eccentricity.

  The eccentricity must be within the interval [0,1).

  See https://hackage.haskell.org/package/diagrams-lib/docs/Diagrams-TwoD-Ellipse.html"
  [eccentricity]
  (sep "Ellipse" eccentricity))

(defn ellipse-xy
  "An axis-aligned ellipse, centered at the origin, with radius x along the x-axis and radius y along the y-axis.

  See https://hackage.haskell.org/package/diagrams-lib/docs/Diagrams-TwoD-Ellipse.html"
  [radius-x radius-y]
  (sep "EllipseXY" radius-x radius-y))

(defn rectangle
  "A rectangle of given width and height."
  [width height]
  (sep "Rectangle" width height))

(defn square
  "A square of given length."
  [n]
  (sep "Rectangle" n n))

(defn bspline
  "A uniform cubic B-spline with given control points.

  See https://hackage.haskell.org/package/diagrams-lib/docs/Diagrams-CubicSpline.html"
  [points]
  (sep "BSpline" (vstr points)))

(defn bspline'
  "Variadic version of bspline."
  [& points]
  (bspline points))

(defn cubic-spline
  "A cubic spline from a list of points.

  The first argument specifies whether the path should be closed.

  See https://hackage.haskell.org/package/diagrams-lib/docs/Diagrams-CubicSpline.html"
  [closed? points]
  (sep "CubicSpline" closed? (vstr points)))

(defn cubic-spline'
  "Variadic version of cubic-spline."
  [closed? & points]
  (cubic-spline closed? points))

(defn hrule
  "A horizontal line of given length."
  [n]
  (sep "HRule" n))

(defn vrule
  "A vertical line of given length."
  [n]
  (sep "VRule" n))

(defn line
  "A line along a list of points."
  [points]
  (sep "Line" (vstr points)))

(defn line'
  "A line along a list of points."
  [& points]
  (line points))

;;
;; Transformations
;;

(defn rotate
  "A diagram rotated anticlockwise by the given angle (in degrees)."
  [angle diagram]
  (sep "Rotate" angle diagram))

(defn scale
  "A diagram scaled by the given x and y factors."
  [x y diagram]
  (sep "Scale" x y diagram))

(defn translate
  "A diagram translated by the given x and y coordinates."
  [x y diagram]
  (sep "Translate" x y diagram))

;;
;; Miscellaneous
;;

(defn named
  "Names a diagram."
  [s diagram]
  (sep "Named" (pr-str s) diagram))

(defn text
  "A diagram with given text."
  [s]
  (pr-str s))

(defn image
  "An image from given file-path with given width and height."
  [file-path width height]
  (sep "Image" (pr-str file-path) width height))
