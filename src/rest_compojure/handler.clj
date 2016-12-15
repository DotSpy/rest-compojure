(ns rest-compojure.handler
  (:use
    ring.middleware.json)
  (:require [rest-compojure.db.core]
            [compojure.core :refer [routes wrap-routes]]
            [rest-compojure.routes.home :refer [home-routes]]
            [compojure.route :as route]
            [rest-compojure.layout :as layout]
            [rest-compojure.middleware.middleware :as middleware]
            [ring.util.response :refer [response]]
            [rest-compojure.env :refer [defaults]]
            [mount.core :as mount]))

(mount/defstate init-app
                :start ((or (:init defaults) identity))
                :stop ((or (:stop defaults) identity)))

(def app-routes
  (routes
    (-> #'home-routes
        ;(wrap-routes middleware/wrap-csrf)
        (wrap-routes middleware/wrap-formats))
    (route/not-found
      (layout/error-page {:status 404 :title "Not found" :message "ooops"}))))

;(def app
;  (-> app-routes
;      ;(wrap-authentication auth-backend)
;      ;(wrap-authorization auth-backend)
;      wrap-json-response
;      (wrap-json-body {:keywords? true})))


(defn app [] (middleware/wrap-base #'app-routes))

;; Strip namespace from namespaced-qualified kewywords, which is how wo represent user levels
;(add-encoder clojure.lang.Keyword
;             (fn [^clojure.lang.Keyword kw ^JsonGenerator gen]
;               (.writeString gen (name kw))))
;
;(defn get-users [_]
;  {:status 200
;   :body   {:count   (users/count-users)
;            :results (users/find-all)}})
;
;(defn create-user [user]
;  ;(println user)
;  (let [new-user (account-controller/do-register-user user)]
;    {:status  201
;     :headers {"Location" (str "/users/" (:id new-user))}}))
;
;(defn find-user [{{:keys [id]} :params}]
;  (response (users/find-by-id (read-string id))))
;
;;(defn lists-for-user [{{:keys [id]} :params}]
;;  (response
;;    (map #(dissoc % :user_id) (post/find-all-by :user_id (read-string id)))))
;;
;(defn delete-user [{{:keys [id]} :params}]
;  (users/delete-user {:id (read-string id)})
;  {:status  204
;   :headers {"Location" "/users"}})
;;
;;(defn get-lists [_]
;;  {:status 200
;;   :body {:count (lists/count-lists)
;;          :results (lists/find-all)}})
;;
;;(defn create-list [{listdata :body}]
;;  (let [new-list (lists/create listdata)]
;;    {:status 201
;;     :headers {"Location" (str "/users/" (:user_id new-list) "/lists")}}))
;;
;;(defn find-list [{{:keys [id]} :params}]
;;  (response (lists/find-by-id (read-string id))))
;;
;;(defn update-list [{{:keys [id]} :params
;;                    listdata :body}]
;;  (if (nil? id)
;;    {:status 404
;;     :headers {"Location" "/lists"}}
;;
;;    ((lists/update-list (assoc listdata :id id))
;;      {:status 200
;;       :headers {"Location" (str "/lists/" id)}})))
;;
;;(defn delete-list [{{:keys [id]} :params}]
;;  (lists/delete-list {:id (read-string id)})
;;  {:status 204
;;   :headers {"Location" "/lists"}})
;;
;;(defn get-products [_]
;;  {:status 200
;;   :body {:count (products/count-products)
;;          :results (products/find-all)}})
;;
;;(defn create-product [{product :body}]
;;  (let [new-prod (products/create product)]
;;    {:status 201
;;     :headers {"Location" (str "/products/" (:id new-prod))}}))
;;
;
;
;;(defn wrap-log-request [handler]
;;  (fn [req]
;;    (println req)
;;    (handler req)))
;;
;
;(def app-routes
;  (routes
;    (-> #'home-routes
;        (wrap-routes middleware/wrap-csrf)
;        (wrap-routes middleware/wrap-formats))
;    (route/not-found
;      (layout/error-page {:status 404 :title "Not found" :message "ooops"}))))
;
;(defn app [] (middleware/wrap-base #'app-routes))
;
;;(def app
;;  (-> app-routes
;;      ;(wrap-authentication auth-backend)
;;      ;(wrap-authorization auth-backend)
;;      wrap-json-response
;;      (wrap-json-body {:keywords? true})))
