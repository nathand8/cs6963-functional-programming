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

(defn fromJSON [json-game-str]
  (json/read-str json-game-str :key-fn #(keyword %)))

(defn toJSON [g]
  (json/write-str g))

(defn players [g]
  (get g :players))

(defn spaces [g]
  (get g :spaces))

(defn allPiecesCoords [g]
  (apply concat (players g)))

(defn ownPiecesCoords [g]
  (first (players g)))

(defn otherPiecesCoords [g]
  (second (players g)))

(defn pieceAtCoord? [g c]
  (in? (allPiecesCoords g) c))

(defn buildOn? [g c]
  (and (board/buildOn? (spaces g) c)
       (not (pieceAtCoord? g c))))

(defn buildOn [g c]
  (assoc g :spaces (board/buildOn (spaces g) c)))

(defn moveTo? [g cf ct]
  (and (board/moveTo? (spaces g) cf ct)
       (not (pieceAtCoord? g ct))))

(defn movePiece-players
  "Create copy of :players, move cf to ct"
  [g cf ct]
  [(replace {cf ct} (ownPiecesCoords g)) (otherPiecesCoords g)])

(defn moveTo [g cf ct]
  (assoc g :players (movePiece-players g cf ct)))

