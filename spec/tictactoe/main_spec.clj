(ns tictactoe.main-spec
  (:require [speclj.core :refer :all]
            [tictactoe.main :refer :all]))

(describe "display"
          (it "prints the rendered board"
              (should= (str "xo-\n"
                            "---\n"
                            "---\n"
                            "\n")
                       (with-out-str
                         (display [:x     :o     :empty
                                   :empty :empty :empty
                                   :empty :empty :empty]))))

          (it "prints x as the winner"
              (should= (str "xxx\n"
                            "oo-\n"
                            "---\n"
                            "\n"
                            "x has won!\n")
                       (with-out-str
                         (display [:x     :x     :x
                                   :o     :o     :empty
                                   :empty :empty :empty]))))

          (it "prints o as the winner"
              (should= (str "xx-\n"
                            "ooo\n"
                            "x--\n"
                            "\n"
                            "o has won!\n")
                       (with-out-str
                         (display [:x     :x     :empty
                                   :o     :o     :o
                                   :x     :empty :empty]))))

          (it "prints the draw message"
              (should= (str "xxo\n"
                            "oox\n"
                            "xxo\n"
                            "\n"
                            "it is a draw!\n")
                       (with-out-str
                         (display [:x     :x     :o
                                   :o     :o     :x
                                   :x     :x     :o])))))

(describe "main"
          (it "runs a game between two humans"
              (should-contain (str "xxx\n"
                                   "oo-\n"
                                   "---\n"
                                   "\n"
                                   "x has won!\n")
                              (with-out-str
                                (with-in-str "0\n3\n1\n4\n2\n"
                                             (-main "human" "human")))))

          (it "runs a game between two computers"
              (should-contain "it is a draw!"
                              (with-out-str
                                (-main "computer" "computer")))))

(run-specs)
