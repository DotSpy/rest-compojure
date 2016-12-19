(ns rest-compojure.routes.home
  (:use compojure.core
        ring.middleware.json)
  (:require [compojure.route :as route]
            [rest-compojure.controller.account-controller :as account-controller]
            [rest-compojure.controller.post-controller :as post-controller]
            [rest-compojure.controller.cashe-controller :as cashe-controller]
            [ring.util.response :refer [response]]
            [cheshire.generate :refer [add-encoder]]
            [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]
            [buddy.auth.accessrules :refer [restrict]]
            [rest-compojure.dsl.mdsl :as test]))

(defroutes home-routes

           (GET "/login" [] (account-controller/login-page))
           (GET "/register" [] (account-controller/signup-page))


           (GET "/posts" [] (-> post-controller/get-posts))
           (GET "/posts/:id" [id] (post-controller/get-post id))
           (POST "/posts" request (post-controller/add-post request))
           (DELETE "/posts/:id" [id] (post-controller/delete-post id))
           (POST "/login" request (account-controller/do-auth-user request))
           (POST "/register" request (account-controller/do-register-user request))
           (context "/cashe" []
             (GET "/posts" [] (-> cashe-controller/get-posts))
             (DELETE "/posts/:id" [id] (cashe-controller/delete-post id))
             (GET "/posts/:id" [id] (cashe-controller/get-post id))
             )
           (GET "/test" [] (-> test/test))
           ;; USERS
           (route/not-found (response {:message "Page not found"}))
           )