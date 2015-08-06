(ns tictactoe.web_main
  (:gen-class :main)
  (:require [tictactoe.web :refer [webapp]]
            [ring.adapter.jetty :as jetty]))

(defn -main [& args]
  (jetty/run-jetty webapp {:port (Integer. (first args))}))