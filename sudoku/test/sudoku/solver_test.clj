(ns sudoku.solver-test
  (:require [clojure.test :refer :all]
            [sudoku.core :refer :all]
            [sudoku.board :refer :all]
            [sudoku.solver :refer :all]))


(deftest solver-solveBoard
  (testing "Solving a simple 1x2 block board"
    (let
     [unsolved-board [[[1] [1 2]]
                      [[2 1] [1]]]
      solved-board [[[1] [2]]
                    [[2] [1]]]
      block-width 1
      block-height 2]

      (is (= solved-board (solveBoard unsolved-board block-width block-height)))))

  (testing "Solving a mostly-filled-in 3x2 block board"
    ;; Puzzle pulled from https://www.cs.utah.edu/~mflatt/cs6963/sudoku.html
    (let
     [unsolved-board [[[1] [2] [3]   [4] [5] [6]]
                      [[4] [5] [6]   [1] [2] [3]]

                      [[2] [3] [4]   [5] [6] [1]]
                      [[5] [6] [1]   [2] [3] [1 2 3 4 5 6]]

                      [[3] [4] [5]   [6] [1] [1 2 3 4 5 6]]
                      [[6] [1] [2]   [3] [4] [5]]]

      solved-board   [[[1] [2] [3]   [4] [5] [6]]
                      [[4] [5] [6]   [1] [2] [3]]

                      [[2] [3] [4]   [5] [6] [1]]
                      [[5] [6] [1]   [2] [3] [4]]

                      [[3] [4] [5]   [6] [1] [2]]
                      [[6] [1] [2]   [3] [4] [5]]]
      block-width 3
      block-height 2]

      (is (= solved-board (solveBoard unsolved-board block-width block-height))))))
