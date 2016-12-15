(ns rest-compojure.routes.home
  (:use compojure.core
        ring.middleware.json)
  (:import (com.fasterxml.jackson.core JsonGenerator))
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [rest-compojure.controller.account-controller :as account-controller]
            [ring.util.response :refer [response]]
            [cheshire.generate :refer [add-encoder]]
            [rest-compojure.models.user :as users]
            [rest-compojure.models.post :as post]
            [rest-compojure.auth :refer [auth-backend user-can user-isa user-has-id authenticated-user unauthorized-handler make-token!]]
            [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]
            [buddy.auth.accessrules :refer [restrict]]))

(defn get-users [_]
  (println (-> get-users))
  {:status 200
   :body   {:count   (users/count-users)
            :results (users/find-all)}})

(defroutes home-routes
           (POST "/register" request
             (account-controller/do-register-user request))
           (GET "/test" [] ()
                           )
           ;; USERS
           (context "/users" []
             (GET "/" [] (-> get-users))


             ;(context "/:id" [id]
             ;  (restrict
             ;    (routes
             ;      (GET "/" [] find-user))
             ;    {:handler
             ;
             ;
             ;     (user-has-id (read-string id))
             ;     }))
             ;
             ;(DELETE "/:id" [id] (-> delete-user
             ;                        (restrict {:handler  {:and [authenticated-user (user-can "manage-users")]}
             ;                                   :on-error unauthorized-handler})))
             )

           (POST "/sessions" {{:keys [user-id password]} :body}
             (if (users/password-matches? user-id password)
               {:status 201
                :body   {:auth-token (make-token! user-id)}}
               {:status 409
                :body   {:status  "error"
                         :message "invalid username or password"}}))

           (route/not-found (response {:message "Page not found"}))
           )