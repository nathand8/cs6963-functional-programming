(ns santorini.strategy
  (:require
   [santorini.util :refer [in?]]
   [santorini.board :as board]
   [santorini.game :as game]
   [santorini.strategy-constants :as stratconst]
   ))


(defn pickGame
  "Given a list of games, pick the one that is most advantageous to player at 0th index."
  [gs]
  (rand-nth gs))

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