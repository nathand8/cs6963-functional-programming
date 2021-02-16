(ns santorini.turn
  (:require
   [santorini.board :as board]
   [santorini.game :as game]))


(defn pieceMoveOutcomes
  "Get all move outcomes for a single piece, pi = piece index {0, 1}"
  [g pi]
  (let [cf (game/piToC g pi)]
    (mapv #(game/moveTo g cf %)
          (filterv #(game/moveTo? g cf %)
                   (board/nborCoords cf)))))

(defn moveOutcomes
  "Get all move outcomes for either piece"
  [g]
  (apply concat (map #(pieceMoveOutcomes g %) (game/ownPieceIndexes))))

(defn pieceBuildOutcomes
  "Get all build outcomes for a single piece, pi = piece index {0, 1}"
  [g pi]
  (let [cf (game/piToC g pi)]
    (mapv #(game/buildOn g %)
          (filterv #(game/buildOn? g %)
                   (board/nborCoords cf)))))

(defn buildOutcomes
  "Get all move outcomes for either piece"
  [g]
  (apply concat (map #(pieceBuildOutcomes g %) (game/ownPieceIndexes))))

(defn pieceTurnOutcomes
  "Get all outcomes for a single piece, pi = piece index {0, 1}"
  [g pi]
  (apply concat (map #(pieceBuildOutcomes % pi) 
       (pieceMoveOutcomes g pi))))

(defn turnOutcomes
  "Get all possible outcomes from a given turn"
  [g]
  (apply concat (map #(pieceTurnOutcomes g %) (game/ownPieceIndexes))))