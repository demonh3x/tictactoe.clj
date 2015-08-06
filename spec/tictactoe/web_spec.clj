(ns tictactoe.web-spec
  (:require [speclj.core :refer :all]
            [tictactoe.web :refer :all]
            [net.cgrand.enlive-html :refer [html-resource select attr=]]))

(defn request [url]
  (let [[uri query-string] (clojure.string/split url #"\?")]
    (webapp {:request-method :get :uri uri :query-string query-string})))

(defn find-in-html [selector response]
  (let [html (->> response :body java.io.StringReader. html-resource)]
    (select html selector)))

(defn get-links [response]
  (->> response
       (find-in-html [:a])
       (map #(get-in % [:attrs :href]))))

(defn get-spaces [response]
  (->> response
       (find-in-html [(attr= :data-space)])
       (map #(get-in % [:attrs :data-space]))))

(describe "webapp"
          (it "displays 9 empty spaces in an initial game between two humans"
              (should= 9
                       (->> (request "/")
                            (get-spaces)
                            (filter #(= % "empty"))
                            count)))

          (it "contains links to move to all empty spaces"
              (should= ["/move?space=0"
                        "/move?space=1"
                        "/move?space=2"
                        "/move?space=3"
                        "/move?space=4"
                        "/move?space=5"
                        "/move?space=6"
                        "/move?space=7"
                        "/move?space=8"]
                       (->> (request "/")
                            (get-links))))

          (it "displays x in the first space after making the move"
              (should= "x"
                       (do (request "/")
                           (->> (request "/move?space=0")
                                (get-spaces)
                                first))))

          (it "displays o in the second space after making the move"
              (should= "o"
                       (do (request "/")
                           (request "/move?space=0")
                           (->> (request "/move?space=1")
                                (get-spaces)
                                second)))))

(describe "player-moving-to"
          (it "the initial specified space"
              (should= nil
                       (let [space (atom nil)
                             player (player-moving-to space)]
                         (player :board))))

          (it "the space set before asking"
              (should= 1
                       (let [space (atom nil)
                             player (player-moving-to space)]
                         (swap! space (fn [prev] 1))
                         (player :board)))))

(run-specs)
