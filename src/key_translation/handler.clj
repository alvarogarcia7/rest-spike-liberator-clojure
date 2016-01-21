(ns key-translation.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [hiccup.middleware :refer [wrap-base-url]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [key-translation.routes.event :refer [event-routes]]))

(defn init []
  (println "service is starting..."))

(defn destroy []
  (println "service is shutting down..."))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (routes event-routes app-routes)
      (handler/site)
      (wrap-base-url)))
