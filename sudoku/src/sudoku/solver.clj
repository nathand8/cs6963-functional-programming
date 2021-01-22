(ns sudoku.solver
  (:require [sudoku.board :refer :all]))

(defn solveBoard [initial-board block-width block-height]
  (loop [board initial-board]
    (if (isAllSolved board) 
      board 
      (recur 
       (reduce 
        #(removeFromAll %1 (first %2) (second %2) block-width) 
        board 
        (solvedCoords board))))))





;; Gotta learn a while loop
;; 


;; A loop that sums the numbers 10 + 9 + 8 + ...

;; Set initial values count (cnt) from 10 and down
(loop [sum 0 cnt 10]
  ;; If count reaches 0 then exit the loop and return sum
  (if (= cnt 0)
    sum
    ;; Otherwise add count to sum, decrease count and 
    ;; use recur to feed the new values back into the loop
    (do
      (println "sum" sum "cnt" cnt)
      (recur (+ cnt sum) (dec cnt)))))