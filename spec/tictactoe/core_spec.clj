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
          (it "places the x mark at first space in a board with no marks"
              (should= [:x     :empty :empty
                        :empty :empty :empty
                        :empty :empty :empty]
                       (do-turn [:empty :empty :empty
                                 :empty :empty :empty
                                 :empty :empty :empty]
                                (fn [] 0))))

          (it "places the o mark at second space in a board with a x mark in the first space"
              (should= [:x     :o     :empty
                        :empty :empty :empty
                        :empty :empty :empty]
                       (do-turn [:x     :empty :empty
                                 :empty :empty :empty
                                 :empty :empty :empty]
                                (fn [] 1)))))

(run-specs)