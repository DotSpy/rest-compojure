(ns rest-compojure.controller.account-controller
  (:use
    [rest-compojure.service.mail-service]
    [rest-compojure.protocols.users]
    [rest-compojure.protocols.common]
    [carica.core])
  (:require
    [rest-compojure.service.account-validator :as rv]
    [ring.util.response :refer [response]]
    [rest-compojure.repo.users :refer :all]
    [buddy.hashers :as hashers]))

(def users-repository (->users-repository))
(def mail-service (->smtp-mail-service (config :smtp)))
(def mail-agent (agent mail-service))

(defn do-register-user [{:keys [params]}]
  (let [errors (rv/validate-register params)]
    (cond
      errors (-> (response {:status 400
                            :title  "Error"
                            :error  errors}))
      (first (get-by-email users-repository (:email params))) (-> (response {:status 409
                                                                             :title  "Error"
                                                                             :email  "User with such email already exist"})
                                                                  )
      :else (do
              (insert-entity users-repository (merge {:pass (hashers/encrypt (params :pass))}
                                                     (select-keys params [:name :surname :email])))

              (send mail-agent (fn [this data] (.send-mail this data) this) {:from    "fckclojure@gmail.com"
                                                                             :to      "fckclojure@gmail.com"
                                                                             :subject "Thanks for registration!"
                                                                             :body    "Your data....."})
              (-> (response {:status  200
                             :title   "Registred"
                             :message "You are successfully registred!"
                             :token   "new token"}))        ;
              )))
  )

(defn do-auth-user [{:keys [params]}]
  (println params)
  (if-let [errors (rv/validate-auth params)]

    (-> (response {:status 400
                   :title  "Error"
                   :error  errors}))

    (do
      (let [user (first (get-by-email users-repository (:email params)))]

        (if (and user (hashers/check (:pass params) (:pass user)))
          (-> (response {:status 200
                         :token  "token"})                  ;getTOken
              )
          (-> (response {:status  401
                         :title   "Error"
                         :message "Wrong creds"})
              )))))
  )