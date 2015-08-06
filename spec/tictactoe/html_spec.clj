(ns tictactoe.html-spec
  (:require [speclj.core :refer :all]
            [tictactoe.html :refer :all]
            [net.cgrand.enlive-html :refer [html-resource select attr=]]))

(describe "render-game"
          (it "renders links for the empty spaces"
              (should-contain (str "<div data-space='x'>x</div>"
                                   "<div data-space='o'>o</div>"
                                   "<div data-space='empty'><a href='/move?space=2'>3</a></div>"
                                   "<div data-space='empty'><a href='/move?space=3'>4</a></div>"
                                   "<div data-space='empty'><a href='/move?space=4'>5</a></div>"
                                   "<div data-space='empty'><a href='/move?space=5'>6</a></div>"
                                   "<div data-space='empty'><a href='/move?space=6'>7</a></div>"
                                   "<div data-space='empty'><a href='/move?space=7'>8</a></div>"
                                   "<div data-space='empty'><a href='/move?space=8'>9</a></div>")
                              (render-game {:board [:x     :o     :empty
                                                    :empty :empty :empty
                                                    :empty :empty :empty]
                                     :finished false})))

          (it "renders no links when it is finished"
              (should-contain (str "<div data-space='x'>x</div>"
                                   "<div data-space='x'>x</div>"
                                   "<div data-space='x'>x</div>"
                                   "<div data-space='o'>o</div>"
                                   "<div data-space='o'>o</div>"
                                   "<div data-space='empty'>6</div>"
                                   "<div data-space='empty'>7</div>"
                                   "<div data-space='empty'>8</div>"
                                   "<div data-space='empty'>9</div>")
                              (render-game {:board [:x     :x     :x
                                                    :o     :o     :empty
                                                    :empty :empty :empty]
                                            :finished true
                                            :winner :x})))

          (it "renders the outcome when it is finished"
              (should-contain "<div data-outcome='x'>The winner is x!</div>"
                              (render-game {:board [:x     :x     :x
                                                    :o     :o     :empty
                                                    :empty :empty :empty]
                                            :finished true
                                            :winner :x})))

          (it "renders a draw outcome"
              (should-contain "<div data-outcome='none'>It is a draw!</div>"
                              (render-game {:board [:x     :x     :o
                                                    :o     :o     :x
                                                    :x     :x     :o]
                                            :finished true
                                            :winner :none})))

          (it "renders a link to restart the game"
              (should-contain "<a href='/'>Play again</a>"
                              (render-game {:board [:x     :x     :o
                                                    :o     :o     :x
                                                    :x     :x     :o]
                                            :finished true
                                            :winner :none}))))

(run-specs)
