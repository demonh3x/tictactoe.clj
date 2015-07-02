(ns tictactoe.ai
  (:require [tictactoe.core :refer :all]))

(defn- final-score [board]
  (if (= :none (winner board))
    0
    1))

(defn- negamax [depth board]
  (cond
    (finished? board) (* (+ 1 depth) (final-score board))
    (= 0 depth) 0
    :else (let [childs (map #(negamax (- depth 1) (place-mark % board))
                            (empty-spaces board))]
            (- (apply max childs)))))

(def minimum-depth-to-avoid-losing 5)
(def score #(negamax minimum-depth-to-avoid-losing %))

(defn computer-player [board]
  (let [option-of (fn [empty-space]
                    {:score (score (place-mark empty-space board))
                     :space empty-space})
        options   (map option-of (empty-spaces board))]
    (:space (apply max-key :score options))))
