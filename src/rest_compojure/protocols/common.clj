(ns rest-compojure.protocols.common)

(defprotocol common-protocol
  (get-entity [this id])
  (get-entities [this])
  (insert-entity [this data])
  (update-entity [this id data])
  (delete-entity [this id]))