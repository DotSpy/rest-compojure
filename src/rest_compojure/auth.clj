(ns rest-compojure.auth
  (:use korma.core)
  (:require [rest-compojure.entities :as e]
            [rest-compojure.models.user :as user]
            [buddy.auth.backends.token :refer [token-backend]]
            [buddy.auth.accessrules :refer [success error]]
            [buddy.auth :refer [authenticated?]]
            [crypto.random :refer [base64]]))

(defn gen-session-id [] (base64 32))

(defn make-token!
  "Creates an auth token in the database for the given user and puts it in the database"
  [user-id]
  (let [token (gen-session-id)]
    (insert e/auth-tokens
            (values {:id token
                     :user_id user-id}))
    token))

(defn authenticate-token
  "Validates a token, returning the id of the associated user when valid and nil otherwise"
  [req token]
  (let [sql (str "SELECT user_id "
                 "FROM auth_tokens "
                 "WHERE id = ? "
                 "AND created_at > current_timestamp - interval '6 hours'")]
    (some-> (exec-raw [sql [token]] :results)
            first
            :user_id
            user/find-by-id)))

(defn unauthorized-handler [req msg]
  {:status 401
   :body {:status :error
          :message (or msg "User not authorized")}})