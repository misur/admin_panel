(ns admin-panel.core-test
  (:require [clojure.test :refer :all]
            [admin-panel.core :refer :all]))


(deftest test-app
  (testing "get all users"
   (let [ response (app-routes {:uri "/users/all" :request-method :get})]
     (is (= (:status response) 200))
     (is (= (get-in response [:headers "Content-Type"]) "application/json"))))

  (testing "get user by id"
   (let [ response (app-routes {:uri "/users/3" :request-method :get})]
     (is (= (:status response) 200))
     (is (= (get-in response [:headers "Content-Type"]) "application/json"))))

  (testing "get user by username and password"
   (let [ response (app-routes {:uri "/users" :request-method :get :params {:username test :password test}})]
     (is (= (:status response) 200))
     (is (= (get-in response [:headers "Content-Type"]) "application/json"))))

  (testing "delete user"
   (let [ response (app-routes {:uri "/users/3" :request-method :delete})]
     (is (= (:status response) 200))
     (is (= (get-in response [:headers "Content-Type"]) "application/json"))))


  (testing "insert new user"
   (let [ response (app-routes {:uri "/users" :request-method :post :params {:username "test22" :password "test22" :type 2 :email "test22@aa.cc"}})]
     (is (= (:status response) 200))
     (is (= (get-in response [:headers "Content-Type"]) "application/json"))))


  (testing "update new user"
   (let [ response (app-routes {:uri "/users/1" :request-method :put :params {:username "test22" :password "test22" :type 2 :email "test22@ee.rr"}})]
     (is (= (:status response) 200))
     (is (= (get-in response [:headers "Content-Type"]) "application/json")))))










