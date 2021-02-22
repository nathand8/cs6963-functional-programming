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
  (json/read-str json-game-str :key-fn keyword))

(defn toJSON [g]
  (json/write-str g))

(defn setupPosTaken
  "Given a game in setup, return true if the suggested position is taken"
  [g c]
  (if (= 0 (count g))
    false
    (in? (apply concat g) c)))

(defn inSetup?
  "Return True if the game is in setup mode (placing pieces), False otherwise"
  [g]
  (not (map? g)))

(defn players [g]
  (get g :players))

(defn spaces [g]
  (get g :spaces))

(defn turn [g]
  (get g :turn))

(defn otherPlayerIndex 
  "Given a player index, get the other player's index"
  [i]
  (mod (+ 1 i) 2))

(defn playerAtIndex
  "Get the player at the given index"
  [g player-index]
  (get (players g) player-index))

;; (defn playerAtOtherIndex
;;   "Get the player NOT at the given index"
;;   [g player-index]
;;   (playerAtIndex g (otherPlayerIndex player-index)))

(defn levelAt 
  "Get the board level at a given coordinate"
  [g c]
  (board/levelAt (spaces g) c))

(defn levelsAt
  "Given a game and list of coordinates, return the board levels at those coordinates"
  [g coords]
  (map #(levelAt g %) coords))

(defn allPiecesCoords [g]
  (apply concat (players g)))

(defn ownPiecesCoords [g]
  (first (players g)))

(defn ownPieceIndexes
  "Get the indexes into own pieces array"
  []
  (range 2))

(defn piToC 
  "'PieceIndexToCoord' - Given a piece index pi {0, 1} - return own piece coordinates"
  [g pi]
  (get (ownPiecesCoords g) pi))

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

(defn swap-players 
  "Creat copy of :players, swap order"
  [g]
  (assoc g :players (reverse (players g))))

(defn incTurn
  "Increment the turn. Swap the player order and increment turn counter."
  [g]
  (assoc (swap-players g) :turn (+ 1 (turn g))))

