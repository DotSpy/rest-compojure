(ns rest-compojure.repo.auth-tokens
  (:require
    [korma.core :refer :all]
    [rest-compojure.entities :refer :all]
    [rest-compojure.protocols.common :refer [common-protocol]]
    [rest-compojure.protocols.tokens :refer [tokens-protocol]]
    [rest-compojure.repo.common :refer [common-repository]]))

(deftype tokens-repository []
  tokens-protocol

  (get-by-user [this user_id] (select auth-tokens
                                      (where {:user_id user_id})))
  )

(extend tokens-repository
  common-protocol
  (common-repository auth-tokens))
