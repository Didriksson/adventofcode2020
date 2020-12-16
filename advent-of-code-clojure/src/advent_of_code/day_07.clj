(ns advent-of-code.day-07
  (:require [clojure.string :as str])) 


(defn to-rules 
[line]
  (let [bagWithContent (map str/trim (str/split line #"contain"))]
    (assoc {} (first bagWithContent) (rest bagWithContent))
  ) 
)

(defn part-1
  "Day 07 Part 1"
  [input]
  (filter (fn [x] (str/includes? x "shiny gold")) (mapcat vals (map to-rules (str/split-lines input)))))

(defn part-2
  "Day 07 Part 2"
  [input]
  input)


