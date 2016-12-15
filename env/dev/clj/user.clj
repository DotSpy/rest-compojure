(ns user
  (:require [mount.core :as mount]
            rest-compojure.core))

(defn start []
  (mount/start-without #'rest-compojure.core/repl-server))

(defn stop []
  (mount/stop-except #'rest-compojure.core/repl-server))

(defn restart []
  (stop)
  (start))


