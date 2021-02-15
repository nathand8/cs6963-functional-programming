(ns santorini.board
  (:require [clojure.math.combinatorics :as math]))


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


(defn cToI
  "Translate the coordinate (1-based) to indexes (0-based)"
  [c]
  (mapv #(- % 1) c))


(defn buildOn?
  "True if the coordinate can be built on, False if not"
  [b c]
  (> 4 (get-in b (cToI c))))
