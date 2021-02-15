(ns santorini.game-test
  (:require [clojure.test :refer :all]
            [santorini.core :refer :all]
            [santorini.game :refer :all]))
(use 'clojure.string)

(deftest json-game-test
  (testing "Should be able to parse game from JSON and convert game to JSON"
    (let [json-game (slurp "test/santorini/files/multiline_board.json")
          game {:players [[[2,3],[4,4]],[[2,5],[3,5]]]
                :spaces [[0,0,0,0,2],[1,1,2,0,0],[1,0,0,3,0],[0,0,3,0,0],[0,0,0,1,4]]
                :turn 18}
          json-output (trim (slurp "test/santorini/files/singleline_board.json"))]

      (is (= game (gameFromJSON json-game)))
      (is (= json-output (gameToJSON game)))
    )
  )
)
