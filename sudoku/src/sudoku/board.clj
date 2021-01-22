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


;; Remove a value from the cell
;; A cell is simply a vector of integers
;; Return a new vector with all the values except val
(defn removeFromCell [cell, val]
  (filterv (fn [cell-val] (not= val cell-val)) cell))


;; Remove the value at the coordinate from the other cells on the row
(defn removeFromRow [board, m, n]
  (let [val-to-remove (first (get (get board m) n))]

    ;; Loop through the rows
    (into [] (map-indexed
              (fn [row-index row]
                (if (not= row-index m)
                  row
                ;; Loop through the columns
                  (map-indexed
                   (fn [cell-index cell]
                     (if (= cell-index n)
                       cell
                       (removeFromCell cell val-to-remove)))
                   row)))
              board)))
    )


  ; (let [row (get board m)
  ;       val-to-remove (first (get row n)) ; define the value to remove
  ;       remove-val-from-cells (fn [cell] (remove #(= val-to-remove %) cell)) ; define a function that matches that value 
  ;       new-row (mapv remove-val-from-cells row)]
  ;   ; NEXT: Need to add that item back to the original cell
  ;   ; OR: Skip removing it from that cell
  ;   (assoc board m new-row)))

(def l (list (list (list 5))))

(remove #(= 3 %) (list 1 2 3 4 5))

(comp 1)

(let [a 1 b (+ a 1)] b)

(get [1 2 3 4] 2)


(map (fn [cell] (remove #(= 3 %) cell)) [[1 2] [2 3] [3 4] [4 5]])

(filterv (fn [item] (not= 3 item)) [1 2 3])

(into [] (map-indexed (fn [index item] [:li index item]) ["rattata" "pidgey" "spearow"]))

