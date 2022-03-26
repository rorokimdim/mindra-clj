(ns mindra.diagrams-test
  (:require [clojure.test :refer [deftest is testing]]
            [mindra.diagrams :as md]))

(deftest test-diagrams
  (testing "image"
    (is (= (md/image "apple.png" 200 300) "Image \"apple.png\" 200 300")))

  (testing "circle"
    (is (= (md/circle 10) "Circle 10"))
    (is (= (md/circle 10.878) "Circle 10.878")))

  (testing "line"
    (is (= (md/line' [10 20] [20 50] [0 0])
           "Line [[10 20] [20 50] [0 0]]"))
    (is (= (md/line' [10.77 20] [20 50.0] [0 0.0])
           "Line [[10.77 20] [20 50.0] [0 0.0]]"))
    (is (= (md/line') "Line []")))

  (testing "text"
    (is (= (md/text "hello") "\"hello\""))
    (is (= (md/text "hello svg diagrams") "\"hello svg diagrams\"")))

  (testing "translate"
    (is (= (md/translate 10 10 (md/rectangle 10.3 45))
           "Translate 10 10 Rectangle 10.3 45"))
    (is (= (md/translate -10.66 -10.89 (md/rectangle 10.3 45))
           "Translate -10.66 -10.89 Rectangle 10.3 45"))
    (is (= (md/translate 30 100 (md/superimpose'
                                 (md/line' [10 34.5] [89 100])
                                 (md/rectangle 10.3 45)))
           "Translate 30 100 [Line [[10 34.5] [89 100]] Rectangle 10.3 45]"))))
