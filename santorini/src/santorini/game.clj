(ns santorini.game
  (:require [clojure.data.json :as json]
            [santorini.board :as board ]
            [santorini.util :refer [in?]]))

;; Game Format:
;; {
;;   :players [[[2,3],[4,4]],[[2,5],[3,5]]]
;;   :spaces [[0,0,0,0,2],[1,1,2,0,0],[1,0,0,3,0],[0,0,3,0,0],[0,0,0,1,4]]
;;   :turn 18
;; }

(defn gameFromJSON [json-game-str]
  (json/read-str json-game-str :key-fn #(keyword %)))

(defn gameToJSON [g]
  (json/write-str g))

(defn players [g]
  (get g :players))

(defn spaces [g]
  (get g :spaces))

(defn getAllPiecesCoords [g]
  (apply concat (players g)))

(defn getOwnPiecesCoords [g]
  (first (players g)))

(defn getOtherPiecesCoords [g]
  (second (players g)))

(defn pieceAtCoord? [g c]
  (in? (getAllPiecesCoords g) c))

(defn buildOn? [g c]
  (board/buildOn? (spaces g) c))

