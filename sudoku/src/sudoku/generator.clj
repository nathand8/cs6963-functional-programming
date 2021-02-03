(ns sudoku.generator
  (:require
   [sudoku.board :refer :all]
   [sudoku.solver :refer :all]))


;; Create a "blank" board with the given block size
(defn blank-board [block-width block-height]
  (let [board-length (* block-width block-height)]
    (vec (repeat board-length (vec (repeat board-length (vec (range 1 (+ 1 board-length)))))))))

(blank-board 3 2)

;; Pick a random value for the cell if the cell isn't already solved
(defn pickCell [board m n]
  (assoc-in board [m n] (vector (rand-nth (get-in board [m n])))))


(defn nextQualifyingCoord [board single-index starting-index fnQualify]
  (let
   [board-size (count board)
    end-index (* board-size board-size)
    m-index (quot single-index board-size)
    n-index (mod single-index board-size)]
    (if (fnQualify (get-in board [m-index n-index]))
      [m-index n-index]
      (let [next-index (mod (inc single-index) end-index)]
        (if (= next-index starting-index)
          nil
          (nextQualifyingCoord board next-index starting-index fnQualify))))))


;; Pick random coordinates for the board
(defn randBlankCoords [board]
  (let [board-size (count board)
        end-index (* board-size board-size)
        rand-index (rand-int end-index)
        qualify-fn (fn [cell] (< 1 (count cell)))]
    (nextQualifyingCoord board rand-index rand-index qualify-fn)))


;; Pick random coordinates for the board
(defn randSolvedCoords [board]
  (let [board-size (count board)
        end-index (* board-size board-size)
        rand-index (rand-int end-index)
        qualify-fn (fn [cell] (= 1 (count cell)))]
    (nextQualifyingCoord board rand-index rand-index qualify-fn)))


;; Generate a board with block dimensions
(defn generate [block-width block-height]

  (loop [board-col (vector (blank-board block-width block-height))]

    (let [board (first (take 1 board-col))]

      ;; If the board is solved, stop here
      (if (isAllSolved board)
        board

        ;; Pick a new random coordinate to solve
        (let [rand-coords (randBlankCoords board)
              rand-m (first rand-coords)
              rand-n (second rand-coords)]

          ;; If there's empty cells (no possible values), this is a bad route to take, back out of it
          (if (empty-cells board)
            (recur (rest board-col))
            (recur (cons (solveBoard (pickCell board rand-m rand-n) block-width block-height) board-col))))))))


;; Generate an "unsolved" cell for the board
(defn unsolvedCell [board]
  (vec (range 1 (+ 1 (count board)))))


;; Remove n random coords from board
(defn removeRandomValues [board n]
  (let [rand-coords (repeatedly n #(randSolvedCoords board))]
    (reduce #(assoc-in %1 [(first %2) (second %2)] (unsolvedCell %1)) board rand-coords)))
