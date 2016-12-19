(ns rest-compojure.controller.cashe-controller
  (:use
    [rest-compojure.service.mail-service]
    [rest-compojure.protocols.posts]
    [rest-compojure.repo.posts-cashe]
    [rest-compojure.protocols.common]
    [carica.core])
  (:require
    [ring.util.response :refer [response]]))

(def posts-cache-repository (->posts-cache-repository))

(defn get-posts [_]
  (-> (response {:posts (.get-entities posts-cache-repository)
                 })))

(defn get-post [id]
  (-> (response {:posts (.get-entity posts-cache-repository id)
                 })))

(defn delete-post [id]
  (.delete-entity posts-cache-repository id)
  (-> (response {:status  200
                 :id      (str id)
                 :message "Post deleted!"
                 })))