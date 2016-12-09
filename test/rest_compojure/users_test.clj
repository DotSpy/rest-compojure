(ns rest-compojure.users-test
  (:use clojure.test
        restful-clojure.test-core)
  (:require [rest-compojure.models.user :as users]
            [rest-compojure.entities :as e]
            [korma.core :as sql]
            [environ.core :refer [env]]))

(deftest authorize-users
  (let [user (users/create {:name "Sly" :email "sly@falilystone.com" :password "s3cr3t"})
        user-id (:id user)]
    (testing "Accepts the correct password"
      (is (users/password-matches? user-id "s3cr3t")))

    (testing "Rejects incorrect passwords"
      (is (not (users/password-matches? user-id "not_my_password"))))))