(ns advent-of-code.day-09
  (:require [clojure.math.combinatorics :as combo]))

(import '(java.io BufferedReader StringReader))


(defn inputAsInt [input] (map #(Long/parseLong %) (line-seq (BufferedReader. (StringReader. input)))))


(defn valid? [number preamble]
  (some #(= number (+ (first %) (last %))) (combo/combinations preamble 2)))

(defn first-invalid [steps input]
  (loop [listTocheck input]
    (if (valid? (first (drop steps listTocheck)) (take steps listTocheck))
        (recur (drop 1 listTocheck))
        (first (drop steps listTocheck))  
      )
    )
  )

(defn part-1
  "Day 09 Part 1"
  [input]
  (first-invalid 25 (inputAsInt input)))

(defn part-2
  "Day 09 Part 2"
  [input]
  (inputAsInt input))
