(ns tictactoe.main-spec
  (:require [speclj.core :refer :all]
            [tictactoe.main :refer :all]))

(describe "main"
          (it "runs a game between two humans"
              (should-contain (str "xxx\n"
                                   "oo-\n"
                                   "---\n"
                                   "x has won!\n")
                              (with-out-str
                                (with-in-str "0\n3\n1\n4\n2\n"
                                             (-main "human" "human")))))

          (it "runs a game between two computers"
              (should-contain "it is a draw!"
                              (with-out-str
                                (-main "computer" "computer")))))

(run-specs)
