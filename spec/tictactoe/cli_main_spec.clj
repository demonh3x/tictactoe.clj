(ns tictactoe.cli-main-spec
  (:require [speclj.core :refer :all]
            [tictactoe.cli-main :refer :all]))

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
                                (-main "computer" "computer"))))

          (it "displays the board before asking to move"
              (should-contain (str "---\n"
                                   "---\n"
                                   "---\n"
                                   "It is your turn! Choose one space: (0 1 2 3 4 5 6 7 8)")
                              (with-out-str
                                (with-in-str "0\n3\n1\n4\n2\n"
                                             (-main "human" "human"))))))

(run-specs)
