(ns mindra.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [mindra.api :as m]))

(deftest test-picture
  (testing "arc"
    (is (= (m/arc 30 90 10) "Arc 30 90 10"))
    (is (= (m/arc 30.56 90.89 10.78) "Arc 30.56 90.89 10.78")))

  (testing "arc-solid"
    (is (= (m/arc-solid 30 90 10) "ArcSolid 30 90 10"))
    (is (= (m/arc-solid 30.56 90.89 10.78) "ArcSolid 30.56 90.89 10.78")))

  (testing "bitmap"
    (is (= (m/bitmap "apple.png") "Bitmap \"apple.png\"")))

  (testing "bitmap-section"
    (is (= (m/bitmap-section 10 20 100 200 "apple.png")
           "BitmapSection 10 20 100 200 Bitmap \"apple.png\"")))

  (testing "blank"
    (is (= (m/blank) "Blank")))

  (testing "circle"
    (is (= (m/circle 10) "Circle 10"))
    (is (= (m/circle 10.878) "Circle 10.878")))

  (testing "circle-solid"
    (is (= (m/circle-solid 10) "CircleSolid 10"))
    (is (= (m/circle-solid 10.89) "CircleSolid 10.89")))

  (testing "color"
    (is (= (m/color 200 200 100 100 (m/circle 10))
           "Color 200 200 100 100 Circle 10"))
    (is (= (m/color 200 200 100 100 (m/arc 10 20 10))
           "Color 200 200 100 100 Arc 10 20 10")))

  (testing "line"
    (is (= (m/line [10 20] [20 50] [0 0])
           "Line [[10 20] [20 50] [0 0]]"))
    (is (= (m/line [10.77 20] [20 50.0] [0 0.0])
           "Line [[10.77 20] [20 50.0] [0 0.0]]"))
    (is (= (m/line) "Line []")))

  (testing "line-loop"
    (is (= (m/line-loop [10 20] [20 50] [0 0])
           "LineLoop [[10 20] [20 50] [0 0]]"))
    (is (= (m/line-loop [10.77 20] [20 50.0] [0 0.0])
           "LineLoop [[10.77 20] [20 50.0] [0 0.0]]"))
    (is (= (m/line-loop) "LineLoop []")))

  (testing "pictures"
    (is (= (m/pictures (m/circle 10)
                       (m/arc 10 20 10))
           "[Circle 10 Arc 10 20 10]"))
    (is (= (m/pictures (m/bitmap "apple.png"))
           "[Bitmap \"apple.png\"]"))
    (is (= (m/pictures) "[]")))

  (testing "polygon"
    (is (= (m/polygon [0 0] [10 20] [30.45 80.99])
           "Polygon [[0 0] [10 20] [30.45 80.99]]"))
    (is (= (m/polygon) "Polygon []")))

  (testing "rectangle-solid"
    (is (= (m/rectangle-solid 100 200) "RectangleSolid 100 200"))
    (is (= (m/rectangle-solid 100.89 200) "RectangleSolid 100.89 200"))
    (is (= (m/rectangle-solid 100.89 200.5) "RectangleSolid 100.89 200.5")))

  (testing "rectangle-upper-solid"
    (is (= (m/rectangle-upper-solid 100 85.6)
           "RectangleUpperSolid 100 85.6")))

  (testing "rectangle-upper-wire"
    (is (= (m/rectangle-upper-wire 100 85.6)
           "RectangleUpperWire 100 85.6")))

  (testing "rectangle-wire"
    (is (= (m/rectangle-wire 100 85.6)
           "RectangleWire 100 85.6")))

  (testing "rotate"
    (is (= (m/rotate 30 (m/rectangle-solid 10.3 45))
           "Rotate 30 RectangleSolid 10.3 45"))
    (is (= (m/rotate 30 (m/pictures
                         (m/line [10 34.5] [89 100])
                         (m/rectangle-solid 10.3 45)))
           "Rotate 30 [Line [[10 34.5] [89 100]] RectangleSolid 10.3 45]")))

  (testing "scale"
    (is (= (m/scale 0.125 0.125 (m/rectangle-solid 10.3 45))
           "Scale 0.125 0.125 RectangleSolid 10.3 45"))
    (is (= (m/scale 0.125 0.1 (m/pictures
                               (m/line [10 34.5] [89 100])
                               (m/rectangle-solid 10.3 45)))
           "Scale 0.125 0.1 [Line [[10 34.5] [89 100]] RectangleSolid 10.3 45]")))

  (testing "sector-wire"
    (is (= (m/sector-wire 30 120 100)
           "SectorWire 30 120 100"))
    (is (= (m/sector-wire 30 12.89 100)
           "SectorWire 30 12.89 100")))

  (testing "text"
    (is (= (m/text "hello") "\"hello\""))
    (is (= (m/text "hello glossy gloss") "\"hello glossy gloss\"")))

  (testing "thick-arc"
    (is (= (m/thick-arc 30 120 100 5) "ThickArc 30 120 100 5"))
    (is (= (m/thick-arc 30 120.89 100.5 5) "ThickArc 30 120.89 100.5 5")))

  (testing "thick-circle"
    (is (= (m/thick-circle 30 5) "ThickCircle 30 5"))
    (is (= (m/thick-circle 30.8 5.5) "ThickCircle 30.8 5.5")))

  (testing "translate"
    (is (= (m/translate 10 10 (m/rectangle-solid 10.3 45))
           "Translate 10 10 RectangleSolid 10.3 45"))
    (is (= (m/translate -10.66 -10.89 (m/rectangle-solid 10.3 45))
           "Translate -10.66 -10.89 RectangleSolid 10.3 45"))
    (is (= (m/translate 30 100 (m/pictures
                                (m/line [10 34.5] [89 100])
                                (m/rectangle-solid 10.3 45)))
           "Translate 30 100 [Line [[10 34.5] [89 100]] RectangleSolid 10.3 45]"))))
