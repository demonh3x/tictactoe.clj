(ns tictactoe.main
  (:require [tictactoe.cli :refer [human-player! display]]
            [tictactoe.core :refer [players game]]
            [tictactoe.ai :refer [computer-player]]))

(defn -main [& args]
  (let [initial-board [:empty :empty :empty
                       :empty :empty :empty
                       :empty :empty :empty]
        player-types  {"human"    human-player!
                       "computer" computer-player}
        players       (apply players (map player-types args))
        board-history (game players initial-board)]
    (doall (map display board-history))))
