(ns sudoku.board)

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
(defn mapvFilterByIndex [items fnIndexFilter fnItemApply]
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
    (mapvFilterByIndex board
                       (fn [row-index] (= m row-index)) ;; only apply to the row where the cell is at
                       (fn [row] (mapvFilterByIndex row
                                                    (fn [col-index] (not= n col-index)) ;; apply to every cell in the row except cell n
                                                    (fn [cell] (removeFromCell cell val-to-remove)))))))


;; Remove the value at the coordinate from the other cells on the column
;; 
(defn removeFromCol [board, m, n]
  (let [val-to-remove (first (get (get board m) n))]
    (mapvFilterByIndex board
                       (fn [row-index] (not= m row-index)) ;; apply to every row except the one with the cell
                       (fn [row] (mapvFilterByIndex row
                                                    (fn [col-index] (= n col-index)) ;; apply only to the column with the cell in it
                                                    (fn [cell] (removeFromCell cell val-to-remove)))))))


;; isSameBlock
;;
(defn isSameBlock [m1 n1 m2 n2 block-width block-height]
  (and (= (quot m1 block-height) (quot m2 block-height)) 
       (= (quot n1 block-width) (quot n2 block-width))))

;; Remove the value at the coordinate from the other cells in the 
(defn removeFromBlock [board, m, n, block-width]
  (let [val-to-remove (first (get (get board m) n))
        block-height (/ (count board) block-width)]
    (walkBoard board
               (fn [row-index col-index] (and (isSameBlock m n row-index col-index block-width block-height) 
                                              (not (and (= row-index m)(= col-index n))))) 
               (fn [cell] (removeFromCell cell val-to-remove)))))

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

