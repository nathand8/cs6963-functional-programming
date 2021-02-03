(ns sudoku.generator-test
  (:require [clojure.test :refer :all]
            [sudoku.core :refer :all]
            [sudoku.board :refer :all]
            [sudoku.generator :refer :all]
            [sudoku.solver :refer :all]))

(deftest generator-generate-2x2
  (testing "Create a board with 2x2 blocks"
    (let
     [block-width 2
      block-height 2
      generated-board (generate block-width block-height)]

      (is (> 64 (count (flatten generated-board)))) ;; Some cells are solved
      (is (<= 16 (count (flatten generated-board)))) ;; Not all cells are solved
      (is (isAllSolved (solveBoard generated-board block-width block-height))) ;; Board be solvable using our solver
      )))

(deftest generator-generate-1x1
  (testing "Create a board with 1x1 blocks (1x1 board)"
    (let
     [block-width 1
      block-height 1
      generated-board (generate block-width block-height)]

      (is (= [[[1]]] generated-board)))))
  
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


(deftest generator-pickCell
  (testing "Picks a random value for a cell"
    (let
     [initial-board [[[1 2] [1 2]]
                     [[1 2] [1]]
                     [[1 2] [1 2]]
                     [[1 2] [1 2]]]

      unpicked-board (pickCell initial-board 1 1) ;; picking a cell with only one value should not change the board
      m 0
      n 1
      picked-board (pickCell initial-board m n)]

      (is (= 1 (count (nth (nth picked-board m) n))))
      (is (= initial-board unpicked-board)))))


(deftest generator-randCoords
  (testing "Should generate random coordinates within board")
    (let
     [board [[[1 2 3 4] [1 2 3 4] [1 2 3 4] [1 2 3 4]]
             [[1 2 3 4] [1 2 3 4] [1 2 3 4] [1 2 3 4]]
             [[1 2 3 4] [1 2 3 4] [1 2 3 4] [1 2 3 4]]
             [[1 2 3 4] [1 2 3 4] [1 2 3 4] [1 2 3 4]]]
      coords (randCoords board)]

             (is (<= 0 (first coords)))
             (is (>= 3 (first coords)))
             (is (<= 0 (second coords)))
             (is (>= 3 (second coords)))))




