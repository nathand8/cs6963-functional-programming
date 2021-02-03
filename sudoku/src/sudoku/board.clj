(ns sudoku.board)
(use '[clojure.string :only (join)])

;; Board structure:
;; [
;;   [  [1, 2, 3],  [3],  [2]],
;;   [  [1],  [2, 3],  [1, 2, 3]],
;; ]
;; 
;; The outer array contains the rows
;; The inner arrays contain cells within a row
;; The cells contain an array of possible values


;; This function takes:
;; 1. Vector with items
;; 2. A function that signifies then to change the item (true) and when to leave it (false)
;; 3. A function that changes the item when it needs to be changed
(defn mapWhen [items fnIndexFilter fnItemApply]
  (into [] (map-indexed
            (fn [item-index item]
              (if (fnIndexFilter item-index)
                (fnItemApply item)
                item))
            items)))


;; This function takes:
;; 1. Board (vector of vectors)
;; 2. fnIndexFilter - Function takes row-index, col-index => change the item (true), leave it (false)
;; 3. fnItemApply - Function that the item and returns a similar item
(defn walkBoard [items fnIndexFilter fnItemApply]
  (into [] (for [row-index (range (count items))]
             (into [] (for [col-index (range (count (get items row-index)))]
                        (let [item (get (get items row-index) col-index)]
                          (if (fnIndexFilter row-index col-index)
                            (fnItemApply item)
                            item)))))))


;; Remove a value from the cell
;; A cell is simply a vector of integers
;; Return a new vector with all the values except val
(defn removeFromCell [cell, val]
  (filterv (fn [cell-val] (not= val cell-val)) cell))


;; Remove the value at the coordinate from the other cells on the row
;; board - the entire board structure
;; m - the row where the cell is located
;; n - the column where the cell is located
;; * It's assumed that the cell at (m, n) has at least one value in it
(defn removeFromRow [board, m, n]
  (let [val-to-remove (first (get (get board m) n))]
    (mapWhen board
                       (fn [row-index] (= m row-index)) ;; only apply to the row where the cell is at
                       (fn [row] (mapWhen row
                                                    (fn [col-index] (not= n col-index)) ;; apply to every cell in the row except cell n
                                                    (fn [cell] (removeFromCell cell val-to-remove)))))))


;; Remove the value at the coordinate from the other cells on the column
;; 
(defn removeFromCol [board, m, n]
  (let [val-to-remove (first (get (get board m) n))]
    (mapWhen board
                       (fn [row-index] (not= m row-index)) ;; apply to every row except the one with the cell
                       (fn [row] (mapWhen row
                                                    (fn [col-index] (= n col-index)) ;; apply only to the column with the cell in it
                                                    (fn [cell] (removeFromCell cell val-to-remove)))))))


;; isSameBlock
;; Takes two points and the block width and height and determines if the two points
;;   are in the same block
(defn isSameBlock [m1 n1 m2 n2 block-width block-height]
  (and (= (quot m1 block-height) (quot m2 block-height))
       (= (quot n1 block-width) (quot n2 block-width))))

;; isSameCoord
;; Takes two points and determines if they are the same point
(defn isSameCoord [m1 n1 m2 n2] 
  (and (= m1 m2) (= n1 n2)))


;; Remove the value at the coordinate from the other cells in the 
(defn removeFromBlock [board m n block-width]
  (let [val-to-remove (first (get (get board m) n))
        block-height (/ (count board) block-width)]
    (walkBoard board
               (fn [row-index col-index] (and (isSameBlock m n row-index col-index block-width block-height)
                                              (not (isSameCoord m n row-index col-index))))
               (fn [cell] (removeFromCell cell val-to-remove)))))


;; Remove the value at coord (m n) from the column, row, and block
(defn removeFromAll [board m n block-width]
  (removeFromBlock (removeFromCol (removeFromRow board m n) m n) m n block-width))


;; getPossibleValueCount [board]
;; Return the total "possible" values for a board
(defn getPossibleValueCount [board]
  (count (flatten board)))


;; isAllSolved [board]
;; The board is "solved" when each cell only has one possible value
(defn isAllSolved [board]
  (let [board-size (count board)
        cell-count (* board-size board-size)]
    (= cell-count (getPossibleValueCount board))))


;; Checks if the board has any cells with no possible options
(defn empty-cells [board]
  (if (sequential? (first board))
    (some #(empty-cells %) board)
    (= 0 (count board))))


;; solvedCoords board => vector of the coords for cells with single possible values
(defn solvedCoords [board]

  ;; Filter - only return the items where the count is 1
  (remove nil?

          (apply concat ;; Flattens one level

                 ;; Loop through the entire board and get [m n] for each cell with only one option
                 (for [row-index (range (count board))]
                   (for [col-index (range (count board))]

                    ;; If there is only one option, return the coordinates
                    ;; Otherwise, returns nil 
                     (if (= 1 (count (get (get board row-index) col-index)))
                       [row-index col-index]
                       nil))))))


(defn boardToString [board]
  (join " "
        (flatten
         (map ;; Map the board

          ;; For each row, get the cells plus a newline
          (fn [row]
            (cons "\n"
                  (map

                   (fn [cell]
                     (if (= 1 (count cell))
                       ;; For cells with a single value, display the value
                       (first cell)
                       ;; For cells with more or less, display underscore
                       "_"))

                   row)))
          board))))
































(def l (list (list (list 5))))

(quot 1 2)

(remove #(= 3 %) (list 1 2 3 4 5))

(comp 1)

(let [a 1 b (+ a 1)] b)

(get [1 2 3 4] 2)


(map (fn [cell] (remove #(= 3 %) cell)) [[1 2] [2 3] [3 4] [4 5]])

(filterv (fn [item] (not= 3 item)) [1 2 3])

(into [] (map-indexed (fn [index item] [:li index item]) ["rattata" "pidgey" "spearow"]))

(into [] (for [x (range 6)] x))

(let [a (atom 0)
      b 100
      v [1 2 3]
      f (fn [] (swap! a inc))]
  (f)
  (f)
  (f)
  (println a)
  (get a 3)
  (inc b)
  (inc b)
  (conj v 4)
  (conj v 4)
  v)

(let [board [[[1] [2 ]]
             [[2] [1]]]
      board-size (count board)]
  (= (* board-size board-size)
     (reduce #(+ %1 %2) 0 (flatten
                           (for [row board]
                             (for [cell row] (count cell)))))))


(let [board [[[1] [2]]
             [[2] [1]]]
      board-size (count board)
      cell-count (* board-size board-size)]
  (= cell-count (count (flatten board))))



(let [board [[[1]       [1 2 3 4] [1 2 3 4] [1 2 3 4]]
             [[1 2 3 4] [1 2 3 4] [1 2 3 4] [1 2 3 4]]
             [[1 2 3 4] [1 2 3 4] [1 2 3 4] [1 2 3 4]]
             [[1 2 3 4] [1 2 3 4] [1 2 3 4] [1 2 3 4]]]
      m 0
      n 0
      block-width 2
      board-size (count board)]
  (removeFromBlock (removeFromCol (removeFromRow board m n) m n) m n block-width))


(let [board [[[1]       [1 2 3 4] [1 2 3 4] [1 2 3 4]]
             [[1 2 3 4] [1 2 3 4] [1 2 3 4] [1 2 3 4]]
             [[1 2 3 4] [1 2 3 4] [1 2 3 4] [1 2 3 4]]
             [[1 2 3 4] [1 2 3 4] [1 2 3 4] [1 2 3 4]]]
      m 0
      n 0
      block-width 2
      board-size (count board)]
  (for [[row row-index] board]
    [row row-index]))


;; getSingles board => vector of the coords for cells with single possible values
;; 
(let [board [[[1]       [1 2 3 4] [1 2 3 4] [1 2 3 4]]
             [[1 2 3 4] [1 2 3 4] [1 2 3 4] [1 2 3 4]]
             [[1 2 3 4] [1 2 3 4] [1 2 3 4] [1 2 3 4]]
             [[1 2 3 4] [1 2 3 4] [1 2 3 4] [1 2 3 4]]]]
          (apply concat (for [row-index (range (count board))]
                             (for [col-index (range (count board))]
                               [row-index col-index (count (get (get board row-index) col-index))]))))

(filter (fn [[_ _ c]] (= 1 c))
        
`([0 0 1]
 [0 1 4]
 [0 2 4]
 [0 3 4]
 [1 0 4]
 [1 1 4]
 [1 2 4]
 [1 3 4]
 [2 0 4]
 [2 1 4]
 [2 2 4]
 [2 3 4]
 [3 0 4]
 [3 1 4]
 [3 2 4]
 [3 3 4]))


;; solvedCoords board => vector of the coords for cells with single possible values
;; 
(let [board [[[1]       [1 2 3 4] [1 2 3 4] [1 2 3 4]]
             [[1 2 3 4] [1 2 3 4] [1 2 3 4] [1 2 3 4]]
             [[1 2 3 4] [1 2 3 4] [1 2 3 4] [1 2 3 4]]
             [[1 2 3 4] [1 2 3 4] [1 2 3 4] [1 2 3 4]]]]
  (map (fn [[m n count]] [m n])
       (filter (fn [[m n count]] (= 1 count))
               (apply concat (for [row-index (range (count board))]
                               (for [col-index (range (count board))]
                                 [row-index col-index (count (get (get board row-index) col-index))]))))))

;; reduce using the remove* functions
(let [board [[[1]       [1 2 3 4] [1 2 3 4] [1 2 3 4]]
             [[1 2 3 4] [1 2 3 4] [1 2 3 4] [1 2 3 4]]
             [[1 2 3 4] [2]       [1 2 3 4] [1 2 3 4]]
             [[1 2 3 4] [1 2 3 4] [1 2 3 4] [1 2 3 4]]]]

  (reduce #(conj %1 %2) [] (solvedCoords board)))


;; reduce using the remove* functions
(let [board [[[1]       [1 2 3 4] [1 2 3 4] [1 2 3 4]]
             [[1 2 3 4] [1 2 3 4] [1 2 3 4] [1 2 3 4]]
             [[1 2 3 4] [2]       [1 2 3 4] [1 2 3 4]]
             [[1 2 3 4] [1 2 3 4] [1 2 3 4] [1 2 3 4]]]]

  (reduce #(removeFromAll %1 (first %2) (second %2) 2) board (solvedCoords board)))


(conj [] 1 2)

(let [[row-index col-index] [0 1]]
       (conj [] row-index col-index))