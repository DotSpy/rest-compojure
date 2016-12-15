(ns rest-compojure.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [rest-compojure.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
               (fn []
                 (parser/cache-off!)
                 (log/info "\n-=[clojure started successfully using the development profile]=-"))
   :stop
               (fn []
                 (log/info "\n-=[clojure has shut down successfully]=-"))
   :middleware wrap-dev})
