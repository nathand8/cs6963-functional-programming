(ns sudoku.generator-test
  (:require [clojure.test :refer :all]
            [sudoku.core :refer :all]
            [sudoku.board :refer :all]
            [sudoku.generator :refer :all]
            [sudoku.solver :refer :all]))

(deftest generator-generate
  (testing "Create a board with 2x2 blocks"
    (let
     [block-width 2
      block-height 2
      generated-board (generate block-width block-height)]

      (is (> 64 (count (flatten generated-board)))) ;; Some cells are solved
      (is (< 16 (count (flatten generated-board)))) ;; Not all cells are solved
      (is (isAllSolved (solveBoard generated-board block-width block-height))) ;; Board be solvable using our solver
      )))

(deftest generator-blank-board
  (testing "Create a blank board with 3x2 blocks")
    (let
     [block-width 3
      block-height 2
      generated-board (blank-board block-width block-height)]

      (is (= 216 (count (flatten generated-board))))
      (is (= 6 (count generated-board)))
      (is (= 6 (count (first generated-board))))
      (is (= [1 2 3 4 5 6] (first (first generated-board))))))

