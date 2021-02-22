(ns santorini.strategy
  (:require
   [santorini.util :refer [in?]]
   [santorini.board :as board]
   [santorini.game :as game]
   [santorini.strategy-constants :as stratconst]
   ))

(defn rateWinCondition
  [g self-index]
  (let [ownPiecesCoords (game/playerAtIndex g self-index)]
    (if (in? (game/levelsAt g ownPiecesCoords) 3)
      stratconst/VALUE_WIN
      0)))

(defn rateFeature
  "Rate the game on a given feature. If flip = true, also rate the game from the persepctive of the opponent.
   Returns a list of values."
  [g self-index feature flip]
  (let [selfRating (feature g self-index)
        opponentRating (if flip (feature g (game/otherPlayerIndex self-index)) 0)
        weightedOpponentRating (* stratconst/OTHER_PLAYER_MULTIPLIER (- opponentRating))]
    [selfRating weightedOpponentRating]))

(defn rateGame
  "Using known strategies, give the game a rating. (Assume player at index 0 is self)"
  [g self-index]
  (let [values [(rateFeature g self-index rateWinCondition true)]]
    (reduce + (flatten values))))

(defn pickGame
  "Given a list of games, pick the one that is most advantageous to player at 0th index."
  [gs]
  (second (last 
           (sort-by first (map #(vector (rateGame % 0) %) gs)))))

(defn startingPositions
  "Pick the starting positions for this player. g - player array"
  [g]
  (loop [player []]
    (if (= 2 (count player))
      (conj g player)
      (let [p (rand-nth stratconst/BEG_POS)]
        (if (or (game/setupPosTaken g p) (in? player p))
          (recur player)
          (recur (conj player p)))))))