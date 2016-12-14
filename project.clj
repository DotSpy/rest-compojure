(defproject rest-compojure "0.2.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://localhost:8080"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [http-kit "2.2.0"]
                 [compojure "1.5.1"]
                 [korma "0.4.2"]
                 [sonian/carica "1.1.0"]
                 [mysql/mysql-connector-java "6.0.5"]
                 [ring "1.5.0"]
                 [ring/ring-defaults "0.2.1"]
                 [ring/ring-json "0.4.0"]
                 [log4j "1.2.15" :exclusions [javax.mail/mail
                                              javax.jms/jms
                                              com.sun.jdmk/jmxtools
                                              com.sun.jmx/jmxri]]
                 [bouncer/bouncer "1.0.0"]
                 [crypto-random "1.2.0"]
                 [buddy/buddy-hashers "1.1.0"]
                 [buddy/buddy-auth "1.3.0"]
                 [com.cemerick/friend "0.2.3"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler       rest-compojure.handler/app
         :open-browser? false}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}})
