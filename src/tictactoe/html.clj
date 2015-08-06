(ns tictactoe.html)

(defn- render-mark [finished space mark]
  (if (= :empty mark)
    (if finished
      (+ 1 space)
      (str "<a href='/move?space=" space "'>"
           (+ 1 space)
           "</a>"))
    (name mark)))

(defn- render-space [finished space mark]
  (str "<div data-space='" (name mark) "'>"
       (render-mark finished space mark)
       "</div>"))

(defn- render-spaces [game]
  (str "<div data-board>"
       (apply str
         (map-indexed (partial render-space (:finished game)) (:board game)))
       "</div>"))

(defn- render-outcome-message [winner]
  (if (= :none winner)
    "It is a draw!"
    (str "The winner is " (name winner) "!")))

(defn- render-outcome [game]
  (when (:finished game)
    (let [winner (:winner game)]
      (str "<div data-outcome='" (name winner) "'>"
           (render-outcome-message winner)
           "</div>"
           "<a href='/'>Play again</a>"))))

(defn render-game [game]
  (str "<!DOCTYPE html><html><head>"
       "<title>Tictactoe</title>"
       "<link rel='stylesheet' type='text/css' href='/styles.css'>"
       "</head><body>"
       "<h1>Tictactoe</h1>"
       (render-spaces game)
       (render-outcome game)
       "</body></html>"))
