(ns key-translation.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [ring.middleware.multipart-params :refer [wrap-multipart-params]]
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
      (wrap-base-url)
      wrap-params
      wrap-multipart-params))
