(ns rest-compojure.entities
  (:use korma.core
        rest-compojure.db))

(declare user lists)
(declare post lists)

(defentity user
           (pk :id)
           (table :user)
           (has-many post)
           (entity-fields :name :surname :pass :login))

(defentity post
           (pk :id)
           (table :post)
           (belongs-to user {:fk :owner_id})
           (entity-fields :name :view_count))

(defentity auth-tokens
           (pk :id)
           (table :auth_tokens)
           (belongs-to user {:fk :user_id}))