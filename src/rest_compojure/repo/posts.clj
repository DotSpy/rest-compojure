(ns rest-compojure.repo.posts
  (:require [korma.core :refer :all]
            [rest-compojure.entities :refer :all]
            [rest-compojure.protocols.common :refer [common-protocol]]
            [rest-compojure.repo.common :refer [common-repository]]
            [rest-compojure.protocols.posts :refer [posts-protocol]]))

(deftype posts-repository []
  posts-protocol

  (get-by-user-id [this id] (select post
                                    (where {:owner_id id})))
  )

(extend posts-repository
  common-protocol
  (common-repository post))