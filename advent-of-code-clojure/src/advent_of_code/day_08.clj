(ns advent-of-code.day-08
  (:require [clojure.string :as str]))


(defn parseInput [input]
  (map #(vector (first  %) (Integer/parseInt (last %))) (map #(str/split % #" ") (str/split-lines input))))

(defn apply-command [commands pointer acc]
  (let [[cmd val] (nth commands pointer)]
    (case cmd
      "acc" [(inc pointer) (+ val acc)]
      "jmp" [(+ val pointer) acc]
      "nop" [(inc pointer) acc])))

(defn run-til-loop-or-terminated [input]
  (loop [pointer 0 acc 0 cmdHistory []]
    (if (some #{pointer} cmdHistory)
      [:looped acc]
      (if (>= pointer (count input))
        [:terminated acc]
        (let [[po ac] (apply-command input pointer acc)]
          (recur po ac (conj cmdHistory pointer)))))))

(defn replaceCmd [input index]
  
  (let [item (nth input index)]
    (if (= (first item) "jmp")
      (assoc (into [] input) index (vector "nop" (last item)))
      (assoc (into [] input) index (vector "jmp" (last item))))))

(defn replace-and-run-til-terminated [input]
  (loop [cmdToChangeIndexes (keep-indexed #(when (or (= (first %2) "nop") (= (first %2) "jmp")) %) input)]
    (let [newInput (replaceCmd input (last cmdToChangeIndexes))]
      (let [[exitcode acc] (run-til-loop-or-terminated newInput)]
        (println exitcode)
        (case exitcode
          :terminated [exitcode acc]
          :looped (recur (drop-last cmdToChangeIndexes)))))))

(defn part-1
  "Day 08 Part 1"
  [input]
  (run-til-loop-or-terminated (parseInput input)))

(defn part-2
  "Day 08 Part 2"
  [input]
  (replace-and-run-til-terminated (parseInput input)))