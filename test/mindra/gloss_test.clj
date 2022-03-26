(ns mindra.gloss-test
  (:require [clojure.test :refer [deftest is testing]]
            [mindra.gloss :as mg]))

(deftest test-gloss
  (testing "arc"
    (is (= (mg/arc 30 90 10) "Arc 30 90 10"))
    (is (= (mg/arc 30.56 90.89 10.78) "Arc 30.56 90.89 10.78")))

  (testing "arc-solid"
    (is (= (mg/arc-solid 30 90 10) "ArcSolid 30 90 10"))
    (is (= (mg/arc-solid 30.56 90.89 10.78) "ArcSolid 30.56 90.89 10.78")))

  (testing "image"
    (is (= (mg/image "apple.png") "Image \"apple.png\"")))

  (testing "image-section"
    (is (= (mg/image-section 10 20 100 200 "apple.png")
           "ImageSection 10 20 100 200 Image \"apple.png\"")))

  (testing "blank"
    (is (= (mg/blank) "Blank")))

  (testing "circle"
    (is (= (mg/circle 10) "Circle 10"))
    (is (= (mg/circle 10.878) "Circle 10.878")))

  (testing "circle-solid"
    (is (= (mg/circle-solid 10) "CircleSolid 10"))
    (is (= (mg/circle-solid 10.89) "CircleSolid 10.89")))

  (testing "color"
    (is (= (mg/color 200 200 100 100 (mg/circle 10))
           "Color 200 200 100 100 Circle 10"))
    (is (= (mg/color 200 200 100 100 (mg/arc 10 20 10))
           "Color 200 200 100 100 Arc 10 20 10")))

  (testing "line"
    (is (= (mg/line' [10 20] [20 50] [0 0])
           "Line [[10 20] [20 50] [0 0]]"))
    (is (= (mg/line' [10.77 20] [20 50.0] [0 0.0])
           "Line [[10.77 20] [20 50.0] [0 0.0]]"))
    (is (= (mg/line') "Line []")))

  (testing "line-loop"
    (is (= (mg/line-loop' [10 20] [20 50] [0 0])
           "LineLoop [[10 20] [20 50] [0 0]]"))
    (is (= (mg/line-loop' [10.77 20] [20 50.0] [0 0.0])
           "LineLoop [[10.77 20] [20 50.0] [0 0.0]]"))
    (is (= (mg/line-loop') "LineLoop []")))

  (testing "pictures"
    (is (= (mg/pictures' (mg/circle 10)
                         (mg/arc 10 20 10))
           "[Circle 10 Arc 10 20 10]"))
    (is (= (mg/pictures' (mg/image "apple.png"))
           "[Image \"apple.png\"]"))
    (is (= (mg/pictures') "[]")))

  (testing "polygon"
    (is (= (mg/polygon' [0 0] [10 20] [30.45 80.99])
           "Polygon [[0 0] [10 20] [30.45 80.99]]"))
    (is (= (mg/polygon') "Polygon []")))

  (testing "rectangle-solid"
    (is (= (mg/rectangle-solid 100 200) "RectangleSolid 100 200"))
    (is (= (mg/rectangle-solid 100.89 200) "RectangleSolid 100.89 200"))
    (is (= (mg/rectangle-solid 100.89 200.5) "RectangleSolid 100.89 200.5")))

  (testing "rectangle-upper-solid"
    (is (= (mg/rectangle-upper-solid 100 85.6)
           "RectangleUpperSolid 100 85.6")))

  (testing "rectangle-upper-wire"
    (is (= (mg/rectangle-upper-wire 100 85.6)
           "RectangleUpperWire 100 85.6")))

  (testing "rectangle-wire"
    (is (= (mg/rectangle-wire 100 85.6)
           "RectangleWire 100 85.6")))

  (testing "rotate"
    (is (= (mg/rotate 30 (mg/rectangle-solid 10.3 45))
           "Rotate 30 RectangleSolid 10.3 45"))
    (is (= (mg/rotate 30 (mg/pictures'
                          (mg/line' [10 34.5] [89 100])
                          (mg/rectangle-solid 10.3 45)))
           "Rotate 30 [Line [[10 34.5] [89 100]] RectangleSolid 10.3 45]")))

  (testing "scale"
    (is (= (mg/scale 0.125 0.125 (mg/rectangle-solid 10.3 45))
           "Scale 0.125 0.125 RectangleSolid 10.3 45"))
    (is (= (mg/scale 0.125 0.1 (mg/pictures'
                                (mg/line' [10 34.5] [89 100])
                                (mg/rectangle-solid 10.3 45)))
           "Scale 0.125 0.1 [Line [[10 34.5] [89 100]] RectangleSolid 10.3 45]")))

  (testing "sector-wire"
    (is (= (mg/sector-wire 30 120 100)
           "SectorWire 30 120 100"))
    (is (= (mg/sector-wire 30 12.89 100)
           "SectorWire 30 12.89 100")))

  (testing "text"
    (is (= (mg/text "hello") "\"hello\""))
    (is (= (mg/text "hello glossy gloss") "\"hello glossy gloss\"")))

  (testing "thick-arc"
    (is (= (mg/thick-arc 30 120 100 5) "ThickArc 30 120 100 5"))
    (is (= (mg/thick-arc 30 120.89 100.5 5) "ThickArc 30 120.89 100.5 5")))

  (testing "thick-circle"
    (is (= (mg/thick-circle 30 5) "ThickCircle 30 5"))
    (is (= (mg/thick-circle 30.8 5.5) "ThickCircle 30.8 5.5")))

  (testing "translate"
    (is (= (mg/translate 10 10 (mg/rectangle-solid 10.3 45))
           "Translate 10 10 RectangleSolid 10.3 45"))
    (is (= (mg/translate -10.66 -10.89 (mg/rectangle-solid 10.3 45))
           "Translate -10.66 -10.89 RectangleSolid 10.3 45"))
    (is (= (mg/translate 30 100 (mg/pictures'
                                 (mg/line' [10 34.5] [89 100])
                                 (mg/rectangle-solid 10.3 45)))
           "Translate 30 100 [Line [[10 34.5] [89 100]] RectangleSolid 10.3 45]"))))
