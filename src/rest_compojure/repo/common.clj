(ns rest-compojure.repo.common
  (:use [korma.core]))

(defn common-repository [entity]
  {
   :get-entity    (fn [this id] (select entity
                                        (where {:id id})))
   :get-entities  (fn [this] (select entity
                                     (order :id :DESC)))
   :insert-entity (fn [this data] (insert entity
                                          (values data)))
   :update-entity (fn [this id data] (update entity
                                             (set-fields data)
                                             (where {:id id})))
   :delete-entity (fn [this id] (delete entity
                                        (where {:id id})))
   })