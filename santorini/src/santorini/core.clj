(ns santorini.core
  (:require
   [santorini.turn :as turn]
   [santorini.game :as game])
  (:gen-class))

(defn -main
  "Take a game board and return the next move"
  [& args]
  (loop []
    (println
     (game/toJSON
      (game/incTurn
       (rand-nth
        (turn/turnOutcomes
         (game/fromJSON
          (read-line)))))))
    (recur))
  )
