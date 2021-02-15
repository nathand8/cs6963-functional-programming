(ns santorini.game-test
  (:require [clojure.test :refer :all]
            [santorini.core :refer :all]
            [santorini.game :refer :all]))

(deftest ten-test
  (testing "Should return 10"
    (is (= 10 (getTen)))))
