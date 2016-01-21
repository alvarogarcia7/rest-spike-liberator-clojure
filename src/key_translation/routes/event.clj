(ns key-translation.routes.event
  (:import [java.io File])
  (:require [compojure.core :refer :all]
            [ring.middleware.multipart-params :refer [wrap-multipart-params]]
            [cheshire.core :refer [generate-string]]
            [liberator.core :refer [defresource resource request-method-in]]
            [clojure.java.io :refer [copy make-parents]]))

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


(defn upload-file [tenant-id folder file]
  (let [file-name (file :filename)
         size (file :size)
         actual-file (file :tempfile)
         destination (format "C:/tmp/%s/%s/%s" tenant-id folder file-name)]
         (make-parents destination)
       (copy actual-file (File. destination))))

(defresource replace-events
  :allowed-methods [:post]
  :post! 
  (fn [context]
    (let [form-params (get-in context [:request :form-params])
           tenant-id (str (get-in context [:request :route-params :tenant]))
           payload (str (get-in context [:request :route-params :payload]))
           event-file (get-in context [:request :multipart-params "event"])
           id-file (get-in context [:request :multipart-params "id"])]
           ;;(clojure.pprint/pprint context)
           ;;(clojure.pprint/pprint (:file context))
           ;;(def ctx context)
           (upload-file tenant-id "event" event-file)
           (upload-file tenant-id "id" id-file)
              (swap! events merge @events {tenant-id payload})))
  :handle-created (fn [_] (generate-string @events))
  :available-media-types ["application/json"])

(defroutes event-routes
  (POST ["/:tenant/:payload/event" :tenant #".+" :payload #".+"] [tenant payload] replace-events)
  (GET ["/:tenant/event" :tenant #".+"] [tenant] get-events))
