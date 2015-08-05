(ns tictactoe.web-main
  (:require [tictactoe.web :refer [webapp]]
            [ring.adapter.jetty :as jetty]))

(defn -main [& args]
  (jetty/run-jetty webapp {:port 8080}))