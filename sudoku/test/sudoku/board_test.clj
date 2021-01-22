(ns sudoku.board-test
  (:require [clojure.test :refer :all]
            [sudoku.core :refer :all]
            [sudoku.board :refer :all]))

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
