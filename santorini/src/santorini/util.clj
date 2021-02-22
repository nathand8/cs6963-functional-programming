(ns santorini.util)

(defn in?
  "true if coll contains elm"
  [coll elm]
  (some #(= elm %) coll))

(defn level3in?
  "Return true if at least one of the levels is 3"
  [levels]
  (in? levels 3))

