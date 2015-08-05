(ns tictactoe.web
  (:require [ring.middleware.params :refer [wrap-params]]))

(defn game [request]
  (let [moves (-> request :query-params (get "moves"))
        empty-cells (- 9 (count moves))]
    {:body (apply str (repeat empty-cells "<div data-cell='empty'></div>"))}))

(def webapp (wrap-params game))