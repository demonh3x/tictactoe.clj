(ns tictactoe.cli-spec
  (:require [speclj.core :refer :all]
            [tictactoe.cli :refer :all]))

(describe "render returns the text representation of a board"
          (it "with no marks"
              (should= (str "---\n"
                            "---\n"
                            "---\n")
                       (render [:empty :empty :empty
                                :empty :empty :empty
                                :empty :empty :empty])))

          (it "with a x mark"
              (should= (str "x--\n"
                            "---\n"
                            "---\n")
                       (render [:x     :empty :empty
                                :empty :empty :empty
                                :empty :empty :empty])))

          (it "with a x and a o marks"
              (should= (str "xo-\n"
                            "---\n"
                            "---\n")
                       (render [:x     :o     :empty
                                :empty :empty :empty
                                :empty :empty :empty]))))
