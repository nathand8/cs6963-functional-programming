(ns santorini.strategy-constants)

;; Giving the other player a good move on their next turn should be slightly worse than
;; having a good move for ourselves on our next turn... because the opponent has the next turn
(def OTHER_PLAYER_MULTIPLIER 1.1)

;; Static list of the "inner ring" positiong
(def INNER_RING [[2,2], [2,3], [2,4], [3,2], [3,4], [4,2], [4,3], [4,4]])

;; List of acceptable beginning positions
(def BEG_POS INNER_RING)

;; Value of winning
(def VALUE_WIN 1000)
