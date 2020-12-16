(ns advent-of-code.day-07
  (:require [clojure.string :as str]))


(defn parse-one-row [row]
  (let [item (map str/trim (str/split (str/trim (str/replace (str/replace row #"\." "") #"bags|bag" "")) #"contain"))]
    (mapcat #(str/split % #" , ") item)))


(defn find-containing-bag [bag rule]
  (when (some #(= % bag) rule)
    (first rule)))

(defn find-all-bags-containing [bag rules]

  (loop [bagsToFind (list bag) foundbags '()]
    (if (empty? bagsToFind)
      foundbags
      (let [newBags (remove nil? (map #(find-containing-bag (first bagsToFind) %) rules))]
        (recur (remove (set foundbags) (distinct (concat (rest bagsToFind) newBags))) (distinct (conj foundbags (first bagsToFind))))))))

(defn part-1
  "Day 07 Part 1"
  [input]
  (let [bags  (distinct (find-all-bags-containing "shiny gold" (map parse-one-row (str/split-lines (str/replace input  #"\d\s" "")))))]
    (-
     (count bags)
     1)))

(defn parse-containing-bags [content]
  (map #(list (Integer/parseInt (first %)) (keyword (str/join "-" (rest %)))) (partition 3 (str/split (str/replace content #", " "") #" "))))
(defn parse-row-with-numbers [row]
  (let [[x xs] (str/split (str/replace (str/replace row #"\." "") #"bags|bag" "") #" contain ")]
    (assoc {} (keyword (str/replace (str/trim x) #" " "-")) (parse-containing-bags xs))))

(defn find-all-containing [input bag result]
  (let [foundBags (get input (keyword (second bag)))]
    (if (empty? foundBags)
      result
      (merge (first foundBags) (map #(find-all-containing input % (merge result foundBags)) foundBags)))
    )
  )



(defn part-2
  "Day 07 Part 2"
  [input]
  (let [bag-map (into {} (map parse-row-with-numbers (str/split-lines input)))]
    (find-all-containing bag-map (list 1 (keyword "shiny-gold")) '()))
  )
