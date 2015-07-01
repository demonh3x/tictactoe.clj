(ns tictactoe.core)

(defn- count-marks-in [board mark-type]
  (count (filter #(= mark-type %) board)))

(defn next-mark [board]
  (let [marks-of-type (partial count-marks-in board)]
    (if (< (marks-of-type :o) (marks-of-type :x))
      :o
      :x)))

(defn place-mark [board space]
  (assoc-in board [space] (next-mark board)))

(defn do-turn [player board]
  (let [chosen-space (player board)]
    (place-mark board chosen-space)))

(defn- marks-at [board line]
  (map #(nth board %) line))

(defn marks-at-lines [board]
  (let [lines [[0 1 2] [3 4 5] [6 7 8]
               [0 3 6] [1 4 7] [2 5 8]
               [0 4 8] [2 4 6]]]
    (map #(marks-at board %) lines)))

(defn same-mark? [marks]
  (every? #(and (not= :empty %)
                (= (first marks) %))
          marks))

(defn winner [board]
  (let [lines (marks-at-lines board)
        winning-lines (filter same-mark? lines)
        winner (ffirst winning-lines)]
    (or winner :none)))

(defn full? [board]
  (not-any? #(= :empty %) board))

(defn finished? [board]
  (or (full? board)
      (not= :none (winner board))))

(defn empty-spaces [board]
  (keep-indexed (fn [space mark]
                  (when (= mark :empty) space))
                board))

(defn players [x o]
  (fn [board]
    (case (next-mark board)
      :x (x board)
      :o (o board))))

(defn- take-while+ [pred coll]
  "Behaves like take-while but includes the first element for which the predicate returns false"
  (lazy-seq
    (when-let [[f & r] (seq coll)]
      (if (pred f)
        (cons f (take-while+ pred r))
        [f]))))

(defn game [player initial-board]
  (let [boards-in-each-turn (iterate (partial do-turn player) initial-board)]
    (take-while+ #(not (finished? %)) boards-in-each-turn)))

(defn- final-score [board]
  (if (= :none (winner board))
    0
    1))

(defn negamax-score [board]
  (if (finished? board)
    (final-score board)
    (let [childs (map #(negamax-score (place-mark board %))
                      (empty-spaces board))]
      (- (apply max childs)))))

(defn perfect-computer-player [board]
  (let [options (map (fn option-of [empty-space]
                       {:score (negamax-score (place-mark board empty-space))
                        :space empty-space})
                     (empty-spaces board))]
    (:space (apply max-key :score options))))
