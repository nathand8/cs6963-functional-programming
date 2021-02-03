(ns sudoku.generator
  (:require
   [sudoku.board :refer :all]
   [sudoku.solver :refer :all]))


;; Create a "blank" board with the given block size
(defn blank-board [block-width block-height]
  (let [board-length (* block-width block-height)]
    (vec (repeat board-length (vec (repeat board-length (vec (range 1 (+ 1 board-length)))))))))

(blank-board 3 2)

;; Pick a random value for the cell if the cell isn't already solved
(defn pickCell [board m n]
  (assoc-in board [m n] (vector (rand-nth (get-in board [m n])))))


;; Pick random coordinates for the board
(defn randCoords [board]
  (let
   [board-size (count board)
    m (rand-int board-size)
    n (rand-int board-size)]
    [m n]))


;; Generate a board with block dimensions
(defn generate [block-width block-height]

  (loop [board-col (vector (blank-board block-width block-height))
         countdown 10000]

    (let [board (first (take 1 board-col))
          rand-coords (randCoords board)
          rand-m (first rand-coords)
          rand-n (second rand-coords)]

      (if (or (isAllSolved board) (>= 0 countdown))
        (doall board)

        (if (empty-cells board)
          (recur (rest board-col) countdown)
          (recur
           (cons (solveBoard (pickCell board rand-m rand-n) block-width block-height) board-col)
           (dec countdown)))))))


;; Blank > Solved (3, 5)
;;
;;
;; get-in
;; 
;; assoc-in
;; 
;; reshape (see link)
;; 

(get-in [ [[1 1] [2 2]]  [[3 3] [4 4]] ] [1 1])

(assoc-in [ [[1 1] [2 2]]  [[3 3] [4 4]] ] [1 1] [5 5])


(defn reshape [m & shape]
  (reduce (fn [vecs dim]
            (reduce #(conj %1 (subvec vecs %2 (+ dim %2)))
                    [] (range 0 (count vecs) dim)))
          (vec (flatten m)) (reverse shape)))
;;  (reshape [1 [2 3 4] 5 6 7 8] 2 2) => [[[1 2] [3 4]] [[5 6] [7 8]]]


;; flow:
;; 
;; loop
  ;; take top board from the stack
  ;; if it has empty cells, drop that board from the stack, loop (same countdown)
  ;; if not, 
    ;; pick a new cell at random to "guess",
    ;; solve the board as much as possible
    ;; add that board to the top of the stack



(take 1 [200])

(cons [1 2 3] [4 5 6])





;; Take a vector and return a lazy-seq of vectors
(defn vec-gen [v]
  (map #(replace {% 0} v) v)
  ;; (repeat (count v) v)
  )

;; Should return ([0 2 3 4], [1 0 3 4], [1 2 0 4], [1 2 3 0])
(vec-gen [1 2 3 4])
(vec-gen [1 2 1 2])


;; Should return |col| copies where ecah copy has a mapping function applied to a single item
(defn mapCopy [mapFn col]
  (map-indexed (fn [index _]
                 (map-indexed (fn [copy-index copy-item] (if (= copy-index index) (mapFn copy-item) copy-item)) col)) col))


;; This should return                   ([0 2 3 4], [1 0 3 4], [1 2 0 4], [1 2 3 0])
(mapCopy (fn [a] (* a 0)) [1 2 3 4])

;; Cell level
(map (fn [cell-val] (vec [cell-val])) [1 2 3 4])

;; Row level                                                                 (((0 2) (3 4)), ((1 0) (3 4)), ((1 2) (0 4)), ((1 2) (3 0)))
(mapCopy (fn [cell] (map (fn [cell-val] (vec [cell-val])) cell)) [[1 2] [3 4]])


;; Take a board and return boards that pick all possible numbers
(defn all-picks [board]

  ;; (map-indexed (fn [row-index row]
  ;;                (map-indexed (fn [cell-index cell]
  ;;                               (map (fn [cell-val]
  ;;                                      (vec [cell-val]))
  ;;                                    cell))
  ;;                             row))
  ;;      board)
  ;; )

  (mapCopy (fn [row]
             (mapCopy (fn [cell]
                        (map #(vec [%]) cell)) row)) board))






(let
 [board [[[1 2]]]]

  (all-picks board))



(reduce #(cons %2 %1) [4 5 6] (reverse [1 2 3]))

(let [board [[[1 2 3] [1 2 3]]
             [[1 2 3] [1 2 3]]
             [[1 2 3] [1 2 3]]]]
  (loop [board-col board]
    ()))

(map (map [[1 2] [3 4]]))


(take 1 (cons 10 [1 2 3]))




(defn rep [n]
  (lazy-seq (cons n (rep (+ n 1)))))

(let [s (rep 1)]
  (+ 10 (first (take 1 s)))
  (+ 10 (first (take 1 s)))
  (+ 10 (first (take 1 s)))
  (+ 10 (first (take 1 s))))

(take-while #(< % 20) (rep 1))
