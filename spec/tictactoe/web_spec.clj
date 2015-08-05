(ns tictactoe.web-spec
  (:require [speclj.core :refer :all]
            [tictactoe.web :refer :all]
            [net.cgrand.enlive-html :refer [html-resource select attr=]]))

(defn get-request [uri]
  {:request-method :get :uri uri})

(defn find-in-html [selector response]
  (let [html (-> response :body java.io.StringReader. html-resource)]
    (select html selector)))

(def empty-cell [(attr= :data-cell "empty")])

(describe "webapp"
          (it "displays 9 empty cells in an initial game between two humans"
              (should= 9
                       (count (find-in-html empty-cell
                                            (webapp (get-request "/game?x=human&o=human")))))))

(run-specs)
