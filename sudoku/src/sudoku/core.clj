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
        board (if use-rand-gen
                (generate block-width block-height)
                (solveBoardGuess (blank-board block-width block-height) block-width block-height 0))]
    (println "Here is your board:")
    (println (boardToString board))))





