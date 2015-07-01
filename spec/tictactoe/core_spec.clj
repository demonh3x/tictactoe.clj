(ns tictactoe.core-spec
  (:require [speclj.core :refer :all]
            [tictactoe.core :refer :all]))

(describe "next-mark"
          (it "is x when the board has no marks"
              (should= :x
                       (next-mark [:empty :empty :empty
                                   :empty :empty :empty
                                   :empty :empty :empty])))

          (it "is o when the board has one x mark"
              (should= :o
                       (next-mark [:empty :x     :empty
                                   :empty :empty :empty
                                   :empty :empty :empty])))

          (it "is x when the board has one x mark and one o mark"
              (should= :x
                       (next-mark [:x     :o     :empty
                                   :empty :empty :empty
                                   :empty :empty :empty]))))

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

(describe "marks-at-lines"
          (it "are the marks at each line"
              (should= [[:o     :x     :empty]
                        [:empty :o     :empty]
                        [:x     :x     :empty]
                        [:o     :empty :x]
                        [:x     :o     :x]
                        [:empty :empty :empty]
                        [:o     :o     :empty]
                        [:empty :o     :x]]
                       (marks-at-lines [:o     :x :empty
                                        :empty :o :empty
                                        :x     :x :empty]))))

(describe "same-mark?"
          (it "is true when there are three x marks"
              (should= true
                       (same-mark? [:x :x :x])))

          (it "is true when there are three o marks"
              (should= true
                       (same-mark? [:o :o :o])))

          (it "is false when there are two x marks and one o mark"
              (should= false
                       (same-mark? [:x :x :o])))

          (it "is false when there are two o marks and one empty space"
              (should= false
                       (same-mark? [:empty :o :o])))

          (it "is false when all are empty"
              (should= false
                       (same-mark? [:empty :empty :empty]))))

(describe "winner"
          (it "is none in a board with no marks"
              (should= :none
                       (winner [:empty :empty :empty
                                :empty :empty :empty
                                :empty :empty :empty])))

          (it "is x in a board with the first horizontal line occupied by x"
              (should= :x
                       (winner [:x     :x     :x
                                :empty :empty :empty
                                :empty :empty :empty])))

          (it "is o in a board with the third vertical line occupied by o"
              (should= :o
                       (winner [:empty :empty :o
                                :empty :empty :o
                                :empty :empty :o]))))

(describe "full?"
          (it "is false when there is one empty space"
              (should= false
                       (full? [:o :x :o
                               :x :x :o
                               :x :o :empty])))

          (it "is false when there are two empty spaces"
              (should= false
                       (full? [:o     :x :o
                               :x     :x :empty
                               :empty :o :x])))

          (it "is true when there is no empty space"
              (should= true
                       (full? [:o :x :o
                               :x :x :o
                               :x :o :x]))))

(describe "finished?"
          (it "is true when the board is full"
              (should= true
                       (finished? [:o :x :o
                                   :x :x :o
                                   :x :o :x])))

          (it "is true when there is a winner"
              (should= true
                       (finished? [:x     :x     :x
                                   :o     :o     :empty
                                   :empty :empty :empty])))

          (it "is false when is not full and there is no winner"
              (should= false
                       (finished? [:o :x :o
                                   :x :x :o
                                   :x :o :empty]))))

(describe "empty-spaces"
          (it "all in an empty board"
              (should= '(0 1 2 3 4 5 6 7 8)
                       (empty-spaces [:empty :empty :empty
                                      :empty :empty :empty
                                      :empty :empty :empty])))

          (it "two in a board"
              (should= '(7 8)
                       (empty-spaces [:x     :o     :x
                                      :x     :o     :x
                                      :o     :empty :empty]))))

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

(describe "game"
          (it "goes though all the boards until the game is finished"
              (let [first-empty-space-player (fn [board]
                                               (first (empty-spaces board)))
                    board-history (game first-empty-space-player
                                        [:empty :empty :empty
                                         :empty :empty :empty
                                         :empty :empty :empty])]
                (should= [[:empty :empty :empty
                           :empty :empty :empty
                           :empty :empty :empty]

                          [:x     :empty :empty
                           :empty :empty :empty
                           :empty :empty :empty]

                          [:x     :o     :empty
                           :empty :empty :empty
                           :empty :empty :empty]

                          [:x     :o     :x
                           :empty :empty :empty
                           :empty :empty :empty]

                          [:x     :o     :x
                           :o     :empty :empty
                           :empty :empty :empty]

                          [:x     :o     :x
                           :o     :x     :empty
                           :empty :empty :empty]

                          [:x     :o     :x
                           :o     :x     :o
                           :empty :empty :empty]

                          [:x     :o     :x
                           :o     :x     :o
                           :x     :empty :empty]
                          ]
                         board-history))))

(describe "score"
          (it "scores positively a board won by player x"
              (should= 1
                       (score [:empty :o     :x
                               :empty :o     :x
                               :empty :empty :x])))

          (it "scores positively a board won by player o"
              (should= 1
                       (score [:x     :o :x
                               :empty :o :x
                               :empty :o :empty])))

          (it "scores neutrally a board ended in a draw"
              (should= 0
                       (score [:x :o :x
                               :x :o :x
                               :o :x :o])))

          (it "scores negativelly a board that will be won by the opponent in this turn"
              (should= -1
                       (score [:o :x :x
                               :x :o :x
                               :o :o :empty])))

          (it "scores neutrally a board that will end in a draw"
              (should= 0
                       (score [:o :x :x
                               :x :x :o
                               :o :o :empty])))

          (it "scores negativelly a board that will be won by the opponent in this turn"
              (should= -1
                       (score [:o :x :x
                               :x :x :empty
                               :o :o :empty]))))

(describe "computer-player"
          (it "blocks when can lose"
              (should= 8
                       (computer-player [:x     :o     :x
                                         :o     :o     :x
                                         :empty :x     :empty])))

          (it "chooses to win when has te opportunity"
              (should= 6
                       (computer-player [:x     :o     :x
                                         :o     :x     :empty
                                         :empty :empty :o])))

          (it "creates a fork if possible"
              (should= 4
                       (computer-player [:o     :empty :empty
                                         :x     :empty :empty
                                         :x     :o     :empty])))

          (it "foresees enough to avoid losing"
              (should= 4
                       (computer-player [:x     :empty :empty
                                         :empty :empty :empty
                                         :empty :empty :empty]))))

(run-specs)