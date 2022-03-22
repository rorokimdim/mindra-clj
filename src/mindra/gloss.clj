(ns mindra.gloss
  (:require [clojure.string :as s]
            [mindra.common :refer [sep vstr]]))

;;
;; All functions should closely match
;; https://hackage.haskell.org/package/gloss-1.13.2.1/docs/Graphics-Gloss-Data-Picture.html#t:Picture
;;
;; All the docstrings were mostly copied over from the same location.
;;

(defn arc
  "A circular arc drawn counter-clockwise between two angles (in degrees) at the given radius."
  [angle1 angle2 radius]
  (sep "Arc" angle1 angle2 radius))

(defn arc-solid
  "A solid arc, drawn counter-clockwise between two angles at the given radius."
  [angle1 angle2 radius]
  (sep "ArcSolid" angle1 angle2 radius))

(defn image
  "An image from given filepath.

  Given filepath can point to a jpeg, png or bmp file.

  Images are stored in memory, indexed by filepath. If a filepath has already been
  loaded, mindra will use the corresponding image from its cache."
  [path]
  (sep "Image" (pr-str path)))

(defn image-section
  "A subsection of an image starting from given x y location with given width and height.

  Besides the subsection selection, should be similar to image function. See docstring of
  image function for more details."
  [x y width height path]
  (sep "ImageSection" x y width height
       (image path)))

(defn blank
  "A blank picture, with nothing in it."
  []
  (str "Blank"))

(defn circle
  "A circle with the given radius."
  [radius]
  (sep "Circle" radius))

(defn circle-solid
  "A solid circle with the given radius."
  [radius]
  (sep "CircleSolid" radius))

(defn color
  "A picture drawn with given color."
  [red green blue alpha picture]
  (sep "Color" red green blue alpha picture))

(defn line
  "A line along a list of points."
  [points]
  (sep "Line" (vstr points)))

(defn line'
  "Variadic version of line."
  [& points]
  (line points))

(defn line-loop
  "A closed loop along a list of points."
  [points]
  (sep "LineLoop" (vstr points)))

(defn line-loop'
  "Variadic version of line-loop."
  [& points]
  (line-loop points))

(defn pictures
  "A picture consisting of several others."
  [pics]
  (str "[" (apply sep pics) "]"))

(defn pictures'
  "Variadic version of pictures."
  [& pics]
  (pictures pics))

(defn polygon
  "A convex polygon along a list of points; filled with a solid color."
  [points]
  (sep "Polygon" (vstr points)))

(defn polygon'
  "Variadic version of polygon."
  [& points]
  (polygon points))

(defn rectangle-solid
  "A solid rectangle centered about the origin."
  [width height]
  (sep "RectangleSolid" width height))

(defn rectangle-upper-solid
  "A solid rectangle in the y > 0 half of the x-y plane."
  [width height]
  (sep "RectangleUpperSolid" width height))

(defn rectangle-upper-wire
  "A wireframe rectangle in the y > 0 half of the x-y plane."
  [width height]
  (sep "RectangleUpperWire" width height))

(defn rectangle-wire
  "A wireframe rectangle centered about the origin."
  [width height]
  (sep "RectangleWire" width height))

(defn rotate
  "A picture rotated clockwise by the given angle (in degrees)."
  [angle picture]
  (sep "Rotate" angle picture))

(defn scale
  "A picture scaled by the given x and y factors."
  [x y picture]
  (sep "Scale" x y picture))

(defn sector-wire
  "A wireframe sector of a circle.

  An arc is draw counter-clockwise from the first to the second angle at the given radius.
  Lines are drawn from the origin to the ends of the arc."
  [angle1 angle2 radius]
  (sep "SectorWire" angle1 angle2 radius))

(defn text
  "Some text to draw with a vector font."
  [s]
  (pr-str s))

(defn thick-arc
  "A circular arc drawn counter-clockwise between two angles (in degrees),
  with the given radius and thickness.

  If the thickness is 0 then this is equivalent to arc."
  [angle1 angle2 radius thickness]
  (sep "ThickArc" angle1 angle2 radius thickness))

(defn thick-circle
  "A circle with the given radius and thickness.

  If the thickness is 0 then this is equivalent to circle."
  [radius thickness]
  (sep "ThickCircle" radius thickness))

(defn translate
  "A picture translated by the given x and y coordinates."
  [x y picture]
  (sep "Translate" x y picture))
