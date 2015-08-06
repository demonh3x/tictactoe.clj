(ns tictactoe.html)

(defn- render-mark [finished space mark]
  (if (= :empty mark)
    (if finished
      space
      (str "<a href='/move?space=" space "'>"
           space
           "</a>"))
    (name mark)))

(defn- render-space [finished space mark]
  (str "<div data-space='" (name mark) "'>"
       (render-mark finished space mark)
       "</div>"))

(defn- render-spaces [game]
  (apply str
         (map-indexed (partial render-space (:finished game)) (:board game))))

(defn- render-outcome-message [winner]
  (if (= :none winner)
    "It is a draw!"
    (str "The winner is " (name winner) "!")))

(defn- render-outcome [game]
  (when (:finished game)
    (let [winner (:winner game)]
      (str "<div data-outcome='" (name winner) "'>"
         (render-outcome-message winner)
         "</div>"))))

(defn render-game [game]
  (str (render-spaces game) (render-outcome game)))
