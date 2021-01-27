(ns sudoku.solver
  (:require [sudoku.board :refer :all]))


;; Solve the board one round's worth
(defn removeCandidates [board block-width]
  (reduce #(removeFromAll %1 (first %2) (second %2) block-width)
          board
          (solvedCoords board)))


;; Solve the board until stuck
;; Return the solved or partially solved board
(defn solveBoard [initial-board block-width block-height]

  ;; Loop through the board
  ;; value-count helps to determine if any candidates were removed (shows progress)
  (loop [board initial-board
         value-count (getPossibleValueCount board)]

    (if (isAllSolved board) 
      board 
      (let [new-board (removeCandidates board block-width)
            new-value-count (getPossibleValueCount new-board)]
        (if (= value-count new-value-count)
          board ;; If no progress was made, stop and return the board
          (recur new-board new-value-count))))))
