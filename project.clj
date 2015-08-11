(defproject tictactoe "0.1.0-SNAPSHOT"
  :description "a tictactoe implementation"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0-RC2"]
                 [ring "1.4.0"]
                 [compojure "1.4.0"]
                 [enlive "1.1.6"]]
  :profiles {:cli {:main  tictactoe.cli_main}
             :web {:dependencies [[ring "1.4.0"]
                                  [compojure "1.4.0"]
                                  [enlive "1.1.6"]]
                   :main tictactoe.web_main}
             :dev {:dependencies [[speclj "3.3.1"]
                                  [compojure "1.4.0"]
                                  [enlive "1.1.6"]]}}
  :plugins [[speclj "3.3.1"]]
  :test-paths ["spec"]
  :target-path "target/%s"
  :main tictactoe.web_main)
