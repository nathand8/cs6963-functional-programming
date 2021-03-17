(ns santorini.turn-test
  (:require [clojure.test :refer :all]
            [santorini.core :refer :all]
            [santorini.board :as board]
            [santorini.game :as game]
            [santorini.turn :as turn]
            ))


(deftest piece-move-outcomes-test
  (testing "Returns all possible outcomes after a single piece moves"
    (let [init-game {:players [{:card :Apollo :tokens [[1,2],[4,4]]}
                               {:card :Artemis :tokens [[2,5],[3,5]]}]

                     :spaces [[0,0,0,0,2]
                              [3,3,3,0,0]
                              [1,0,0,3,0]
                              [0,0,3,0,0]
                              [0,0,0,1,4]]

                     :turn 18}

          ;; Outcome if the piece at (1, 1) is moved to the left
          g-left    {:players [{:card :Apollo :tokens [[1,1],[4,4]]}
                               {:card :Artemis :tokens [[2,5],[3,5]]}]

                     :spaces [[0,0,0,0,2]
                              [3,3,3,0,0]
                              [1,0,0,3,0]
                              [0,0,3,0,0]
                              [0,0,0,1,4]]

                     :turn 18}

          ;; Outcome if the piece at (1, 1) is moved to the right
          g-right   {:players [{:card :Apollo :tokens [[1,3],[4,4]]}
                               {:card :Artemis :tokens [[2,5],[3,5]]}]

                     :spaces [[0,0,0,0,2]
                              [3,3,3,0,0]
                              [1,0,0,3,0]
                              [0,0,3,0,0]
                              [0,0,0,1,4]]

                     :turn 18}
          move-outcomes [g-left, g-right]]

      (is (= 2 (count (turn/pieceMoveOutcomes init-game 0))))
      (is (= move-outcomes (turn/pieceMoveOutcomes init-game 0))))))


(deftest move-outcomes-test
  (testing "Returns all possible outcomes after either piece moves"
    (let [g {:players [{:card :Apollo :tokens [[1,2],[4,4]]}
                       {:card :Artemis :tokens [[2,5],[3,5]]}]

             :spaces [[0,0,0,0,2]
                      [3,3,3,0,0]
                      [1,0,0,3,0]
                      [0,0,3,0,0]
                      [0,0,0,1,4]]

             :turn 18}]
      
      ;; From (1, 2) there are 2 possible places to move
      ;; From (4, 4) there are 4 possible places to move
      ;; Total: 6
      (is (= 6 (count (turn/moveOutcomes g)))))))


(deftest piece-build-outcomes-test
  (testing "Returns all possible outcomes after a single piece builds"
    (let [init-game {:players [{:card :Apollo :tokens [[1,2],[4,4]]}
                               {:card :Artemis :tokens [[2,5],[3,5]]}]

                     :spaces [[0,0,0,0,2]
                              [4,4,4,0,0]
                              [1,0,0,3,0]
                              [0,0,3,0,0]
                              [0,0,0,1,4]]

                     :turn 18}

          ;; Outcome if the piece at (1, 1) builds to the left
          g-left    {:players [{:card :Apollo :tokens [[1,2],[4,4]]}
                               {:card :Artemis :tokens [[2,5],[3,5]]}]

                     :spaces [[1,0,0,0,2]
                              [4,4,4,0,0]
                              [1,0,0,3,0]
                              [0,0,3,0,0]
                              [0,0,0,1,4]]

                     :turn 18}

          ;; Outcome if the piece at (1, 1) builds to the right
          g-right   {:players [{:card :Apollo :tokens [[1,2],[4,4]]}
                               {:card :Artemis :tokens [[2,5],[3,5]]}]

                     :spaces [[0,0,1,0,2]
                              [4,4,4,0,0]
                              [1,0,0,3,0]
                              [0,0,3,0,0]
                              [0,0,0,1,4]]

                     :turn 18}
          build-outcomes [g-left, g-right]]
      
      (is (= 2 (count (turn/pieceBuildOutcomes init-game 0))))
      (is (= build-outcomes (turn/pieceBuildOutcomes init-game 0))))))


(deftest build-outcomes-test
  (testing "Returns all possible outcomes after either piece builds"
    (let [g {:players [{:card :Apollo :tokens [[1,2],[4,4]]}
                       {:card :Artemis :tokens [[2,5],[3,5]]}]

             :spaces [[0,0,0,0,2]
                      [3,3,3,0,0]
                      [1,0,0,3,0]
                      [0,0,3,0,0]
                      [0,0,0,1,4]]

             :turn 18}]
      
      ;; From (1, 2) there are 5 possible places to build
      ;; From (4, 4) there are 6 possible places to build
      ;; Total: 11
      (is (= 11 (count (turn/buildOutcomes g)))))))


(deftest turn-outcomes-test
  (testing "Returns all possible outcomes after either piece builds"
    (let [g {:players [{:card :Apollo :tokens [[1,2],[4,4]]}
                       {:card :Artemis :tokens [[2,5],[3,5]]}]

             :spaces [[0,0,0,0,2]
                      [4,4,4,4,0]
                      [1,4,3,4,0]
                      [0,4,3,0,0]
                      [0,4,0,1,4]]

             :turn 18}]
      
      ;; From (1, 2) there are 2 move spots -> 1 + 2 = 3 permutations
      ;; From (4, 4) there are 3 move spots -> 3 + 4 + 2 = 9 permutations
      ;; Total: 12
      (is (= 12 (count (turn/turnOutcomes g))))))
      
  (testing "Should only move on a winning move, not build"
    (let [g       {:players [{:card :Apollo :tokens [[1,1],[1,5]]}
                             {:card :Artemis :tokens [[2,5],[3,5]]}]

                   :spaces [[2,3,0,4,0]
                            [4,4,4,4,4]
                            [1,4,3,4,0]
                            [0,4,3,0,0]
                            [0,4,0,1,4]]

                   :turn 18}

          g-after {:players [{:card :Apollo :tokens [[1,2],[1,5]]}
                             {:card :Artemis :tokens [[2,5],[3,5]]}]

                   :spaces [[2,3,0,4,0]
                            [4,4,4,4,4]
                            [1,4,3,4,0]
                            [0,4,3,0,0]
                            [0,4,0,1,4]]

                   :turn 18}]
      
      ;; From (1, 1) there are 1 move spots, and it's a WIN (no build)
      ;; From (4, 4) there are 0 move spots
      ;; Total: 1
      (is (= 1 (count (turn/turnOutcomes g))))
      (is (= [g-after] (turn/turnOutcomes g))))))