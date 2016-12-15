(ns rest-compojure.models.user
  (:use korma.core)
  (:require [rest-compojure.entities :as e]
            [buddy.hashers :as hashers]
            [clojure.set :refer [map-invert]]))

(defn find-all []
  (select e/user))

(defn find-by [field value]
  (some-> (select* e/user)
          (where {field value})
          (limit 1)
          select
          first
          (dissoc :password)))

(defn find-by-id [id]
  (find-by :id id))

(defn for-post [listdata]
  (find-by-id (listdata :owner_id)))

(defn find-by-email [email]
  (find-by :email email))

(defn create-user [user]
  ;(println user)
  (insert e/user
          (values user)))

(defn password-matches?
  "Check to see if the password given matches the digest of the user's saved password"
  [id password]
  (some-> (select* e/user)
          (fields :password)
          (where {:id id})
          select
          first
          :password
          (->> (hashers/check password))))

(defn update-user [user]
  (update e/user
          (set-fields (dissoc user :id))
          (where {:id (user :id)})))

(defn count-users []
  (select :user
          (aggregate (count :*) :cnt))
  )

(defn delete-user [user]
  (delete e/user
          (where {:id (user :id)})))