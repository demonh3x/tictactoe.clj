(ns tictactoe.game
  (:require [tictactoe.board :refer [place-mark empty-spaces finished? next-mark]]))

(defn do-turn [player board]
  (let [chosen-space (player board)]
    (place-mark chosen-space board)))

(defn- take-while+ [pred coll]
  "Behaves like take-while but includes the first element for which the predicate returns false"
  (lazy-seq
    (when-let [[f & r] (seq coll)]
      (if (pred f)
        (cons f (take-while+ pred r))
        [f]))))

(defn game [player initial-board]
  (let [boards-in-each-turn (iterate #(do-turn player %) initial-board)]
    (take-while+ #(not (finished? %)) boards-in-each-turn)))

(defn players [x o]
  (fn [board]
    (case (next-mark board)
      :x (x board)
      :o (o board))))
