(ns santorini.game-test
  (:require [clojure.test :refer :all]
            [santorini.core :refer :all]
            [santorini.game :as game]
            [clojure.string :as string]))

(deftest json-game-test
  (testing "Should be able to parse game from JSON and convert game to JSON"
    (let [json-game (slurp "test/santorini/files/multiline_board.json")
          game {:players [[[2,3],[4,4]],[[2,5],[3,5]]]
                :spaces [[0,0,0,0,2],[1,1,2,0,0],[1,0,0,3,0],[0,0,3,0,0],[0,0,0,1,4]]
                :turn 18}
          json-output (string/trim (slurp "test/santorini/files/singleline_board.json"))]

      (is (= game (game/fromJSON json-game)))
      (is (= json-output (game/toJSON game)))
    )
  )
)


(deftest get-all-pieces-coords-test
  (testing "Should be able to get all of the game piece coords"
    (let [g {:players [[[2,3],[4,4]],[[2,5],[3,5]]]
             :spaces [[0,0,0,0,2],[1,1,2,0,0],[1,0,0,3,0],[0,0,3,0,0],[0,0,0,1,4]]
             :turn 18}
          expected-coords [[2, 3], [4, 4], [2, 5], [3, 5]]]

      (is (= expected-coords (game/allPiecesCoords g))))))


(deftest get-own-pieces-coords-test
  (testing "Should be able to get OWN game piece coords (assume first position in player array is always own)"
    (let [g {:players [[[2,3],[4,4]],[[2,5],[3,5]]]
             :spaces [[0,0,0,0,2],[1,1,2,0,0],[1,0,0,3,0],[0,0,3,0,0],[0,0,0,1,4]]
             :turn 18}
          expected-coords [[2, 3], [4, 4]]]

      (is (= expected-coords (game/ownPiecesCoords g))))))


(deftest get-other-pieces-coords-test
  (testing "Should be able to get OTHER PLAYER game piece coords (assume first position in player array is always own)"
    (let [g {:players [[[2,3],[4,4]],[[2,5],[3,5]]]
             :spaces [[0,0,0,0,2],[1,1,2,0,0],[1,0,0,3,0],[0,0,3,0,0],[0,0,0,1,4]]
             :turn 18}
          expected-coords [[2, 5], [3, 5]]]

      (is (= expected-coords (game/otherPiecesCoords g))))))


(deftest piece-at-coord?-test
  (testing "Should be able to get OTHER PLAYER game piece coords (assume first position in player array is always own)"
    (let [g {:players [[[2,3],[4,4]],[[2,5],[3,5]]]
             :spaces [[0,0,0,0,2],[1,1,2,0,0],[1,0,0,3,0],[0,0,3,0,0],[0,0,0,1,4]]
             :turn 18}]

      (is (true? (game/pieceAtCoord? g [2, 3])))
      (is (nil? (game/pieceAtCoord? g [1, 1]))))))