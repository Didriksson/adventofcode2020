(ns advent-of-code.day-04
  (:require [clojure.string :as str]))

;byr (Birth Year)
;iyr (Issue Year)
;eyr (Expiration Year)
;hgt (Height)
;hcl (Hair Color)
;ecl (Eye Color)
;pid (Passport ID)
;cid (Country ID)
(def fieldsToCheck (list :byr :iyr :eyr :hgt :hcl :ecl :pid :cid))

(def validEyeColors (list "amb" "blu" "brn" "gry" "grn" "hzl" "oth"))

(defn passportHasAllField?
  [passport]
  (every? #(contains? passport %) (remove #{:cid} fieldsToCheck)))


(defn validBirthYear?
  [passport]
  (let [year (Integer/parseInt (get passport :byr))]
    (and
     (>= year 1920)
     (<= year 2002))))

(defn validIssueYear?
  [passport]
  (let [year (Integer/parseInt (get passport :iyr))]
    (and
     (>= year 2010)
     (<= year 2020))))

(defn validExpirationYear?
  [passport]
  (let [year (Integer/parseInt (get passport :eyr))]
    (and
     (>= year 2020)
     (<= year 2030))))

(defn validHeight?
  [passport]
  (let [height (get passport :hgt)]
    (cond
      (str/ends-with? height "cm") (and
                                    (>= (Integer/parseInt (first (str/split height #"cm"))) 150)
                                    (<= (Integer/parseInt (first (str/split height #"cm"))) 193))
      (str/ends-with? height "in") (and
                                    (>= (Integer/parseInt (first (str/split height #"in"))) 59)
                                    (<= (Integer/parseInt (first (str/split height #"in"))) 76))
      :else false)))


(defn validHaircolor?
  [passport]
  (let [color (get passport :hcl)]
    (string? (re-matches #"#\w{6}" color))))

(defn validEyecolor?
  [passport]
  (let [color (get passport :ecl)]
    (some #(= color %) validEyeColors)))

(defn validPassportID?
  [passport]
  (let [id (get passport :pid)]
    (string? (re-matches #"\d{9}" id))))

(defn passportValid?
  [passport]
  ((every-pred
    passportHasAllField?
    validBirthYear?
    validIssueYear?
    validExpirationYear?
    validHeight?
    validHaircolor?
    validEyecolor?
    validPassportID?)
   passport))



(defn readLinesForPassport
  [input]
  (filter #(not= '("") %) (partition-by str/blank? (str/split-lines input))))

(defn toKeyValueMap [passport]
  (into {} (map vec (map (fn [kv] (list (keyword (first kv)) (last kv))) (map (fn [y] (str/split y #":")) (flatten (map (fn [x] (str/split x #" ")) passport)))))))


(defn part-1
  "Day 04 Part 1"
  [input]
  (let [passports (readLinesForPassport input)]
    (count (filter true? (map passportHasAllField? (map toKeyValueMap passports))))))

(defn part-2
  "Day 04 Part 2"
  [input]
  (let [passports (readLinesForPassport input)]
    (count (filter true? (map passportValid? (map toKeyValueMap passports))))))
