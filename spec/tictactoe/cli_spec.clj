(ns tictactoe.cli_spec
  (:require [speclj.core :refer :all]
            [tictactoe.cli :refer :all]))

(describe "render returns the text representation of a board"
          (it "with no marks"
              (should= (str "---\n"
                            "---\n"
                            "---\n")
                       (with-out-str
                         (print-board [:empty :empty :empty
                                       :empty :empty :empty
                                       :empty :empty :empty]))))

          (it "with a x mark"
              (should= (str "x--\n"
                            "---\n"
                            "---\n")
                       (with-out-str
                         (print-board [:x     :empty :empty
                                       :empty :empty :empty
                                       :empty :empty :empty]))))

          (it "with a x and a o marks"
              (should= (str "xo-\n"
                            "---\n"
                            "---\n")
                       (with-out-str
                         (print-board [:x     :o     :empty
                                       :empty :empty :empty
                                       :empty :empty :empty])))))

(describe "print-outcome"
          (it "prints x as the winner"
              (should= "x has won!\n"
                       (with-out-str
                         (print-outcome :x))))

          (it "prints o as the winner"
              (should= "o has won!\n"
                       (with-out-str
                         (print-outcome :o))))

          (it "prints the draw message"
              (should= "it is a draw!\n"
                       (with-out-str
                         (print-outcome :none)))))

(describe "human-player!"
          (around [it] (with-out-str (it)))

          (it "returns 0 when 0 is received from *in*"
              (should= 0
                       (with-in-str "0\n"
                                    (human-player! [:empty :empty :empty
                                                    :empty :empty :empty
                                                    :empty :empty :empty]))))

          (it "returns 1 when 1 is received from *in* after receiving something that is not a number"
              (should= 1
                       (with-in-str "not-a-number\n1\n"
                                    (human-player! [:empty :empty :empty
                                                    :empty :empty :empty
                                                    :empty :empty :empty]))))

          (it "tells *out* invalid input after receiving something that is not a number"
              (should-contain "That is not a number!\n"
                              (with-out-str
                                (with-in-str "not-a-number\n1\n"
                                             (human-player! [:empty :empty :empty
                                                             :empty :empty :empty
                                                             :empty :empty :empty])))))

          (it "returns 1 when 1 is received from *in* after receiving a space that is not empty"
              (should= 1
                       (with-in-str "0\n1\n"
                                    (human-player! [:x     :empty :empty
                                                    :empty :empty :empty
                                                    :empty :empty :empty]))))

          (it "tells *out* space not empty after receiving a space that is not empty"
              (should-contain "That space is not empty!\n"
                              (with-out-str
                                (with-in-str "0\n1\n"
                                             (human-player! [:x     :empty :empty
                                                             :empty :empty :empty
                                                             :empty :empty :empty])))))

          (it "asks *out* to choose an empty space in an empty board"
              (should-contain "It is your turn! Choose one space: (0 1 2 3 4 5 6 7 8)\n"
                              (with-out-str
                                (with-in-str "0\n"
                                             (human-player! [:empty :empty :empty
                                                             :empty :empty :empty
                                                             :empty :empty :empty])))))

          (it "asks *out* to choose an empty space in a board with only two empty spaces"
              (should-contain "It is your turn! Choose one space: (7 8)\n"
                              (with-out-str
                                (with-in-str "7\n"
                                             (human-player! [:x     :o     :x
                                                             :x     :o     :x
                                                             :o     :empty :empty]))))))

(run-specs)
