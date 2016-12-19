(ns rest-compojure.repo.posts-cashe
  (:use [rest-compojure.protocols.common]
        [rest-compojure.repo.common]
        [rest-compojure.repo.posts]))

(def posts-repository (->posts-repository))

(def cache (atom {}))

(deftype posts-cache-repository []
  common-protocol

  (get-entities [this] (let [records @cache]
                         (if (empty? records)
                           (let [from-db (reduce #(assoc %1 (keyword (str (:id %2))) %2) {} (get-entities posts-repository))]
                             (reset! cache from-db)
                             (vals from-db)
                             )
                           (vals records))))

  (get-entity [this id] (let [cached ((keyword (str id)) @cache)]
                          (if (nil? cached)
                            (let [from-db (get-entity posts-repository id)]
                              (swap! cache conj {(keyword (str id)) (first from-db)})
                              (first from-db))
                            cached)))

  (delete-entity [this id]
    (delete-entity posts-repository id)
    (swap! cache dissoc (keyword (str id))))
  )