(ns tictactoe.ring-test
  (:require [net.cgrand.enlive-html :refer [html-resource select attr=]]))

(defn request [url]
  (let [[uri query-string] (clojure.string/split url #"\?")]
    {:request-method :get :uri uri :query-string query-string}))

(defn find-in-html [selector response]
  (let [html (->> response :body java.io.StringReader. html-resource)]
    (select html selector)))

(defn get-redirect-url [response]
  (-> response
      (get-in [:headers "Location"])))

(defn- find-refresh [response]
  (->> response
       (find-in-html [(attr= :http-equiv "refresh")])
       first))

(defn get-refresh-url [response]
  (-> (find-refresh response)
      (get-in [:attrs :content])
      (clojure.string/split #";")
      second
      (clojure.string/split #"=")
      second))
