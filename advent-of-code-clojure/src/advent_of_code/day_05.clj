(ns advent-of-code.day-05
  (:require [clojure.string :as str]))

(defn binarySpacePartition [rangeLeft charToAnalyze]
  (cond
    (or (= charToAnalyze \F)
        (= charToAnalyze \L)) (list (first rangeLeft) (quot (reduce + rangeLeft) 2))

    (or (= charToAnalyze \B)
        (= charToAnalyze \R)) (list (+ (quot (reduce + rangeLeft) 2) 1) (last rangeLeft))
    :else (println "Nej nej nej nej! Något på tok" charToAnalyze)))



(defn performBinarySearchFor [range chars]
  (loop [seat range remChars chars]
    (if (empty? remChars)
      (first seat)
      (recur (binarySpacePartition seat (first remChars)) (rest remChars)))))

(defn checkBoardingpass [boardingpass]
  (let [row (performBinarySearchFor '(0 127) (take 7 boardingpass))
        col (performBinarySearchFor '(0 7) (drop 7 boardingpass))]
    (+ col (* row 8))))

(defn part-1
  "Day 05 Part 1"
  [input]
  (reduce max (map checkBoardingpass (str/split-lines input))))

(defn emptySeat? [seatsChunk]
  (= 2 (- (second seatsChunk) (first seatsChunk))))

(defn part-2
  "Day 05 Part 2"
  [input]
  (+ 1 (first (flatten (filter emptySeat? (partition 2 1 (sort (map checkBoardingpass (str/split-lines input)))))))))