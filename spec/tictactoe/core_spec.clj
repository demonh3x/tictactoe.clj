(ns tictactoe.core-spec
  (:require [speclj.core :refer :all]
            [tictactoe.core :refer :all]))

(describe "next-mark"
          (it "in a board with no marks, x is the next"
               (should= :x
                        (next-mark [:empty :empty :empty
                                    :empty :empty :empty
                                    :empty :empty :empty])))

          (it "in a board with one x mark, o is the next"
               (should= :o
                        (next-mark [:empty :x     :empty
                                    :empty :empty :empty
                                    :empty :empty :empty])))

          (it "in a board with one x mark and one o mark, x is the next"
               (should= :x
                        (next-mark [:x     :o     :empty
                                    :empty :empty :empty
                                    :empty :empty :empty]))))

(describe "do-turn"
          (it "in the initial board, places the mark at player x's chosen space"
              (should= [:x     :empty :empty
                        :empty :empty :empty
                        :empty :empty :empty]
                       (do-turn (fn [] 0)
                                [:empty :empty :empty
                                 :empty :empty :empty
                                 :empty :empty :empty])))

          (it "in the initial board, places the mark at player x's chosen space"
              (should= [:empty :x     :empty
                        :empty :empty :empty
                        :empty :empty :empty]
                       (do-turn (fn [] 1)
                                [:empty :empty :empty
                                 :empty :empty :empty
                                 :empty :empty :empty])))

          (it "in a board with one move from x, places the mark at player o's chosen space"
              (should= [:x     :o     :empty
                        :empty :empty :empty
                        :empty :empty :empty]
                       (do-turn (fn [] 1)
                                [:x     :empty :empty
                                 :empty :empty :empty
                                 :empty :empty :empty]))))

(run-specs)