(ns rest-compojure.service.account-validator
  (:require [bouncer.core :as b]
            [bouncer.validators :as v]))

(defn validate-register [params]
  (first
    (b/validate
      params
      :name v/required
      :surname v/required
      :email [v/required v/email]
      :pass [v/required [v/min-count 6]])))