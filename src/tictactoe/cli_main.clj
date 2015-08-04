(ns tictactoe.cli-main
  (:require [tictactoe.cli :refer [human-player! print-board print-outcome]]
            [tictactoe.game :refer [players game]]
            [tictactoe.ai :refer [computer-player]]))

(defn -main [& args]
  (let [player-types  {"human"    human-player!
                       "computer" computer-player}
        players       (apply players (map player-types args))
        game-history  (game players)]
    (doall (map print-board (map :board game-history)))
    (print-outcome (:winner (last game-history)))))
