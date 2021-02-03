(ns sudoku.core
  (:require
   [sudoku.board :refer :all]
   [sudoku.solver :refer :all]
   [sudoku.generator :refer :all]))
(use '[clojure.string :only (join)])


(defn getNumberInput [prompt]
  (println prompt)
  (Integer/parseInt (read-line)))


(defn getBooleanInput [prompt]
  (println (join " " [prompt "(Y/y/Yes/Anything or leave empty if not)"]))
  (not (= "" (read-line))))


(defn main []
  (let [block-width (getNumberInput "What block-width would you like?")
        block-height (getNumberInput "What block-height would you like?")
        use-rand-gen (getBooleanInput "Would you like to use random generation?")
        remove-values (getNumberInput "How many values would you like to remove from the solved board?")
        board (if use-rand-gen
                (generate block-width block-height)
                (solveBoardGuess (blank-board block-width block-height) block-width block-height 0))
        puzzle-board (removeRandomValues board remove-values)
        solved-board (solveBoard puzzle-board block-width block-height)]

    (println "Here is the generated, completed board:")
    (println (boardToString board))
    (println)

    (println "Here is the puzzle board:")
    (println (boardToString puzzle-board))
    (println)
    
    (println "Here is the solved board:")
    (println (boardToString solved-board))
    (println)

    ))





