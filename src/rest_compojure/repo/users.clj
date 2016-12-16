(ns rest-compojure.repo.users
  (:require
    [korma.core :refer :all]
    [rest-compojure.entities :refer :all]
    [rest-compojure.protocols.common :refer [common-protocol]]
    [rest-compojure.protocols.users :refer [users-protocol]]
    [rest-compojure.repo.common :refer [common-repository]]))

(deftype users-repository []
  users-protocol

  (get-by-email [this email] (select user
                                     (where {:email email})))
  )

(extend users-repository
  common-protocol
  (common-repository user))
