(ns santorini.turn
  (:require
   [santorini.board :as board]
   [santorini.game :as game]))


(defn pieceMoveOutcomes
  "Get all the move outcomes for a single piece (starting at cp)"
  [g cp]
  (mapv #(game/moveTo g cp %)
        (filterv #(game/moveTo? g cp %)
                 (board/nborCoords cp))))

(defn moveOutcomes
  "Get all the move outcomes for either piece"
  [g]
  (apply concat (map #(pieceMoveOutcomes g %) (game/ownPiecesCoords g))))
