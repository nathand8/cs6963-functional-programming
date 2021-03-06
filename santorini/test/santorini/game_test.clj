(ns santorini.game-test
  (:require [clojure.test :refer :all]
            [santorini.core :refer :all]
            [santorini.game :as game]
            [clojure.string :as string]))

(deftest json-game-test
  (testing "Should be able to parse game from JSON and convert game to JSON"
    (let [json-game (slurp "test/santorini/files/multiline_board.json")
          game {:players [{ :card :Apollo :tokens [[2,3],[4,4]] },
                          { :card :Artemis :tokens [[2,5],[3,5]] }]
                :spaces [[0,0,0,0,2],[1,1,2,0,0],[1,0,0,3,0],[0,0,3,0,0],[0,0,0,1,4]]
                :turn 18
                :state {}}
          json-output (string/trim (slurp "test/santorini/files/singleline_board.json"))]

      (is (= game (game/fromJSON json-game)))
      (is (= json-output (game/toJSON game)))
    )
  )
  (testing "Should be able to parse a game in setup mode from JSON and convert game back to JSON"
    (let [json-game "[{\"card\":\"Apollo\"},{\"card\":\"Artemis\"}]"
          game [{:card :Apollo} {:card :Artemis}]
          json-output "[{\"card\":\"Apollo\"},{\"card\":\"Artemis\"}]"]

      (is (= game (game/fromJSON json-game)))
      (is (= json-output (game/toJSON game)))
    )
  )
  (testing "Should be able to parse a game in setup mode with one player from JSON and convert game back to JSON"
    (let [json-game "[{\"card\":\"Apollo\",\"tokens\":[[1,2],[4,4]]},{\"card\":\"Artemis\"}]"
          game [{:card :Apollo :tokens [[1, 2], [4, 4]]} {:card :Artemis}]
          json-output "[{\"card\":\"Apollo\",\"tokens\":[[1,2],[4,4]]},{\"card\":\"Artemis\"}]"]

      (is (= game (game/fromJSON json-game)))
      (is (= json-output (game/toJSON game)))
    )
  )
  (testing "Should get rid of the game 'state' when serializing to JSON"
    (let [game {:players [{:card :Apollo :tokens [[2,3],[4,4]]}
                          {:card :Artemis :tokens [[2,5],[3,5]]}]
                :spaces [[0,0,0,0,2],[1,1,2,0,0],[1,0,0,3,0],[0,0,3,0,0],[0,0,0,1,4]]
                :turn 18
                :state {:levelCeiling 3 :pieceIndex 0 :levelsMoved -2 :buildSpace [1,3]}}
          json-output (string/trim (slurp "test/santorini/files/singleline_board.json"))]

      (is (= json-output (game/toJSON game)))
    )
  )
)

(deftest setup-test
  (testing "Should detect when a board is in the setup phase of game"
    (let [g1 [{:card :Apollo}, {:card :Artemis}]
          g2 [{:card :Apollo}, {:card :Artemis :tokens [[1, 2], [2, 2]]}]
          g3 [{:card :Apollo :tokens [[1, 2], [2, 2]]} {:card :Artemis :tokens [[4, 4], [3, 4]]}]
          g4 {:players [{:card :Apollo :tokens [[2,3],[4,4]]} {:card :Artemis :tokens [[2,5],[3,5]]}]
             :spaces [[0,0,0,0,2],[1,1,2,0,0],[1,0,0,3,0],[0,0,3,0,0],[0,0,0,1,4]]
             :turn 18}
          ]
      (is (true? (game/inSetup? g1)))
      (is (true? (game/inSetup? g2)))
      (is (true? (game/inSetup? g3)))
      (is (false? (game/inSetup? g4))))))

(deftest setup-Pos-Taken-test
  (testing "Should detect when a position is taken, given a board in setup mode"
    (is (true? (game/setupPosTaken [{:card :Apollo}, {:card :Artemis :tokens [[1, 2], [2, 2]]}] [1, 2])))
    (is (true? (game/setupPosTaken [{:card :Apollo}, {:card :Artemis :tokens [[1, 2], [2, 2]]}] [2, 2])))
    (is (true? (game/setupPosTaken [{:card :Apollo :tokens [[3, 2]]}, {:card :Artemis :tokens [[1, 2], [2, 2]]}] [3, 2])))
    (is (false? (boolean (game/setupPosTaken [{:card :Apollo}, {:card :Artemis :tokens [[1, 2], [2, 2]]}] [4, 4]))))
    (is (false? (boolean (game/setupPosTaken [{:card :Apollo :tokens [[3, 2]]}, {:card :Artemis :tokens [[1, 2], [2, 2]]}] [5, 5]))))
    (is (false? (boolean (game/setupPosTaken [{:card :Apollo}, {:card :Artemis}] [5, 5]))))))


(deftest get-all-pieces-coords-test
  (testing "Should be able to get all of the game piece coords"
    (let [g {:players [{:card :Apollo :tokens [[2,3],[4,4]]},{:card :Artemis :tokens [[2,5],[3,5]]}]
             :spaces [[0,0,0,0,2],[1,1,2,0,0],[1,0,0,3,0],[0,0,3,0,0],[0,0,0,1,4]]
             :turn 18}
          expected-coords [[2, 3], [4, 4], [2, 5], [3, 5]]]

      (is (= expected-coords (game/allPiecesCoords g))))))


(deftest get-own-pieces-coords-test
  (testing "Should be able to get OWN game piece coords (assume first position in player array is always own)"
    (let [g {:players [{:card :Apollo :tokens [[2,3],[4,4]]},{:card :Artemis :tokens [[2,5],[3,5]]}]
             :spaces [[0,0,0,0,2],[1,1,2,0,0],[1,0,0,3,0],[0,0,3,0,0],[0,0,0,1,4]]
             :turn 18}
          expected-coords [[2, 3], [4, 4]]]

      (is (= expected-coords (game/ownPiecesCoords g))))))


(deftest get-other-pieces-coords-test
  (testing "Should be able to get OTHER PLAYER game piece coords (assume first position in player array is always own)"
    (let [g {:players [{:card :Apollo :tokens [[2,3],[4,4]]},{:card :Artemis :tokens [[2,5],[3,5]]}]
             :spaces [[0,0,0,0,2],[1,1,2,0,0],[1,0,0,3,0],[0,0,3,0,0],[0,0,0,1,4]]
             :turn 18}
          expected-coords [[2, 5], [3, 5]]]

      (is (= expected-coords (game/otherPiecesCoords g))))))


(deftest piece-at-coord?-test
  (testing "Should determine if a piece is at coord c"
    (let [g {:players [{:card :Apollo :tokens [[2,3],[4,4]]},{:card :Artemis :tokens [[2,5],[3,5]]}]
             :spaces [[0,0,0,0,2],[1,1,2,0,0],[1,0,0,3,0],[0,0,3,0,0],[0,0,0,1,4]]
             :turn 18}]

      (is (true? (game/pieceAtCoord? g [2, 3])))
      (is (nil? (game/pieceAtCoord? g [1, 1]))))))


(deftest build-on?-test
  (testing "Pieces can't build on other pieces"
    (let [g {:players [{:card :Apollo :tokens [[3,3],[4,4]]},{:card :Artemis :tokens [[2,5],[3,5]]}]
             :spaces [[0,0,0,0,2],[1,1,2,0,0],[1,0,0,3,0],[0,0,3,0,0],[0,0,0,1,4]]
             :turn 18}]

      (is (true? (game/buildOn? g [4, 3])))
      (is (false? (game/buildOn? g [4, 4])))
      (is (false? (game/buildOn? g [5, 5])))
      )))


(deftest build-on-test
  (testing "Build on a spot"
    (let [g-init {:players [{:card :Apollo :tokens [[1,1],[1,2]]},{:card :Artemis :tokens [[2,5],[3,5]]}]
             :spaces [[0,0,0,0,2],[1,1,2,0,0],[1,0,0,3,0],[0,0,3,0,0],[0,0,0,3,4]]
             :turn 18}
          g-out {:players [{:card :Apollo :tokens [[1,1],[1,2]]},{:card :Artemis :tokens [[2,5],[3,5]]}]
             :spaces [[0,0,1,0,2],[1,1,2,0,0],[1,0,0,3,0],[0,0,3,0,0],[0,0,0,3,4]]
             :turn 18}]

      (is (= g-out (game/buildOn g-init [1, 3])))
      )))


(deftest move-to?-test
  (testing "Pieces can't move to the same spot as another piece"
    (let [g {:players [{:card :Apollo :tokens [[1,1],[1,2]]},{:card :Artemis :tokens [[2,5],[3,5]]}]
             :spaces [[0,0,0,0,2],[1,1,2,0,0],[1,0,0,3,0],[0,0,3,0,0],[0,0,0,3,4]]
             :turn 18}]

      (is (true? (game/moveTo? g [1, 1] [2, 2])))
      (is (false? (game/moveTo? g [1, 1] [1, 2])))
      (is (false? (game/moveTo? g [5, 4] [5, 5])))
      )))
  

(deftest move-to-test
  (testing "Move a piece to a new spot"
    (let [g-init {:players [{:card :Apollo :tokens [[1,1],[1,2]]},{:card :Artemis :tokens [[2,5],[3,5]]}]
             :spaces [[0,0,0,0,2],[1,1,2,0,0],[1,0,0,3,0],[0,0,3,0,0],[0,0,0,3,4]]
             :turn 18}
          g-out {:players [{:card :Apollo :tokens [[1,1],[1,3]]},{:card :Artemis :tokens [[2,5],[3,5]]}]
             :spaces [[0,0,0,0,2],[1,1,2,0,0],[1,0,0,3,0],[0,0,3,0,0],[0,0,0,3,4]]
             :turn 18}]

      (is (= g-out (game/moveTo g-init [1, 2] [1, 3])))
      )))


(deftest int-turn-test
  (testing "Increment turn"
    (let [g-init {:players [{:card :Apollo :tokens [[1,1],[1,2]]},{:card :Artemis :tokens [[2,5],[3,5]]}]
             :spaces [[0,0,0,0,2],[1,1,2,0,0],[1,0,0,3,0],[0,0,3,0,0],[0,0,0,3,4]]
             :turn 18}
          g-out {:players [{:card :Artemis :tokens [[2,5],[3,5]]},{:card :Apollo :tokens [[1,1],[1,2]]}] ;; Swap player order
             :spaces [[0,0,0,0,2],[1,1,2,0,0],[1,0,0,3,0],[0,0,3,0,0],[0,0,0,3,4]]
             :turn 19}] ;; Inc turn counter

      (is (= g-out (game/incTurn g-init)))
      )))