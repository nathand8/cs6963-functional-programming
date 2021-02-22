(ns santorini.strategy-test
  (:require [clojure.test :refer :all]
            [santorini.core :refer :all]
            [santorini.board :as board]
            [santorini.game :as game]
            [santorini.turn :as turn]
            [santorini.strategy :as strategy]
            [santorini.strategy-constants :as stratconst]
            ))


(deftest starting-positions-test
  (testing "Can pick starting position starting from zero players"
    (let [g-init []
          g-out (strategy/startingPositions g-init)]

      ;; Expecting something like
      ;; [[[1, 2], [2, 4]]]

      (is (true? (game/inSetup? g-out)))
      (is (= 1 (count g-out)))
      (is (= 2 (count (first g-out))))
      (is (= 2 (count (first (first g-out)))))
      (is (= 2 (count (second (first g-out)))))))

  (testing "Can pick starting position starting from one player"
    (let [g-init [[[1, 2], [2, 4]]]
          g-out (strategy/startingPositions g-init)]

      ;; Expecting something like
      ;; [[[1, 2], [2, 4]], [[4, 4], [1, 5]]]

      (is (true? (game/inSetup? g-out)))
      (is (= 2 (count g-out)))
      (is (= 2 (count (second g-out))))
      (is (= 2 (count (first (second g-out)))))
      (is (= 2 (count (second (second g-out))))))))

(deftest rateWinCondition-test
  (testing "A piece on a level three should be a win condition"
    (let [g {:players [[[1,1],[3,4]],[[1,3],[1,4]]]
             :spaces [[0,0,0,0,2],[1,1,2,0,0],[1,0,0,3,0],[0,0,3,0,0],[0,0,0,1,4]]
             :turn 18}]
      (is (== stratconst/VALUE_WIN (strategy/rateWinCondition g 0)))
      (is (== 0 (strategy/rateWinCondition g 1)))
      )))

(deftest rateFeature-test
  (testing "Given a feature, get rating for self and (optionally) other player"
    (let [g {:players [[[1,1],[3,4]],[[1,3],[1,4]]]
                 :spaces [[0,0,0,0,2],[1,1,2,0,0],[1,0,0,3,0],[0,0,3,0,0],[0,0,0,1,4]]
                 :turn 18}]
      (is (== stratconst/VALUE_WIN (reduce + (strategy/rateFeature g 0 strategy/rateWinCondition false))))
      (is (== stratconst/VALUE_WIN (reduce + (strategy/rateFeature g 0 strategy/rateWinCondition true))))
      (is (== 0 (reduce + (strategy/rateFeature g 1 strategy/rateWinCondition false))))
      (is (== (* stratconst/OTHER_PLAYER_MULTIPLIER (- stratconst/VALUE_WIN)) (reduce + (strategy/rateFeature g 1 strategy/rateWinCondition true)))))))

(deftest pickGame-test
  (testing "Should always pick the game where self wins"
    (let [g-win   {:players [[[1,1],[3,4]],[[1,3],[1,4]]]
                   :spaces [[0,0,0,0,2],[1,1,2,0,0],[1,0,0,3,0],[0,0,3,0,0],[0,0,0,1,4]]
                   :turn 18}
          g-other {:players [[[1,1],[3,5]],[[1,3],[1,4]]]
                   :spaces [[0,0,0,0,2],[1,1,2,0,0],[1,0,0,3,0],[0,0,3,0,0],[0,0,0,1,4]]
                   :turn 18}]
      (is (= g-win (strategy/pickGame [g-win g-other])))
      (is (= g-win (strategy/pickGame [g-other g-win]))))))