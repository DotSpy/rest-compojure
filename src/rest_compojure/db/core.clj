(ns rest-compojure.db.core
  (:use carica.core
        korma.core
        korma.db))

(defdb db {:classname   (config :db :classname)
           :subprotocol (config :db :subprotocol)
           :user        (config :db :user)
           :password    (config :db :password)
           :subname     (config :db :subname)
           :delimiters  (config :db :delimiters)})