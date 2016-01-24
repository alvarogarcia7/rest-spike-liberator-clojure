(defproject liberator-service "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [compojure "1.1.6"]
                 [hiccup "1.0.5"]
                 [liberator "0.14.0"]
                 [cheshire "5.2.0"]
                 [org.clojure/data.csv "0.1.3"]
                 [ring-server "0.3.1"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler key-translation.handler/app
         :init key-translation.handler/init
         :destroy key-translation.handler/destroy
         :uberwar-name "kt-ws-rest.war"}
  :profiles
  {:uberjar {:aot :all}
   :production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}}
   :dev
   {:dependencies [[ring-mock "0.1.5"] [ring/ring-devel "1.3.1"]]}})
