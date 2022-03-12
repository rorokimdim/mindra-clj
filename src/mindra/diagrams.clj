(ns mindra.diagrams
  (:refer-clojure :exclude [cat])
  (:require [clojure.string :as s]
            [mindra.common :refer [sep vstr]]))

(defn background [r g b diagram]
  (sep "Background" r g b diagram))

(defn background-frame [n r g b diagram]
  (sep "BackgroundFrame" n r g b diagram))

(defn beside
  "Places two diagrams beside each other along given x-y vector.

  See http://support.hfm.io/1.6/api/diagrams-lib-1.4.1.2/Diagrams-Combinators.html"
  [x y diagram-a diagram-b]
  (sep "Beside" x y diagram-a diagram-b))

(defn bspline
  "A uniform cubic B-spline with given control points."
  [points]
  (sep "BSpline" (vstr points)))

(defn cat
  "Positions diagrams so that their local origins lie along given x-y vector.

  See http://support.hfm.io/1.6/api/diagrams-lib-1.4.1.2/Diagrams-Combinators.html"
  [x y diagrams]
  (sep "Cat" x y "[" (apply sep diagrams) "]"))

(defn circle
  "A circle with the given radius."
  [radius]
  (sep "Circle" radius))

(defn cubic-spline
  "A cubic spline from a list of points.

  The first argument specifies whether the path should be closed."
  [closed? points]
  (sep "CubicSpline" closed? (vstr points)))

(defn dashing-g
  "Sets global line-dashing style of a diagram.

  NS is list of alternating lengths of on and off portions of the stroke.
  N is offset into the dash pattern at which the stroke should start.

  See https://hackage.haskell.org/package/diagrams-lib-1.4.5.1/docs/Diagrams-Attributes.html"
  [ns n diagram]
  (sep "DashingG" (vstr ns) n diagram))

(defn dashing-l
  "Sets local line-dashing style of a diagram.

  NS is list of alternating lengths of on and off portions of the stroke.
  N is offset into the dash pattern at which the stroke should start.

  See https://hackage.haskell.org/package/diagrams-lib-1.4.5.1/docs/Diagrams-Attributes.html"
  [ns n diagram]
  (sep "DashingL" (vstr ns) n diagram))

(defn dashing-n
  "Sets normalized line-dashing style of a diagram.

  NS is list of alternating lengths of on and off portions of the stroke.
  N is offset into the dash pattern at which the stroke should start.

  See https://hackage.haskell.org/package/diagrams-lib-1.4.5.1/docs/Diagrams-Attributes.html"
  [ns n diagram]
  (sep "DashingN" (vstr ns) n diagram))

(defn fill-color [r g b a diagram]
  (sep "FillColor" r g b a diagram))

(defn hcat
  "Horizontally concatenates given diagrams.

  See https://diagrams.github.io/haddock/diagrams-lib/Diagrams-TwoD-Combinators.html"
  [diagrams]
  (sep "HCat" (str "[" (apply sep diagrams) "]")))

(defn hsep
  "Horizontally separates given diagrams with given pad (separation).

  See https://diagrams.github.io/haddock/diagrams-lib/Diagrams-TwoD-Combinators.html"
  [pad diagrams]
  (sep "HSep" pad (str "[" (apply sep diagrams) "]")))

(defn line
  "A line along a list of points."
  [& points]
  (sep "Line" (vstr points)))

(defn line-color
  "Sets line color of a diagram."
  [r g b a diagram]
  (sep "LineColor" r g b a diagram))

(defn line-width-g
  "Sets global line-width of a diagram.

  See https://hackage.haskell.org/package/diagrams-lib-1.4.5.1/docs/Diagrams-Attributes.html"
  [w diagram]
  (sep "LineWidthG" w diagram))

(defn line-width-l
  "Sets local line-width of a diagram.

  See https://hackage.haskell.org/package/diagrams-lib-1.4.5.1/docs/Diagrams-Attributes.html"
  [w diagram]
  (sep "LineWidthL" w diagram))

(defn line-width-n
  "Sets normalized line-width of a diagram.

  See https://hackage.haskell.org/package/diagrams-lib-1.4.5.1/docs/Diagrams-Attributes.html"
  [w diagram]
  (sep "LineWidthN" w diagram))

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

(defn superimpose [& diagrams]
  "Superimposes given diagrams on top of each other."
  (str "[" (apply sep diagrams) "]"))

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

(defn vcat
  "Vertically concatenates given diagrams.

  See https://diagrams.github.io/haddock/diagrams-lib/Diagrams-TwoD-Combinators.html"
  [diagrams]
  (sep "VCat" (str "[" (apply sep diagrams) "]")))

(defn vsep
  "Vertically separates given diagrams with given pad (separation).

  See https://diagrams.github.io/haddock/diagrams-lib/Diagrams-TwoD-Combinators.html"
  [pad diagrams]
  (sep "VSep" pad (str "[" (apply sep diagrams) "]")))
