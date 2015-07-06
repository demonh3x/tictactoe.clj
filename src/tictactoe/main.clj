(ns tictactoe.main
  (:require [tictactoe.cli :refer [human-player! display]]
            [tictactoe.game :refer [players game]]
            [tictactoe.ai :refer [computer-player]]))

(defn -main [& args]
  (let [player-types  {"human"    human-player!
                       "computer" computer-player}
        players       (apply players (map player-types args))
        board-history (game players)]
    (doall (map display board-history))))
