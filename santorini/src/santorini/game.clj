(ns santorini.game
  (:require [clojure.data.json :as json]))


(defn gameFromJSON [json-game-str]
  (json/read-str json-game-str :key-fn #(keyword %)))

(defn gameToJSON [g]
  (json/write-str g))

