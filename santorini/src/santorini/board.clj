(ns santorini.board
  (:require [clojure.math.combinatorics :as math]))


(defn cToI
  "Translate the coordinate (1-based) to indexes (0-based)"
  [c]
  (mapv #(- % 1) c))


(defn levelAt 
  "Get the board level at a given coordinate"
  [b c]
  (get-in b (cToI c)))


(defn setLevelAt
  "Return a new board with the level at coordinate c set to l"
  [b c l]
  (assoc-in b (cToI c) l))


(defn onBoard?
  "True if the coordinate is a valid board coord, false otherwise"
  [c]
  (and (< 0 (first c))
       (> 6 (first c))
       (< 0 (second c))
       (> 6 (second c))))


(defn nborCoords
  "Get all the coordinates neighboring c"
  [c]
  (let [rows (range (- (first c) 1) (+ (first c) 2))
        cols (range (- (second c) 1) (+ (second c) 2))
        cells (math/cartesian-product rows cols)]

    (filterv #(and (onBoard? %) (not (= % c))) cells)))


(defn buildOn?
  "True if the coordinate can be built on, False if not"
  [b c]
  (> 4 (levelAt b c)))


(defn buildOn
  "Build the given coordinate one level higher, return new board"
  [b c]
  (let [curr-level (levelAt b c)
        new-level (inc curr-level)]

    (setLevelAt b c new-level)))


(defn moveTo?
  "True if piece can move from cf to ct, False otherwise. ASSUMING THE COORDINATES ARE NEIGHBORS"
  [b cf ct]
  (let [lf (levelAt b cf)
        lt (levelAt b ct)]
        (and (not= 4 lt)
             (<= lt (+ 1 lf)))
    ))