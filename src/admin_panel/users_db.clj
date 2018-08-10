(ns admin_panel.users_db
  (:require [clojure.java.jdbc :as sql]))

(def db {:classname "com.mysql.jdbc.Driver"
          :subprotocol "mysql"
          :subname "//127.0.0.1:8889/admin"
          :user "root"
          :password "root"})


(defn  get-all-users
  "Get all users - users table"
  []
  (sql/query db ["SELECT * FROM users"]))

(defn get-user-by-id
  "Get a user by id - users table"
  [id]
  (sql/query db ["SELECT * FROM users WHERE id = ?" id]))

(defn get-user-by-username-and-password
  "Get user by username and password - users table"
  [username password]
  (sql/query db ["SELECT * FROM users WHERE username = ? and password = ?" username password]))


(defn insert-new-user
  "Insert new user (username password type email) -  users table"
  [username password utype email]
  (sql/insert! db :users {:username username :password password  :type utype :email email}))

(defn update-user-by-id
  "Update user by id - users table"
  [id user]
  (sql/update! db :users {:username (get user :username) :password (get user :password) :type (get user :type) :email (get user :email)} ["id = ? " id] ))

(defn remove-user-by-id
  "Remove user by id - users table"
  [id]
  (sql/delete! db :users ["id = ?" id]))


(defn drop-users-table
  "Drop users table - admin function"
  []
  (sql/db-do-commands db
    (sql/drop-table-ddl :users)))


(defn create-users-table
  "Create users table"
  []
  (sql/db-do-commands db
    (sql/create-table-ddl
      :users
        [[:id :int "PRIMARY KEY" "AUTO_INCREMENT"]
         [:username "varchar(32)" "NOT NULL"]
         [:password "varchar(32)" "NOT NULL"]
         [:email "varchar(32)" ]
         [:type :int "NOT NULL"]
         [:about "varchar(32)" ]]
       {:conditional? (println "Table already  exists")})))




;;--------------------------- test code -------------------------------------

;; (get-user-by-username-and-password "user" "pass")



;; (create-users-table)


;; (sql/db-do-commands db
;; (drop-users-table))


;; (remove-user-by-id 4)

;; (update-user-by-id 1 {:username "milosu" :password "passwordu" :email "misur@gmail.com" :type 2})

;; (insert-new-user "user1" "pass1" 2 nil)


;;   (sql/query db ["SHOW TABLES"])

;;   (sql/query db ["DESC users"])


;;   (sql/query db ["SELECT * FROM USERS"])




