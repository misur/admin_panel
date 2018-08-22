(ns admin-panel.myvalidator
  (:require [bouncer.core :as b]
             [bouncer.validators :as v]))


(defn validate-email?
  "Check email in pattern"
  [email]
  (let [pattern #"[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"]
    (and (string? email) (re-matches pattern email))))


(defn check-email
  "Check email"
  [email]
  (string?  (validate-email? email)))



(defn  validate-user
  "Valid user object"
  [user]
  (b/validate user
    :username [v/required v/string]
    :password [v/required v/string]
    :type [v/required v/number v/positive]
    :email [v/email v/required]))




