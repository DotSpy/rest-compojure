(ns rest-compojure.handler
  (:use compojure.core
        ring.middleware.json)
  (:import (com.fasterxml.jackson.core JsonGenerator))
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.util.response :refer [response]]
            [cheshire.generate :refer [add-encoder]]
            [rest-compojure.models.user :as users]
            [rest-compojure.models.post :as post]
            [rest-compojure.auth :refer [auth-backend user-can user-isa user-has-id authenticated-user unauthorized-handler make-token!]]
            [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]
            [buddy.auth.accessrules :refer [restrict]]))

; Strip namespace from namespaced-qualified kewywords, which is how wo represent user levels
(add-encoder clojure.lang.Keyword
             (fn [^clojure.lang.Keyword kw ^JsonGenerator gen]
               (.writeString gen (name kw))))

(defn get-users [_]
  {:status 200
   :body   {:count   (users/count-users)
            :results (users/find-all)}})

(defn create-user [user]
  ;(println user)
  (let [new-user (users/create user)]
    {:status  201
     :headers {"Location" (str "/users/" (:id new-user))}}))

(defn find-user [{{:keys [id]} :params}]
  (response (users/find-by-id (read-string id))))

;(defn lists-for-user [{{:keys [id]} :params}]
;  (response
;    (map #(dissoc % :user_id) (post/find-all-by :user_id (read-string id)))))
;
(defn delete-user [{{:keys [id]} :params}]
  (users/delete-user {:id (read-string id)})
  {:status  204
   :headers {"Location" "/users"}})
;
;(defn get-lists [_]
;  {:status 200
;   :body {:count (lists/count-lists)
;          :results (lists/find-all)}})
;
;(defn create-list [{listdata :body}]
;  (let [new-list (lists/create listdata)]
;    {:status 201
;     :headers {"Location" (str "/users/" (:user_id new-list) "/lists")}}))
;
;(defn find-list [{{:keys [id]} :params}]
;  (response (lists/find-by-id (read-string id))))
;
;(defn update-list [{{:keys [id]} :params
;                    listdata :body}]
;  (if (nil? id)
;    {:status 404
;     :headers {"Location" "/lists"}}
;
;    ((lists/update-list (assoc listdata :id id))
;      {:status 200
;       :headers {"Location" (str "/lists/" id)}})))
;
;(defn delete-list [{{:keys [id]} :params}]
;  (lists/delete-list {:id (read-string id)})
;  {:status 204
;   :headers {"Location" "/lists"}})
;
;(defn get-products [_]
;  {:status 200
;   :body {:count (products/count-products)
;          :results (products/find-all)}})
;
;(defn create-product [{product :body}]
;  (let [new-prod (products/create product)]
;    {:status 201
;     :headers {"Location" (str "/products/" (:id new-prod))}}))
;
(defroutes app-routes
           ;; USERS
           (context "/users" []
             (GET "/" [] (-> get-users))
             (POST "/" request
               (create-user request))

             (context "/:id" [id]
               (restrict
                 (routes
                   (GET "/" [] find-user))
                 {:handler


                  (user-has-id (read-string id))
                  }))

             (DELETE "/:id" [id] (-> delete-user
                                     (restrict {:handler  {:and [authenticated-user (user-can "manage-users")]}
                                                :on-error unauthorized-handler}))))

           (POST "/sessions" {{:keys [user-id password]} :body}
             (if (users/password-matches? user-id password)
               {:status 201
                :body   {:auth-token (make-token! user-id)}}
               {:status 409
                :body   {:status  "error"
                         :message "invalid username or password"}}))

           (route/not-found (response {:message "Page not found"})))

;(defn wrap-log-request [handler]
;  (fn [req]
;    (println req)
;    (handler req)))
;
(def app
  (-> app-routes
      ;(wrap-authentication auth-backend)
      ;(wrap-authorization auth-backend)
      wrap-json-response
      (wrap-json-body {:keywords? true})))
