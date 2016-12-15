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

                 [com.draines/postal "2.0.2"]
                 [markdown-clj "0.9.89"]
                 [mount "0.1.11"]
                 [selmer "1.0.9"]
                 [cprop "0.1.9"]
                 [ring-middleware-format "0.7.0"]
                 [ring-webjars "0.1.1"]
                 [luminus-nrepl "0.1.4"]
                 [luminus-migrations "0.2.7"]
                 [luminus-immutant "0.2.2"]
                 [bouncer/bouncer "1.0.0"]
                 [metosin/ring-http-response "0.8.0"]
                 [crypto-random "1.2.0"]
                 [buddy/buddy-hashers "1.1.0"]
                 [buddy/buddy-auth "1.3.0"]
                 [org.clojure/tools.cli "0.3.5"]
                 [org.clojure/tools.logging "0.3.1"]
                 [com.cemerick/friend "0.2.3"]]
  :ring {:handler       rest-compojure.handler/app
         :open-browser? false}

  :jvm-opts ["-server" "-Dconf=.lein-env"]
  :resource-paths ["resources" "target/cljsbuild"]
  :target-path "target/%s/"
  :main rest-compojure.core

  :plugins [[lein-ring "0.9.7"]
            [lein-cprop "1.0.1"]
            [migratus-lein "0.4.2"]
            [lein-cljsbuild "1.1.4"]
            [lein-immutant "2.1.0"]]
  :clean-targets ^{:protect false}
[:target-path [:cljsbuild :builds :app :compiler :output-dir] [:cljsbuild :builds :app :compiler :output-to]]
  :figwheel
  {:http-server-root "public"
   :nrepl-port       7002
   :css-dirs         ["resources/public/css"]
   :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}


  :profiles
  {:uberjar       {:omit-source    true
                   :prep-tasks     ["compile" ["cljsbuild" "once" "min"]]
                   :cljsbuild
                                   {:builds
                                    {:min
                                     {:source-paths ["src/cljc" "src/cljs" "env/dev/cljs"]
                                      :compiler
                                                    {:output-to     "target/cljsbuild/public/js/app.js"
                                                     :externs       ["react/externs/react.js"]
                                                     :optimizations :advanced
                                                     :pretty-print  false
                                                     :closure-warnings
                                                                    {:externs-validation :off :non-standard-jsdoc :off}}}}}


                   :aot            :all
                   :uberjar-name   "clojure.jar"
                   :source-paths   ["env/dev/clj"]
                   :resource-paths ["env/dev/resources"]}

   :dev           [:project/dev :profiles/dev]
   :test          [:project/dev :project/test :profiles/test]

   :project/dev   {:dependencies   [[prone "1.1.2"]
                                    [ring/ring-mock "0.3.0"]
                                    [ring/ring-devel "1.5.0"]
                                    [pjstadig/humane-test-output "0.8.1"]
                                    [doo "0.1.7"]
                                    [binaryage/devtools "0.8.2"]
                                    [figwheel-sidecar "0.5.8"]
                                    [com.cemerick/piggieback "0.2.2-SNAPSHOT"]]
                   :plugins        [[com.jakemccrary/lein-test-refresh "0.14.0"]
                                    [lein-doo "0.1.7"]
                                    [lein-figwheel "0.5.8"]
                                    [org.clojure/clojurescript "1.9.229"]]
                   :cljsbuild
                                   {:builds
                                    {:app
                                     {:source-paths ["src/cljs" "src/cljc" "env/dev/cljs"]
                                      :compiler
                                                    {:main          "clojure.app"
                                                     :asset-path    "/js/out"
                                                     :output-to     "target/cljsbuild/public/js/app.js"
                                                     :output-dir    "target/cljsbuild/public/js/out"
                                                     :source-map    true
                                                     :optimizations :none
                                                     :pretty-print  true}}}}



                   :doo            {:build "test"}
                   :source-paths   ["env/dev/clj" "test/clj"]
                   :resource-paths ["env/dev/resources"]
                   :repl-options   {:init-ns user}
                   :injections     [(require 'pjstadig.humane-test-output)
                                    (pjstadig.humane-test-output/activate!)]}
   :project/test  {:resource-paths ["env/dev/resources" "env/test/resources"]
                   :cljsbuild
                                   {:builds
                                    {:test
                                     {:source-paths ["src/cljc" "src/cljs" "test/cljs"]
                                      :compiler
                                                    {:output-to     "target/test.js"
                                                     :main          "rest_compojure.doo-runner"
                                                     :optimizations :whitespace
                                                     :pretty-print  true}}}}

                   }
   :profiles/dev  {}
   :profiles/test {}})

