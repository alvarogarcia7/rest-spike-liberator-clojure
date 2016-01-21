(ns key-translation.routes.event
  (:require [compojure.core :refer :all]
            [cheshire.core :refer [generate-string]]
            [liberator.core :refer [defresource resource request-method-in]]))

(def events (atom {}))

(defresource get-events
  :allowed-methods [:get]
  :malformed? (fn [context]
                          (let [tenant-id (str (get-in context [:request :route-params :tenant]))]
                            (empty? (get @events tenant-id))))
  :handle-ok (fn [context] 
                      (let [tenant-id (str (get-in context [:request :route-params :tenant]))]
                        (get @events tenant-id)))
  :available-media-types ["application/json"])

(defresource replace-events
  :allowed-methods [:post]
  :post! 
  (fn [context]
    (let [form-params (get-in context [:request :form-params])
           tenant-id (str (get-in context [:request :route-params :tenant]))
           payload (str (get-in context [:request :route-params :payload]))]
              (swap! events merge @events {tenant-id payload})))
  :handle-created (fn [_] (generate-string @events))
  :available-media-types ["application/json"])

(defroutes event-routes
  (POST ["/:tenant/:payload/event" :tenant #".+" :payload #".+"] [tenant payload] replace-events)
  (GET ["/:tenant/event" :tenant #".+"] [tenant] get-events))
