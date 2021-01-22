(ns sudoku.board-test
  (:require [clojure.test :refer :all]
            [sudoku.core :refer :all]
            [sudoku.board :refer :all]))

(deftest board-mapvFilterByIndex
  (testing "mapvFilterByIndex should take a vector, fnFilterItem, fn. It should only apply"
    (let
     [initial-vector [10 20 30 40 50]
      index-filter (fn [i] (even? i))
      item-map (fn [value] (+ value 2))
      expected-vector [12 20 32 40 52]]

      (is (=
           (mapvFilterByIndex initial-vector index-filter item-map)
           expected-vector)))))

(deftest board-walkBoard
  (testing "walkBoard should take a vector of vectors, filterFn and applyFn. It should only applyFn when filterFn comes back true"
    (let
     [initial-board [[1 2 3 4 5]
                     [6 7 8 9 10]]
      index-filter (fn [row-index col-index] (= 2 (+ row-index col-index)))
      apply-fn (fn [value] (+ value 10))
      expected-board [[1 2 13 4 5]
                     [6 17 8 9 10]]]
      (is (= expected-board (walkBoard initial-board index-filter apply-fn))))))

(deftest board-removeFromRow
  (testing "The value from the coordinate should be removed from that row"
    (let
     [initial-board [[[1 2] [2 3] [1]]
                     [[1] [2 3] [1 2]]]
      expected-board [[[2] [2 3] [1]]
                      [[1] [2 3] [1 2]]]]
      (is (=
           (removeFromRow initial-board 0 2)
           expected-board)))))

(deftest board-removeFromCol
  (testing "The value from the coordinate should be removed from that column"
    (let
     [initial-board [[[1 2] [2 3] [1]]
                     [[1] [2 3] [1 2]]]
      expected-board [[[1 2] [2 3] [1]]
                      [[1] [2 3] [2]]]]
      (is (=
           (removeFromCol initial-board 0 2)
           expected-board)))))

(deftest board-removeFromBlock
  (testing "The value from the coordinate should be removed from that block (1x2 block)"
    (let
     [initial-board [[[1] [1 2]]
                     [[1 2] [1]]]
      expected-board [[[1] [1 2]]
                     [[2] [1]]]]
      (is (=
           (removeFromBlock initial-board 0 0 1) ;; block-width = 1 ; blocks are 1x2 
           expected-board))))
  (testing "The value from the coordinate should be removed from that block (2x1 block)"
    (let
     [initial-board [[[1] [1 2]]
                     [[1 2] [1]]]
      expected-board [[[1] [2]]
                     [[1 2] [1]]]]
      (is (=
           (removeFromBlock initial-board 0 0 2) ;; block-width = 2 ; blocks are 2x1
           expected-board)))))

(deftest board-isSameBlock
  (testing "Given two sets of coordinates, determine if they are in the same block"

    ;; block-width = 3
    ;; block-height = 2
    ;; first coord (1, 5)
    ;; second coord (1, 6)
    ;;
    ;; x x x  x x x  x x x 
    ;; x x x  x x o  o x x 
    ;;
    ;; x x x  x x x  x x x 
    ;; x x x  x x x  x x x 
    ;;
    ;; x x x  x x x  x x x 
    ;; x x x  x x x  x x x 
    (is (false? (isSameBlock 1 5 1 6 3 2)))
    (is (false? (isSameBlock 1 5 2 5 3 2)))

    ;; block-width = 3
    ;; block-height = 2
    ;; first coord (1, 5)
    ;; second coord (0, 5)
    ;;
    ;; x x x  x x o  x x x 
    ;; x x x  x x o  x x x 
    ;;
    ;; x x x  x x x  x x x 
    ;; x x x  x x x  x x x 
    ;;
    ;; x x x  x x x  x x x 
    ;; x x x  x x x  x x x 
    (is (true? (isSameBlock 1 5 0 5 3 2)))
    (is (true? (isSameBlock 1 5 0 3 3 2)))

    ;; The same two coords should always be in the same block
    (is (true? (isSameBlock 2 5 2 5 2 3)))))
