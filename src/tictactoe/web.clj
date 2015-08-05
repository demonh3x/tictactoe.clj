(ns tictactoe.web)

(defn webapp [request]
  {:body (apply str (repeat 9 "<div data-cell='empty'></div>"))})