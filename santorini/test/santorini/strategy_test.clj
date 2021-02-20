(ns santorini.strategy-test
  (:require [clojure.test :refer :all]
            [santorini.core :refer :all]
            [santorini.board :as board]
            [santorini.game :as game]
            [santorini.turn :as turn]
            [santorini.strategy :as strategy]
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