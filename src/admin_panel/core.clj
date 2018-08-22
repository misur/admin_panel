(ns admin-panel.core
  (:require [clojure.java.jdbc :as sql]
            [admin_panel.users_db :as my-db]
            [admin-panel.users_controller :as users_ctrl]
            [ring.adapter.jetty :as jetty]
            [clojure.java.io :as io]
            [compojure.core :as compojure :refer (GET POST ANY DELETE PUT defroutes)]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.cookies :refer [wrap-cookies]]
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



(defn wrap-spy [handler spyname include-body]
  (fn [request]
    (println  request )
    (let [response (handler request)]
        response)))

(def app
  (handler/site
    (-> app-routes
        (wrap-cookies )
        (wrap-spy "what the handler sees" true)
        (wrap-json-response )
        (wrap-params))))

(defn -main []
  (jetty/run-jetty app {:port 3000}))

(defonce server (jetty/run-jetty #'app {:port 9000 :join? false}))


(.start server)




