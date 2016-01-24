(ns key-translation.test.handler
  (:import [java.io File])
  (:require [clojure.java.io :as io])
  (:use clojure.test
        ring.mock.request
        key-translation.handler))

(deftest test-app
  (testing "the request does not exist yet"
    (let [response (app (request :get "/1003/010100120001"))]
      (is (= (:status response) 400))))

  (testing "add and retrieve a response"
    (let [

            request2 (assoc (request :post "/1003/23")
                                      :multipart-params    {"id"
                                                                         {:filename "vc.csv",
                                                                          :content-type "application/octet-stream",
                                                                          :tempfile (io/file  "dev-resources/vc.csv"),
                                                                          :size 1462}})


              response (app request2)
              response2 (app (request :get "/1003/010100120001" ))]
      (is (= (:status response) 201))
      (is (= (:status response2) 200))
      (is (= (:body response2) "2649abbb5522855870d200cbbd5488a6")))))