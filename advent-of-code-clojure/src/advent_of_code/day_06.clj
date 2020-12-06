(ns advent-of-code.day-06
  (:require [clojure.string :as str]))


(defn group-until-blank
  [input]
  (filter #(not= '("") %) (partition-by str/blank? (str/split-lines input))))

(defn occurances-for-group [group]
  (apply merge-with + (map frequencies  group)))

(defn part-1
  "Day 06 Part 1"
  [input]
  (reduce + (map count (map occurances-for-group (group-until-blank input)))))

(defn allAnswered [input]
  (filter #(= (second %) (count input)) (occurances-for-group input)))


(defn part-2
  "Day 06 Part 2"
  [input]
  (reduce + (map count (map allAnswered (group-until-blank input)))))
