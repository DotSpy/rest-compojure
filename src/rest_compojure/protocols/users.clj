(ns rest-compojure.protocols.users)

(defprotocol users-protocol
  (get-by-email [this email]))