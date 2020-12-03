(ns advent-of-code.day-03
  (:require [clojure.string :as str]))


(defn getForPosition [input width x y]
  (if (= \# (get input (+ (rem x width) (* y width))))
    1
    0))

(defn doForSlope 
  [input slope]
  (let [width (count (first (str/split input #"\r\n")))]
    (loop [x 0 y 0 trees 0]
      (if (>= y (/ (count (str/replace input #"\r\n" "")) width))
        trees
        (recur (+ x (first slope)) (+ y (last slope)) (+ (getForPosition (str/replace input #"\r\n" "") width x y) trees))))))

(defn part-1
  "Day 03 Part 1"
  [input]
  (doForSlope input '(3 1)))

(def slopes '( (1 1) (3 1) (5 1) (7 1) (1 2)))

(defn part-2
  "Day 03 Part 2"
  [input]
  (reduce * (map (fn [slope] (doForSlope input slope)) slopes)))
