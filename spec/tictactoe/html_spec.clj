(ns tictactoe.html_spec
  (:require [speclj.core :refer :all]
            [tictactoe.html :refer :all]
            [tictactoe.ring_test :refer :all]
            [net.cgrand.enlive-html :refer [html-resource select attr=]]))

(def routes {:user-move "/move"
             :computer-move "/computer-move"
             :menu "/menu"
             :new-game "/new-game"})

(def render-game-with-routes #(render-game routes %))

(describe "render-game"
          (it "renders links for the empty spaces when the user plays next"
              (should= ["/move?space=2"
                        "/move?space=3"
                        "/move?space=4"
                        "/move?space=5"
                        "/move?space=6"
                        "/move?space=7"
                        "/move?space=8"]
                       (-> {:board [:x     :o     :empty
                                    :empty :empty :empty
                                    :empty :empty :empty]
                            :user-plays-next true
                            :finished false
                            :winner :none}
                           render-game-with-routes
                           find-links)))

          (it "renders no links when the user does not play next"
              (should= []
                       (-> {:board [:x     :o     :empty
                                    :empty :empty :empty
                                    :empty :empty :empty]
                            :user-plays-next false
                            :finished false
                            :winner :none}
                           render-game-with-routes
                           find-links)))

          (it "sets an automatic refresh to the computer move when the user does not play next"
              (should= "/computer-move"
                       (-> {:board [:x     :o     :empty
                                    :empty :empty :empty
                                    :empty :empty :empty]
                            :user-plays-next false
                            :finished false
                            :winner :none}
                           render-game-with-routes
                           find-refresh-url)))

          (it "renders only a link to the menu when it is finished"
              (should= ["/menu"]
                       (-> {:board [:x     :x     :x
                                    :o     :o     :empty
                                    :empty :empty :empty]
                            :user-plays-next true
                            :finished true
                            :winner :x}
                           render-game-with-routes
                           find-links)))

          (it "does not set an automatic refresh when it is finished"
              (should= nil
                       (-> {:board [:x     :x     :x
                                    :o     :o     :empty
                                    :empty :empty :empty]
                            :user-plays-next false
                            :finished true
                            :winner :x}
                           render-game-with-routes
                           find-refresh-url)))

          (it "renders the outcome when it is finished"
              (should-contain "The winner is x!"
                              (-> {:board [:x     :x     :x
                                           :o     :o     :empty
                                           :empty :empty :empty]
                                   :finished true
                                   :winner :x}
                                  render-game-with-routes)))

          (it "renders a draw outcome"
              (should-contain "It is a draw!"
                              (-> {:board [:x     :x     :o
                                           :o     :o     :x
                                           :x     :x     :o]
                                   :finished true
                                   :winner :none}
                                  render-game-with-routes))))

(describe "render-menu"
          (it "the form's action sends to a new game"
              (should= ["/new-game"]
                       (->> routes
                            render-menu
                            (find-in-html [:form])
                            (map #(get-in % [:attrs :action]))))))

(run-specs)
