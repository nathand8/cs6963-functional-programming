(ns santorini.core
  (:require
   [santorini.turn :as turn]
   [santorini.game :as game]
   [santorini.strategy :as strategy]
   [clojure.java.io :as cjio])
  (:gen-class))

(defn stdout
  "Send a string to stdout"
  [s]
  (cjio/copy s *out*)
  (cjio/copy "\n" *out*)
  (.flush *out*))

(defn outputGame
  "Prepare a game for output"
  [g]
  (if (game/inSetup? g)
    (stdout (game/toJSON g))
    (stdout (game/toJSON (game/incTurn g)))))

(defn -main
  "Take a game board and return the next move"
  [& args]
  (loop []
    (let [g (game/fromJSON (read-line))]
      (if (game/inSetup? g)
        (outputGame (strategy/startingPositions g))
        (outputGame (strategy/pickGame (turn/turnOutcomes g)))))
    (recur))
  )
