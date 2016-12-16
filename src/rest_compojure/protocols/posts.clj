(ns rest-compojure.protocols.posts)

(defprotocol posts-protocol
  (get-by-user-id [this user_id]))