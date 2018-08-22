(ns admin-panel.users_controller
    (:require [clojure.java.jdbc :as sql]
              [admin_panel.users_db :as my-db]
              [admin-panel.myvalidator :as valid]))


(defn http-response
  "Default HTTP response"
  [body status headers]
  {:body body
   :status status
   :headers headers})

(defn get-user-by-id
  "GET user by id"
  [&[id]]
  (if ( = nil id)
    (http-response "Id is not valid!" 404 {"Content-Type" "application/json"})
    (http-response (my-db/get-user-by-id id) 200 {"Content-Type" "application/json"})))


(defn insert-new-user
  "POST insert new user"
  [user]
  (if ( = nil (first(valid/validate-user user)))
    (http-response (my-db/insert-new-user (:username user) (:password user) (:type user) (:email user)) 200 {"Content-Type" "application/json"})
    (http-response (first(valid/validate-user user)) 404 {"Content-Type" "application/json"})))


(defn get-all-users
  "GET all users"
  []
  (http-response (my-db/get-all-users) 200 {"Content-Type" "application/json"}))



(defn get-user-by-username-and-password
  "GET user by username and password"
  [& [params]]
  (if (= nil params)
      (http-response "Username and pasword are not valid!" 404 {"Content-Type" "application/json"})
      (if (= nil (:username params))
            (http-response "Username is not valid!" 404 {"Content-Type" "application/json"})
            (if (= nil (:password params))
                  (http-response "Password is not valid!" 404 {"Content-Type" "application/json"})
                  (http-response (my-db/get-user-by-username-and-password (-> params :username) (-> params :password))
                  200 {"Content-Type" "application/json"})))))


(defn  delete-user-by-id
  "DELETE user by id"
  [&[id]]
  (if ( = nil id)
    (http-response "Id is not valid!" 404 {"Content-Type" "application/json"})
    (http-response (my-db/remove-user-by-id id) 200 {"Content-Type" "application/json"})))




(defn update-user
  "PUT update user"
  [ & [id user]]
  (if (= nil id)
    (http-response "Id is not valid!" 404 {"Content-Type" "application/json"})
    (if (= nil user)
      (http-response "User is not valid!" 404 {"Content-Type" "application/json"})
      (if ( = nil (first(valid/validate-user user)))
          (http-response (my-db/update-user-by-id id user)
            200
            {"Content-Type" "application/json"})
          (http-response (first(valid/validate-user user)) 404 {"Content-Type" "application/json"})))))


;TODO make method to check  if user exist by id
