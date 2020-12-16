(ns advent-of-code.day-09
  (:require [clojure.math.combinatorics :as combo]))

(import '(java.io BufferedReader StringReader))

(def testinput "35
20
15
25
47
40
62
55
65
95
102
117
150
182
127
219
299
277
309
576")

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

(defn find-series-that-sums-to-this-number-please [partitionsize number bunchofnumbers]
  (first (filter #(= number (reduce + %)) (partition-all partitionsize 1 bunchofnumbers)))
  )

(defn part-1
  "Day 09 Part 1"
  [input]
  (first-invalid 5 (inputAsInt input)))

(defn part-2
  "Day 09 Part 2"
  [input]
  (loop [partitionsize 2 number (first-invalid 25 (inputAsInt input))]
    (let [result (sort (find-series-that-sums-to-this-number-please partitionsize (first-invalid 25 (inputAsInt input)) (inputAsInt input)))]
      (if (not (empty? result))
        (+ (first result) (last result))
        (if (>= partitionsize (count (inputAsInt input)))
          :error-no-match
          (recur (inc partitionsize) number)))))
    )
  

