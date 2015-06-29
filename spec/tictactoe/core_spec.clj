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
(run-specs)