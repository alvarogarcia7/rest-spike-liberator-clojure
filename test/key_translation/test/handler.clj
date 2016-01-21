(ns key-translation.test.handler
  (:use clojure.test
        ring.mock.request
        key-translation.handler))

(deftest test-app
  (testing "the request does not exist yet"
    (let [response (app (request :get "/1003/event"))]
      (is (= (:status response) 400))))

  (testing "add and retrieve a response"
    (let [response (app (request :post "/1003/28/event"))
           response2 (app (request :get "/1003/event"))]
      (is (= (:status response) 201))
      (is (= (:status response2) 200))
      (is (= (:body response2) "28")))))
