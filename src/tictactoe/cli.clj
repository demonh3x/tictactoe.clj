(ns tictactoe.cli
  (:require [tictactoe.core :refer :all]))

(defn- render-mark [mark]
  (case mark
    :empty "-"
    (name mark)))

(defn- render-rows [board]
  (partition 3 (map render-mark board)))

(defn- add-newline [row]
  (cons row "\n"))

(defn- to-string [rows]
  (apply str (flatten rows)))

(defn render [board]
  (to-string (map add-newline (render-rows board))))

(defn- read-int! []
  (try
    (Integer/parseInt (read-line))
    (catch NumberFormatException e
      (println "That is not a number!")
      (read-int!))))

(defn- print-to-choose-from [empty-spaces]
  (println "It is your turn! Choose one space:" (pr-str empty-spaces)))

(defn- ask-for [empty-space?]
  (let [choice (read-int!)]
    (when-not (empty-space? choice)
      (println "That space is not empty!"))
    choice))

(defn- first [predicate seq]
  (some predicate seq))

(defn- ask-until-is-one-of [empty-spaces]
  (let [empty-space? (fn [space]
                       ((set empty-spaces) space))
        choices (repeatedly #(ask-for empty-space?))]
    (print-to-choose-from empty-spaces)
    (first empty-space? choices)))

(defn human-player! [board]
  (ask-until-is-one-of (empty-spaces board)))
