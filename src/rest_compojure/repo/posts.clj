(ns rest-compojure.repo.posts
  (:require [korma.core :refer :all]
            [rest-compojure.entities :refer :all]
            [rest-compojure.protocols.common :refer [common-protocol]]
            [rest-compojure.repo.common :refer [common-repository]]
            [rest-compojure.protocols.posts :refer [posts-protocol]]))

(deftype posts-repository []
  posts-protocol

  ;(entities [this] (select post
  ;                         (order :id :DESC)))
  ;
  ;(get-entity [this id] (select post
  ;                              (where {:id id})))
  ;
  ;(delete-entity [this id] (delete post
  ;                                 (where {:id id})))
  ;
  ;(insert-entity [this data] (insert post
  ;                                   (values data)))

  posts-protocol
  (get-by-user-id [this id] (select post
                                    (where {:owner_id id})))
  )

(extend posts-repository
  common-protocol
  (common-repository post))