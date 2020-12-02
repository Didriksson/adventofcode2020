(ns advent-of-code.day-02
  (:require [clojure.string :as str]))

(import '(java.io BufferedReader StringReader))


(defn passwordPolicyOkP1? 
  [line]
  (let [minMax (nth (str/split line #" ") 0) password (nth (str/split line #" ") 2) char (first (nth (str/split line #" ") 1))]        
    (and 
      (>= (get (frequencies password) char 0) 
         (Integer/parseInt (str (first (map #(Integer/parseInt %) (str/split minMax #"-"))))))
      (<= (get (frequencies password) char 0) 
          (Integer/parseInt (str (last (map #(Integer/parseInt %) (str/split minMax #"-")))))))))

(defn passwordPolicyOkP2?
  [line]
  (let [minMax (nth (str/split line #" ") 0) password (nth (str/split line #" ") 2) char (first (nth (str/split line #" ") 1))]
    (let [matchFirst (= (get password (- (Integer/parseInt (str (first (map #(Integer/parseInt %) (str/split minMax #"-"))))) 1)) char)
          matchSecond (= (get password (- (Integer/parseInt (str (last (map #(Integer/parseInt %) (str/split minMax #"-"))))) 1)) char)]
          (and 
            (or matchFirst matchSecond)
            (not (and matchFirst matchSecond)))
  )))

(defn part-1
  "Day 02 Part 1"
  [input]
    (count (filter passwordPolicyOkP1? (str/split input #"\n" ))))

(defn part-2
  "Day 02 Part 2"
  [input]
    (count (filter passwordPolicyOkP2? (str/split input #"\n" ))))