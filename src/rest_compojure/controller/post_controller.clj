(ns rest-compojure.controller.post-controller
  (:use
    [rest-compojure.service.mail-service]
    [rest-compojure.protocols.posts]
    [rest-compojure.repo.posts]
    [rest-compojure.protocols.common]
    [carica.core])
  (:require
    [rest-compojure.layout :as layout]
    [ring.util.response :refer [response redirect]]))

(def posts-repository (->posts-repository))

(defn posts-page
  ([] (layout/render "posts.html"))
  ([posts] (layout/render "posts.html" (merge {:posts posts}))))

(defn add-post [{:keys [params]}]
  (insert-entity posts-repository {:name (:name params) :text (:text params)})
  (-> (redirect "/posts")))

(defn delete-post [id]
  (delete-entity posts-repository id)
  (-> (redirect "/posts")))

(defn get-posts [_]
  (-> (posts-page (get-entities posts-repository))))

(defn get-post [id]
  (-> (response {:posts (get-entity posts-repository id)
                 })))