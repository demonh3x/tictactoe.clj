(ns tictactoe.core)

(defn- count-marks-in [board mark-type]
  (count (filter #(= mark-type %) board)))

(defn next-mark [board]
  (let [marks-of-type (partial count-marks-in board)]
    (if (< (marks-of-type :o) (marks-of-type :x))
      :o
      :x)))

(defn do-turn [board player]
  (let [chosen-space (player)]
    (assoc-in board [chosen-space] (next-mark board))))
