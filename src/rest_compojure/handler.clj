(ns rest-compojure.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]])
  (:use carica.core
        korma.core
        korma.db
        rest-compojure.db.core
        ))

(defentity user)

(defroutes app-routes
           (GET "/" [] (select user))
           (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
