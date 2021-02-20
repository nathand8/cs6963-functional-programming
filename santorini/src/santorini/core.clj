(ns santorini.core
  (:require
   [santorini.turn :as turn]
   [santorini.game :as game]
   [santorini.strategy :as strategy])
  (:gen-class))

(defn outputGame
  "Prepare a game for output"
  [g]
  (if (game/inSetup? g)
    (game/toJSON g)
    (game/toJSON (game/incTurn g))))

(defn -main
  "Take a game board and return the next move"
  [& args]
  (loop []
    (let [g (game/fromJSON (read-line))]
      (if (game/inSetup? g)
        (println (outputGame (strategy/startingPositions g)))
        (println (outputGame (strategy/pickGame (turn/turnOutcomes g))))))
    (recur))
  )
