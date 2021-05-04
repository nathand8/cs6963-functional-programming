(ns layout-quickcheck-web.endpoints
  (:require [clojure.data.json :as json]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(def bug-folder (io/file "../layout-quickcheck-web"))

;; Open a file
;; (first (map #(.getPath %)
;;         (filter #(clojure.string/ends-with? % "data.json")
;;                 (file-seq bug-folder))))

(defn get-all-bugs [req]
  (let [bug-folder (io/file "../layout-quickcheck-bugs")
        all-bugs-json (json/write-str (map #(json/read-str (slurp %) :key-fn keyword)
                                           (filter #(string/ends-with? % "data.json")
                                                   (file-seq bug-folder))))]
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body all-bugs-json}))
