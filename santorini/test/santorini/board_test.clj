(ns santorini.board-test
  (:require [clojure.test :refer :all]
            [santorini.core :refer :all]
            [santorini.board :as board]))

(deftest neighbor-coords-test
  (testing "Get the coordinates around the given coordinate"
    (let [c11 [1, 1]
          c11-neighbors [[1, 2], [2, 1], [2, 2]]
          c15 [1, 5]
          c15-neighbors [[1, 4], [2, 4], [2, 5]]
          c51 [5, 1]
          c51-neighbors [[4, 1], [4, 2], [5, 2]]
          c55 [5, 5]
          c55-neighbors [[4, 4], [4, 5], [5, 4]]]

      (is (= c11-neighbors (board/nborCoords c11)))
      (is (= c15-neighbors (board/nborCoords c15)))
      (is (= c51-neighbors (board/nborCoords c51)))
      (is (= c55-neighbors (board/nborCoords c55))))))


(deftest build-on?-test
  (testing "Check if the coordinate can be built on"
    (let [b [[0,0,0,0,2],[1,1,2,0,0],[1,0,0,3,0],[0,0,3,0,0],[0,0,0,1,4]]]

      (is (true? (board/buildOn? b [1, 1])))
      (is (true? (board/buildOn? b [1, 5])))
      (is (true? (board/buildOn? b [3, 4])))
      (is (false? (board/buildOn? b [5, 5])))
      )))


(deftest move-to?-test
  (testing "Check if a piece can move from one coordinate to another"
    (let [b [[0,0,0,0,2],[1,1,2,0,0],[1,0,0,3,0],[0,0,3,0,0],[0,1,2,3,4]]]

      (is (true? (board/moveTo? b [1, 1] [1, 2]))) ;; Same height - ground level
      (is (true? (board/moveTo? b [2, 2] [2, 3]))) ;; Up one level
      (is (true? (board/moveTo? b [5, 4] [4, 5]))) ;; Down multiple levels
      (is (false? (board/moveTo? b [2, 4] [2, 3]))) ;; Up 2 levels
      (is (false? (board/moveTo? b [4, 2] [4, 3]))) ;; Up 3 levels
      (is (false? (board/moveTo? b [5, 4] [5, 5]))) ;; Up to level 4
      )))

