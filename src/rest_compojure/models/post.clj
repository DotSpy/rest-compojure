(ns rest-compojure.models.post
  (:use korma.core)
  (:require [rest-compojure.entities :as e]))

(defn find-all []
  (select e/post))

(defn find-by [field value]
  (first
    (select e/post
            (where {field value})
            (limit 1))))

(defn find-by-id [id]
  (find-by :id id))

(defn find-by-name [email]
  (find-by :name name))

(defn create [post]
  (insert e/post
          (values post)))

(defn update-post [post]
  (update e/post
          (set-fields (dissoc post :id))
          (where {:id (post :id)})))

(defn count-posts []
  (let [agg (select e/post
                    (aggregate (count :*) :cnt))]
    (get-in agg [0 :cnt] 0)))

(defn delete-post [post]
  (delete e/post
          (where {:id (post :id)})))