(ns rest-compojure.entities
  (:use korma.core
        rest-compojure.db.core))

(declare user lists)
(declare post lists)
(declare auth-tokens lists)

(defentity user
           (pk :id)
           (table :user)
           (has-many post)
           (entity-fields :name :surname :pass :email))

(defentity post
           (pk :id)
           (table :post)
           (belongs-to user {:fk :owner_id})
           (entity-fields :name :text))

(defentity auth-tokens
           (pk :id)
           (table :auth_tokens)
           (belongs-to user {:fk :user_id}))