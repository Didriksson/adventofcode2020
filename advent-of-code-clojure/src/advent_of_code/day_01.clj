(ns advent-of-code.day-01
  (:require [clojure.math.combinatorics :as combo]))
(import '(java.io BufferedReader StringReader))

(defn inputAsInt [input] (map #(Integer/parseInt %) (line-seq (BufferedReader. (StringReader. input)))))

(defn solveForN
  [input n]
  (reduce * (first (filter (fn [x] (= (reduce + x) 2020)) (map vec
                                                               (combo/combinations
                                                                (inputAsInt input) n))))))

(defn part-1
  "Day 01 Part 1"
  [input]
  (solveForN input 2))

(defn part-2
  "Day 01 Part 2"
  [input]
  (solveForN input 3))