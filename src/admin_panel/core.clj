(ns admin-panel.core
  (:require [clojure.java.jdbc :as sql]
            [admin_panel.users_db :as my-db]
            [ring.adapter.jetty :as jetty]
            [clojure.java.io :as io]
            [compojure.core :as compojure :refer (GET POST ANY defroutes)]
            (compojure [handler :as handler]
                       [route :as route])))



(defroutes app-routes
  (GET "/" [] "Hello World by misur")
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))

(defn -main []
  (jetty/run-jetty app {:port 3000}))

(defonce server (jetty/run-jetty #'app {:port 9000 :join? false}))


(.stop server)
