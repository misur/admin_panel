(ns admin-panel.core
  (:require [clojure.java.jdbc :as sql]
            [admin_panel.users_db :as my-db]
            [ring.adapter.jetty :as jetty]
            [clojure.java.io :as io]
            [compojure.core :as compojure :refer (GET POST ANY DELETE PUT defroutes)]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.middleware.params :refer [wrap-params]]
            (compojure [handler :as handler]
                       [route :as route])))


(defn get-all-users
  "GET all users"
  []
  {:body (my-db/get-all-users)
   :status 200
   :headers {"Content-Type" "application/json"}})

(defn get-user-by-id
  "GET user by id"
  [id]
  {:body (my-db/get-user-by-id id)
   :status 200
   :headers {"Content-Type" "application/json"}})

(defn get-user-by-username-and-password
  "GET user by username and password"
  [username password]
  {:body (my-db/get-user-by-username-and-password username password)
   :status 200
   :headers {"Content-Type" "application/json"}})

(defn  delete-user-by-id
  "DELETE user by id"
  [id]
  {:body (my-db/remove-user-by-id id)
   :status 200
   :headers {"Content-Type" "application/json"}})

(defn insert-new-user
  "POST insert new user"
  [req]
  {:body (my-db/insert-new-user (-> req :params :username) (-> req :params :password) (-> req :params :type) (-> req :params :email))
   :status 200
   :headers {"Content-Type" "application/json"}})

(defn update-user
  "PUT update user"
  [req]
  {:body (my-db/update-user-by-id (-> req :route-params :id) {:username (-> req :params :username)
                                   :password (-> req :params :password)
                                   :type (-> req :params :type)
                                   :email (-> req :params :email)})
   :status 200
   :headers {"Content-Type" "application/json"}})

(defroutes app-routes
  (GET "/" [] "Hello World by misur")
  (GET "/users/all" [] (get-all-users))
  (GET "/users/:id" [id] (get-user-by-id id))
  (GET "/users" [username password] (get-user-by-username-and-password username password))
  (DELETE "/users/:id" [id] (delete-user-by-id id))
  (POST "/users" req  (insert-new-user req))
  (PUT "/users/:id" req (update-user req))
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



;;----------------------------test--------------------------------------


(app-routes {:uri "/users/3" :request-method :get})
(app-routes {:uri "/users/3"  :request-method :put :params
             {:username "test" :password "test" :type 1 :email "new@gmail.com"}})


