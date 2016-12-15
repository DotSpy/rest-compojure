(ns rest-compojure.service.mail-service
  (:require [postal.core :refer [send-message]]
            [rest-compojure.protocols.mail-service :refer [mail-service-protocol]]))

(deftype smtp-mail-service [conn]
  mail-service-protocol
  (send-mail [this data] (send-message conn data)))