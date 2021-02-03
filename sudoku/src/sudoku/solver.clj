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


(defn solveBoardGuess [board block-width block-height index]
  (let [board-size (* block-width block-height)
        end-index (* board-size board-size)
        m-index (quot index board-size)
        n-index (mod index board-size)
        cell (get-in board [m-index n-index])]

    ;; Stop after reaching the last index
    (if (>= index end-index)

      ;; Return the solved board
      board

      ;; Solve this index, then try the next index
      (first
       (remove nil?
               (for [cell-val cell
                     :let [new-board-raw (assoc-in board [m-index n-index] (vector cell-val))
                           new-board (solveBoard new-board-raw block-width block-height)]]
                 (if (empty-cells board)
                   nil
                   (solveBoardGuess new-board block-width block-height (inc index)))))))))
