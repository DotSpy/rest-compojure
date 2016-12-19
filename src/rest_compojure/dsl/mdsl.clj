(ns rest-compojure.dsl.mdsl)

(defn query [& criteria]
  (apply merge criteria))

(defn matches-name [name]
  {:user.name name})

(defn with-surname [surname]
  {:user.surname {:$lte surname}})

(defn in-region [{:keys [name text start stop]}]
  {:post.name      name
   :post.text      text
   :location.start {:$gte start}
   :location.stop  {:$lte stop}})

(defn test [_] (str (query (matches-name "Bob")
                           (with-surname "Surname")
                           (in-region {:name  "post-name"
                                       :text  "post-text"
                                       :start 12345
                                       :stop  34567}))))

