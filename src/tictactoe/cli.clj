(ns tictactoe.cli)

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
