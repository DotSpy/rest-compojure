(ns rest-compojure.controller.account-controller
  (:require
    [rest-compojure.service.account-validator :as rv]))

(defn do-register-user [{:keys [params]}]
  (let [errors (rv/validate-register params)]
    ))