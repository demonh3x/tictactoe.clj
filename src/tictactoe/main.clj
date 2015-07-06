(ns tictactoe.main
  (:require [tictactoe.cli :refer [human-player! print-board print-outcome]]
            [tictactoe.board :refer [finished? winner]]
            [tictactoe.game :refer [players game]]
            [tictactoe.ai :refer [computer-player]]))

(defn display [board]
  (print-board board)
  (println)
  (when (finished? board)
    (print-outcome (winner board))))

(defn -main [& args]
  (let [player-types  {"human"    human-player!
                       "computer" computer-player}
        players       (apply players (map player-types args))
        board-history (game players)]
    (doall (map display board-history))))
