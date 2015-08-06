(ns tictactoe.ring_test
  (:require [net.cgrand.enlive-html :refer [html-resource select attr=]]))

(defn request [url]
  (let [[uri query-string] (clojure.string/split url #"\?")]
    {:request-method :get :uri uri :query-string query-string}))

(defn find-in-html [selector html]
  (select (->> html java.io.StringReader. html-resource) selector))

(defn redirect-url [response]
  (-> response
      (get-in [:headers "Location"])))

(defn- find-refresh [html]
  (->> html
       (find-in-html [(attr= :http-equiv "refresh")])
       first))

(defn find-refresh-url [html]
  (let [refresh-tag (find-refresh html)]
    (when refresh-tag
      (-> refresh-tag
          (get-in [:attrs :content])
          (clojure.string/split #";")
          second
          (clojure.string/split #"=")
          second))))

(defn find-spaces [html]
  (->> html
       (find-in-html [(attr= :data-space)])
       (map #(get-in % [:attrs :data-space]))
       (filter #(not (nil? %)))))

(defn find-links [html]
  (->> html
       (find-in-html [:a])
       (map #(get-in % [:attrs :href]))))

(defn in-response-body [body-receiver]
  (fn [response]
    (body-receiver (:body response))))
