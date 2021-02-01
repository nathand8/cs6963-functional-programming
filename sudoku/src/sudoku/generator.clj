(ns sudoku.generator
  (:require
   [sudoku.board :refer :all]
   [sudoku.solver :refer :all]))


;; Create a "blank" board with the given block size
(defn blank-board [block-width block-height]
  (let [board-length (* block-width block-height)]
    (repeat board-length (repeat board-length (range 1 (+ 1 board-length))))))
  
(blank-board 2 2)


(defn generate [block-width block-height]

  ;; Create a completely unsolved board with block size (block-width x block-height)
  (blank-board block-width block-height)

  ;; Fold-Loop until...

    ;; Choose a "random" unsolved cell, pick a "random" value from that cell

    ;; Try solving the board

      ;; If the board is "broken", throw away this path

      ;; If not, proceed with this one
  )


(defn rep [n]
  (lazy-seq (cons n (rep (+ n 1)))))

(let [s (rep 1)]
  (+ 10 (first (take 1 s)))
  (+ 10 (first (take 1 s)))
  (+ 10 (first (take 1 s)))
  (+ 10 (first (take 1 s))))

(take-while #(< % 20) (rep 1))
