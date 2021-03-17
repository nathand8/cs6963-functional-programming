(ns santorini.game
  (:require [clojure.data.json :as json]
            [santorini.board :as board]
            [santorini.util :refer [in?]]))

;; JSON Format:
;; Setup 1:play-random with [{"card":"Apollo"},{"card":"Artemis"}]
;; Setup 2:play-random with [{"card":"Artemis"},{"card":"Apollo","tokens":[[1,3],[1,4]]}]
;; {"players":[{"card":"Apollo","tokens":[[1,3],[1,4]]},{"card":"Artemis","tokens":[[4,2],[5,4]]}],"spaces":[[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0]],"turn":0}
;; Move 1:play-random
;; in 0.000200439453125 secs
;; {"players":[{"card":"Artemis","tokens":[[4,2],[5,4]]},{"card":"Apollo","tokens":[[1,2],[1,4]]}],"spaces":[[0,0,1,0,0],[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0]],"turn":1}
;; Move 2:play-random
;; in 0.000405029296875 secs
;; {"players":[{"card":"Apollo","tokens":[[1,2],[1,4]]},{"card":"Artemis","tokens":[[3,3],[5,4]]}],"spaces":[[0,0,1,0,0],[0,0,0,0,0],[0,1,0,0,0],[0,0,0,0,0],[0,0,0,0,0]],"turn":2}

;; Game Format:
;; {
;;   :players [
;;       {
;;         :card :Apollo
;;         :tokens [[2,3],[4,4]]
;;       },
;;       {
;;         :card :Artemis
;;         :tokens [[2,5],[3,5]]
;;       }
;;     ]
;;   :spaces [[0,0,0,0,2],[1,1,2,0,0],[1,0,0,3,0],[0,0,3,0,0],[0,0,0,1,4]]
;;   :turn 18
;;   :state {
;;     :levelCeiling 4 ;; A piece cannot move to this level (Prometheus) (starts as 4)
;;     :pieceIndex -1 ;; Which piece is using the turn (starts as unset)
;;     :levelsMoved 0 ;; tracks how many levels have been moved, eg -2 is going from level 2 to level 0 (Pan and playing against Minotaur) (starts as unset)
;;     :buildSpace [1,3] ;; tracks which space was built on (Demeter and Hephastus) (starts as unset)
;;   }
;; }

(defn cardToKeyword [key value]
  (if (= key :card) (keyword value) value))

(defn inSetup?
  "Return True if the game is in setup mode (placing pieces), False otherwise"
  [g]
  (nil? (get g :spaces)))

(defn addBlankState [g]
  (assoc g :state {}))

(defn stripState [g]
  (if (inSetup? g)
    g
    (dissoc g :state)))

(defn fromJSON [json-game-str]
  (let [g (json/read-str json-game-str :key-fn keyword :value-fn cardToKeyword)]
    (if (inSetup? g) g (addBlankState g))))

(defn toJSON [g]
  (json/write-str (stripState g)))

(defn playerCard [p]
  (get p :card))

(defn playerTokens [p]
  (get p :tokens))

(defn setupAllPiecesCoords
  "Get all the coordinates of the pieces when the game is in setup mode"
  [g]
  (remove nil? (apply concat (map #(playerTokens %) g))))

(defn setupOwnPiecesCoords
  "Get all the coordinates of own pieces when the game is in setup mode"
  [g]
  (playerTokens (first g)))

(defn setupPosTaken
  "Given a game in setup, return true if the suggested position is taken"
  [g c]
  (if (= 0 (count g))
    false
    (in? (setupAllPiecesCoords g) c)))

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
  (apply concat (map #(playerTokens %) (players g))))

(defn ownPlayer [g]
  (first (players g)))

(defn ownPiecesCoords [g]
  (playerTokens (ownPlayer g)))

(defn ownPieceIndexes
  "Get the indexes into own pieces array"
  []
  (range 2))

(defn piToC
  "'PieceIndexToCoord' - Given a piece index pi {0, 1} - return own piece coordinates"
  [g pi]
  (get (ownPiecesCoords g) pi))

(defn otherPlayer [g]
  (second (players g)))

(defn otherPiecesCoords [g]
  (playerTokens (otherPlayer g)))

(defn pieceAtCoord? [g c]
  (in? (allPiecesCoords g) c))

(defn setupReplaceOwnTokens
  "Given a game in setup, set own tokens to t"
  [g t]
  [(assoc (first g) :tokens t) (second g)])

(defn buildOn? [g c]
  (and (board/buildOn? (spaces g) c)
       (not (pieceAtCoord? g c))))

(defn buildOn [g c]
  (assoc g :spaces (board/buildOn (spaces g) c)))

(defn moveTo? [g cf ct]
  (and (board/moveTo? (spaces g) cf ct)
       (not (pieceAtCoord? g ct))))

(defn movesFrom
  "All the possible coordinates to move to from c"
  [g c]
  (filterv #(moveTo? g c %) (board/nborCoords c)))

(defn movePiece-players
  "Create copy of :players, move cf to ct"
  [g cf ct]
  [(assoc (ownPlayer g) :tokens (replace {cf ct} (ownPiecesCoords g))) (otherPlayer g)])

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

