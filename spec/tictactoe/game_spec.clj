(ns tictactoe.game_spec
  (:require [speclj.core :refer :all]
            [tictactoe.game :refer :all]
            [tictactoe.board :refer [empty-spaces]]))

(describe "do-turn"
          (it "places mark x at first space in a board with no marks"
              (should= [:x     :empty :empty
                        :empty :empty :empty
                        :empty :empty :empty]
                       (do-turn (fn [board] 0)
                                [:empty :empty :empty
                                 :empty :empty :empty
                                 :empty :empty :empty])))

          (it "places mark o at second space in a board with a x mark in the first space"
              (should= [:x     :o     :empty
                        :empty :empty :empty
                        :empty :empty :empty]
                       (do-turn (fn [board] 1)
                                [:x     :empty :empty
                                 :empty :empty :empty
                                 :empty :empty :empty])))

          (it "tells the player to play in a board with no marks"
              (should-not-throw (do-turn (fn [board]
                                           {:pre [(= board [:empty :empty :empty
                                                            :empty :empty :empty
                                                            :empty :empty :empty])]}
                                           0)
                                         [:empty :empty :empty
                                          :empty :empty :empty
                                          :empty :empty :empty])))

          (it "tells the player to play in a board with a x mark"
              (should-not-throw (do-turn (fn [board]
                                           {:pre [(= board [:x     :empty :empty
                                                            :empty :empty :empty
                                                            :empty :empty :empty])]}
                                           1)
                                         [:x     :empty :empty
                                          :empty :empty :empty
                                          :empty :empty :empty]))))

(describe "game"
          (it "goes though the history of it"
              (let [first-empty-space-player (fn [board]
                                               (first (empty-spaces board)))]
                (should= [{:board [:empty :empty :empty
                                   :empty :empty :empty
                                   :empty :empty :empty]
                           :next-mark :x
                           :finished false
                           :winner :none}

                          {:board [:x     :empty :empty
                                   :empty :empty :empty
                                   :empty :empty :empty]
                           :next-mark :o
                           :finished false
                           :winner :none}

                          {:board [:x     :o     :empty
                                   :empty :empty :empty
                                   :empty :empty :empty]
                           :next-mark :x
                           :finished false
                           :winner :none}

                          {:board [:x     :o     :x
                                   :empty :empty :empty
                                   :empty :empty :empty]
                           :next-mark :o
                           :finished false
                           :winner :none}

                          {:board [:x     :o     :x
                                   :o     :empty :empty
                                   :empty :empty :empty]
                           :next-mark :x
                           :finished false
                           :winner :none}

                          {:board [:x     :o     :x
                                   :o     :x     :empty
                                   :empty :empty :empty]
                           :next-mark :o
                           :finished false
                           :winner :none}

                          {:board [:x     :o     :x
                                   :o     :x     :o
                                   :empty :empty :empty]
                           :next-mark :x
                           :finished false
                           :winner :none}

                          {:board [:x     :o     :x
                                   :o     :x     :o
                                   :x     :empty :empty]
                           :next-mark :o
                           :finished true
                           :winner :x}]
                         (game first-empty-space-player)))))

(describe "players"
          (it "calls player x with the board and returns its return value when it is x turn"
              (let [player (players (fn [board]
                                      {:pre [(= board [:x     :o     :empty
                                                       :empty :empty :empty
                                                       :empty :empty :empty])]}
                                      :player-x-choice)
                                    (fn [board] :player-o-choice))]
                (should= :player-x-choice
                         (player [:x     :o     :empty
                                  :empty :empty :empty
                                  :empty :empty :empty]))))

          (it "calls player o with the board and returns its return value when it is o turn"
              (let [player (players (fn [board] :player-x-choice)
                                    (fn [board]
                                      {:pre [(= board [:x     :empty :empty
                                                       :empty :empty :empty
                                                       :empty :empty :empty])]}
                                      :player-o-choice))]
                (should= :player-o-choice
                         (player [:x     :empty :empty
                                  :empty :empty :empty
                                  :empty :empty :empty])))))

(run-specs)
