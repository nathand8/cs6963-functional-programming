(ns santorini.turn-test
  (:require [clojure.test :refer :all]
            [santorini.core :refer :all]
            [santorini.board :as board]
            [santorini.game :as game]
            [santorini.turn :as turn]
            ))


(deftest piece-move-outcomes-test
  (testing "Returns all possible outcomes after a single piece moves"
    (let [init-game {:players [[[1,2],[4,4]]
                               [[2,5],[3,5]]]

                     :spaces [[0,0,0,0,2]
                              [3,3,3,0,0]
                              [1,0,0,3,0]
                              [0,0,3,0,0]
                              [0,0,0,1,4]]

                     :turn 18}

          ;; Outcome if the piece at (1, 1) is moved to the left
          g-left    {:players [[[1,1],[4,4]]
                               [[2,5],[3,5]]]

                     :spaces [[0,0,0,0,2]
                              [3,3,3,0,0]
                              [1,0,0,3,0]
                              [0,0,3,0,0]
                              [0,0,0,1,4]]

                     :turn 18}

          ;; Outcome if the piece at (1, 1) is moved to the right
          g-right   {:players [[[1,3],[4,4]]
                               [[2,5],[3,5]]]

                     :spaces [[0,0,0,0,2]
                              [3,3,3,0,0]
                              [1,0,0,3,0]
                              [0,0,3,0,0]
                              [0,0,0,1,4]]

                     :turn 18}
          move-outcomes [g-left, g-right]]
      
      (is (= 2 (count (turn/pieceMoveOutcomes init-game [1, 2]))))
      (is (= move-outcomes (turn/pieceMoveOutcomes init-game [1, 2])))
      
      )))

