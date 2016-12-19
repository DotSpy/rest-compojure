(ns rest-compojure.controller.account-controller
  (:use
    [rest-compojure.service.mail-service]
    [rest-compojure.protocols.users]
    [rest-compojure.protocols.common]
    [carica.core])
  (:require
    [rest-compojure.layout :as layout]
    [rest-compojure.service.account-validator :as rv]
    [ring.util.response :refer [response redirect]]
    [rest-compojure.repo.users :refer :all]
    [buddy.hashers :as hashers]))

(def users-repository (->users-repository))
(def mail-service (->smtp-mail-service (config :smtp)))
(def mail-agent (agent mail-service))

(defn login-page
  ([] (layout/render "login.html"))
  ([error] (layout/render "login.html" (merge {:error-message error}))))

(defn signup-page
  ([] (layout/render "register.html"))
  ([error] (layout/render "register.html" (merge {:error-message error}))))

(defn do-register-user [{:keys [params]}]
  (let [errors (rv/validate-register params)]
    (cond
      errors (-> (signup-page (vals errors)))
      (first (get-by-email users-repository (:email params))) (-> (signup-page "User with such email already exist")
                                                                  )
      :else (do
              (insert-entity users-repository (merge {:pass (hashers/encrypt (params :pass))}
                                                     (select-keys params [:name :surname :email])))

              (send mail-agent (fn [this data] (.send-mail this data) this) {:from    "fckclojure@gmail.com"
                                                                             :to      "fckclojure@gmail.com"
                                                                             :subject "Thanks for registration!"
                                                                             :body    "Your data....."})
              (redirect "/login")
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
          (redirect "/posts")
          (login-page "There is no user with such credentials")))))
  )