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
    (let [g-init [{:card :Apollo}, {:card :Artemis}]
          g-out (strategy/startingPositions g-init)
          own-pieces-out (game/playerTokens (first g-out))]

      ;; Expecting something like
      ;; [[[1, 2], [2, 4]]]

      (is (true? (game/inSetup? g-out)))
      (is (= 2 (count own-pieces-out)))
      (is (= 2 (count (first own-pieces-out))))
      (is (= 2 (count (second own-pieces-out))))))

  (testing "Can pick starting position starting from one player"
    (let [g-init [{:card :Apollo}, {:card :Artemis :tokens [[1, 2], [2, 4]]}]
          g-out (strategy/startingPositions g-init)
          own-pieces-out (game/playerTokens (second g-out))]

      ;; Expecting something like
      ;; [[[1, 2], [2, 4]], [[4, 4], [1, 5]]]

      (is (true? (game/inSetup? g-out)))
      (is (= (second g-init) (second g-out)))
      (is (= 2 (count own-pieces-out)))
      (is (= 2 (count (first own-pieces-out))))
      (is (= 2 (count (second own-pieces-out)))))))

(deftest rateWinCondition-test
  (testing "A piece on a level three should be a win condition"
    (let [g {:players [{:card :Apollo :tokens [[1,1],[3,4]]},{:card :Artemis :tokens [[1,3],[1,4]]}]
             :spaces [[0,0,0,0,2],[1,1,2,0,0],[1,0,0,3,0],[0,0,3,0,0],[0,0,0,1,4]]
             :turn 18}]
      (is (== stratconst/VALUE_WIN (strategy/rateWinCondition g 0)))
      (is (== 0 (strategy/rateWinCondition g 1)))
      )))

(deftest rateFeature-test
  (testing "Given a feature, get rating for self and (optionally) other player"
    (let [g {:players [{:card :Apollo :tokens [[1,1],[3,4]]},{:card :Artemis :tokens [[1,3],[1,4]]}]
                 :spaces [[0,0,0,0,2],[1,1,2,0,0],[1,0,0,3,0],[0,0,3,0,0],[0,0,0,1,4]]
                 :turn 18}]
      (is (== stratconst/VALUE_WIN (reduce + (strategy/rateFeature g 0 strategy/rateWinCondition false))))
      (is (== stratconst/VALUE_WIN (reduce + (strategy/rateFeature g 0 strategy/rateWinCondition true))))
      (is (== 0 (reduce + (strategy/rateFeature g 1 strategy/rateWinCondition false))))
      (is (== (* stratconst/OTHER_PLAYER_MULTIPLIER (- stratconst/VALUE_WIN)) (reduce + (strategy/rateFeature g 1 strategy/rateWinCondition true)))))))

(deftest pickGame-test
  (testing "Should always pick the game where self wins"
    (let [g-win   {:players [{:card :Apollo :tokens [[1,1],[3,4]]},{:card :Artemis :tokens [[1,3],[1,4]]}]
                   :spaces [[0,0,0,0,2],[1,1,2,0,0],[1,0,0,3,0],[0,0,3,0,0],[0,0,0,1,4]]
                   :turn 18}
          g-other {:players [{:card :Apollo :tokens [[1,1],[3,5]]},{:card :Artemis :tokens [[1,3],[1,4]]}]
                   :spaces [[0,0,0,0,2],[1,1,2,0,0],[1,0,0,3,0],[0,0,3,0,0],[0,0,0,1,4]]
                   :turn 18}]
      (is (= g-win (strategy/pickGame [g-win g-other])))
      (is (= g-win (strategy/pickGame [g-other g-win]))))))

(deftest rateNextMoveL3-test
  (testing "Next move to L3"
    (let [g {:players [{:card :Apollo :tokens [[1,3],[1,1]]},{:card :Artemis :tokens [[2,1],[2,2]]}]
             :spaces [[0,0,2,3,0],[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0]]
             :turn 18}]
      (is (== stratconst/VALUE_LEVEL_3_NEXT_MOVE (strategy/rateNextMoveL3 g 0)))
      (is (== 0 (strategy/rateNextMoveL3 g 1)))
      )))