(ns tictactoe.ai-spec
  (:require [speclj.core :refer :all]
            [tictactoe.ai :refer :all]))

(describe "score"
          (it "scores positively a board won by player x"
              (should-be #(> % 0)
                         (score [:empty :o     :x
                                 :empty :o     :x
                                 :empty :empty :x])))

          (it "scores positively a board won by player o"
              (should-be #(> % 0)
                         (score [:x     :o :x
                                 :empty :o :x
                                 :empty :o :empty])))

          (it "scores neutrally a board ended in a draw"
              (should= 0
                       (score [:x :o :x
                               :x :o :x
                               :o :x :o])))

          (it "scores negativelly a board that will be won by the opponent in this turn"
              (should-be #(< % 0)
                         (score [:o :x :x
                                 :x :o :x
                                 :o :o :empty])))

          (it "scores negativelly a board that will be won by the opponent in this turn"
              (should-be #(< % 0)
                         (score [:o :x :x
                                 :x :x :empty
                                 :o :o :empty])))

          (it "scores neutrally a board that will end in a draw"
              (should= 0
                       (score [:o :x :x
                               :x :x :o
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
                                         :empty :empty :empty])))

          (it "chooses an immediate win over a fork"
              (should= 2
                       (computer-player [:x :x     :empty
                                         :o :empty :empty
                                         :o :empty :empty]))))

(run-specs)