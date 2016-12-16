(ns rest-compojure.dsl.exmpl)
;
;(fetch-all db (select
;                (from :products)
;                (join-left :users (= :products.user_id :users.id))
;                (order :products.id :desc)
;                (limit 2)))
