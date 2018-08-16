(ns admin-panel.core
  (:require [clojure.java.jdbc :as sql]
            [admin_panel.users_db :as my-db]
            [admin-panel.users_controller :as users_ctrl]
            [ring.adapter.jetty :as jetty]
            [clojure.java.io :as io]
            [compojure.core :as compojure :refer (GET POST ANY DELETE PUT defroutes)]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.middleware.params :refer [wrap-params]]
            (compojure [handler :as handler]
                       [route :as route])))


(defroutes app-routes
  (GET "/users/all" [] (users_ctrl/get-all-users))
  (GET "/users/:id" [id] (users_ctrl/get-user-by-id id))
  (GET "/users" {params :params} (users_ctrl/get-user-by-username-and-password params))
  (DELETE "/users/:id" [id] (users_ctrl/delete-user-by-id id))
  (POST "/users" req  (users_ctrl/insert-new-user (-> req :params)))
  (PUT "/users/:id" req (users_ctrl/update-user (-> req :route-params :id) (-> req :params)))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site
      (wrap-json-response app-routes)
      (wrap-params app-routes)))

(defn -main []
  (jetty/run-jetty app {:port 3000}))

(defonce server (jetty/run-jetty #'app {:port 9000 :join? false}))


(.start server)




