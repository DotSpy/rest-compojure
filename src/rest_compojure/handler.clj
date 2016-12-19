(ns rest-compojure.handler
  (:use
    ring.middleware.json)
  (:require [rest-compojure.db.core]
            [compojure.core :refer [routes wrap-routes]]
            [rest-compojure.routes.home :refer [home-routes]]
            [compojure.route :as route]
            [rest-compojure.layout :as layout]
            [rest-compojure.middleware.middleware :as middleware]
            [ring.util.response :refer [response]]
            [rest-compojure.env :refer [defaults]]
            [mount.core :as mount]))

(mount/defstate init-app
                :start ((or (:init defaults) identity))
                :stop ((or (:stop defaults) identity)))

(def app-routes
  (routes
    (-> #'home-routes
        ;(wrap-routes middleware/wrap-csrf)
        (wrap-routes middleware/wrap-formats))
    (route/not-found
      (layout/error-page {:status 404 :title "Not found" :message "ooops"}))))

(defn app [] (middleware/wrap-base #'app-routes))

