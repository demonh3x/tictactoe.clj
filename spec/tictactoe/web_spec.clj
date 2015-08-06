(ns tictactoe.web-spec
  (:require [speclj.core :refer :all]
            [tictactoe.web :refer :all]
            [tictactoe.ring-test :refer :all]
            [net.cgrand.enlive-html :refer [attr=]]))

(defn run-request [url]
  (-> url
      request
      webapp))

(defn follow-redirect [response]
  (run-request (redirect-url response)))

(defn follow-refresh [response]
  (run-request ((in-response-body find-refresh-url) response)))

(def responded-spaces (in-response-body find-spaces))

(def responded-links (in-response-body find-links))

(describe "webapp"
          (describe "human vs human"
                    (it "displays 9 empty spaces in a new game"
                        (should= 9
                                 (->> (run-request "/new-game?x=human&o=human")
                                      (follow-redirect)
                                      (responded-spaces)
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
                                 (->> (run-request "/new-game?x=human&o=human")
                                      (follow-redirect)
                                      (responded-links))))

                    (it "displays x in the first space after making the move"
                        (should= "x"
                                 (do (run-request "/new-game?x=human&o=human")
                                     (->> (run-request "/move?space=0")
                                          (follow-redirect)
                                          (responded-spaces)
                                          first))))

                    (it "displays o in the second space after making the move"
                        (should= "o"
                                 (do (run-request "/new-game?x=human&o=human")
                                     (run-request "/move?space=0")
                                     (->> (run-request "/move?space=1")
                                          (follow-redirect)
                                          (responded-spaces)
                                          second)))))

          (describe "computer vs human"
                    (it "displays x's move after refreshing"
                        (should= 1
                                 (->> (run-request "/new-game?x=computer&o=human")
                                      (follow-redirect)
                                      (follow-refresh)
                                      (follow-redirect)
                                      (responded-spaces)
                                      (filter #(= % "x"))
                                      count)))))

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
