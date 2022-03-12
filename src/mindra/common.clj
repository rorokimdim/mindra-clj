(ns mindra.common
  (:require [clojure.string :as s]))

(defn sep [& xs]
  (with-out-str
    )
  (s/join " " xs))

(defn vstr [xs]
  (if (vector? xs)
    (pr-str xs)
    (pr-str (into [] xs))))
