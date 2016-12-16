(ns rest-compojure.controller.post-controller
  (:use
    [rest-compojure.service.mail-service]
    [rest-compojure.protocols.posts]
    [rest-compojure.repo.posts]
    [rest-compojure.protocols.common]
    [carica.core])
  (:require
    [ring.util.response :refer [response]]))

(def posts-repository (->posts-repository))

(defn add-post [{:keys [params]}]
  (insert-entity posts-repository {:name (:name params) :text (:text params)})
  (-> (response {:status  200
                 :title   "Post"
                 :message "Post added!"
                 })))

(defn delete-post [id]
  (delete-entity posts-repository id)
  (-> (response {:status  200
                 :id      (str id)
                 :message "Post deleted!"
                 })))

(defn get-posts [_]
  (-> (response {:posts (get-entities posts-repository)
                 })))

(defn get-post [id]
  (-> (response {:posts (get-entity posts-repository id)
                 })))