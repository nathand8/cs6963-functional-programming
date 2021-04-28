(ns layout-quickcheck-web.core
  (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [ring.middleware.cors :refer [wrap-cors]]
            [clojure.pprint :as pp]
            [clojure.string :as str]
            [clojure.data.json :as json]
            [layout-quickcheck-web.endpoints :as endpoints])
  (:gen-class))

(defroutes app-routes
  ;; (GET "/" [] simple-body-page)
  ;; (GET "/request" [] request-example)
  (GET "/bugs" [] endpoints/get-all-bugs)
  (route/not-found "Error, page not found!"))

(defn -main
  "This is our main entry point"
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "5000"))]
    ; Run the server with Ring.defaults middleware
    (server/run-server 
     (wrap-cors (wrap-defaults #'app-routes site-defaults) 
                :access-control-allow-origin [#".*"] 
                :access-control-allow-methods [:get :put :post :delete])
     {:port port})
    ; Run the server without ring defaults
    ;(server/run-server #'app-routes {:port port})
    (println (str "Running webserver at http:/127.0.0.1:" port "/"))))

