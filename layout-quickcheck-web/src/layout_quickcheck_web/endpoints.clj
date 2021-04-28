(ns layout-quickcheck-web.endpoints)

(defn get-all-bugs [req]
  {:status 200
   :headers {"Content-Type" "application/json"}}
  "You got all the bugs!")