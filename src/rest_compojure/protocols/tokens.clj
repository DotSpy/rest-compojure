(ns rest-compojure.protocols.tokens)

(defprotocol tokens-protocol
  (get-by-user [this user_id]))