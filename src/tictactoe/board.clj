(ns tictactoe.board)

(def empty-board
  [:empty :empty :empty
   :empty :empty :empty
   :empty :empty :empty])

(defn- count-marks-in [board mark-type]
  (count (filter #(= mark-type %) board)))

(defn next-mark [board]
  (let [marks-of-type (partial count-marks-in board)]
    (if (< (marks-of-type :o) (marks-of-type :x))
      :o
      :x)))

(defn place-mark [at-space board]
  (assoc-in board [at-space] (next-mark board)))

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

(def winner (memoize (fn [board]
  (let [lines (marks-at-lines board)
        winning-lines (filter same-mark? lines)
        winner (ffirst winning-lines)]
    (or winner :none)))))

(defn full? [board]
  (not-any? #(= :empty %) board))

(defn finished? [board]
  (or (full? board)
      (not= :none (winner board))))

(defn empty-spaces [board]
  (keep-indexed (fn [space mark]
                  (when (= :empty mark)
                    space))
                board))
