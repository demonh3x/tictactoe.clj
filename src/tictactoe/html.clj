(ns tictactoe.html)

(defn- render-mark [user-turn space mark]
  (let [space-to-display (+ 1 space)]
    (if (not= :empty mark)
      (name mark)
      (if user-turn
        (str "<a href='/move?space=" space "'>"
             space-to-display
             "</a>")
        space-to-display))))

(defn- render-space [user-turn space mark]
  (str "<div data-space='" (name mark) "'>"
       (render-mark user-turn space mark)
       "</div>"))

(defn- user-turn? [game]
  (and (not (:finished game))
       (:user-plays-next game)))

(defn- render-spaces [game]
  (str "<div data-board>"
       (apply str
         (map-indexed (partial render-space (user-turn? game)) (:board game)))
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

(defn- should-refresh? [game]
  (and (not (:finished game))
       (not (:user-plays-next game))))

(defn- render-refresh [game]
  (when (should-refresh? game)
    "<meta http-equiv='refresh' content='1; url=/computer-move'>"))

(defn render-game [game]
  (str "<!DOCTYPE html><html><head>"
       "<title>Tictactoe</title>"
       "<link rel='stylesheet' type='text/css' href='/styles.css'>"
       (render-refresh game)
       "</head><body>"
       "<h1>Tictactoe</h1>"
       (render-spaces game)
       "<div class='outcome'>"
       (render-outcome game)
       "</div>"
       "</body></html>"))

(defn render-menu []
  (str "<!DOCTYPE html><html><head>"
       "<title>Tictactoe</title>"
       "<link rel='stylesheet' type='text/css' href='/styles.css'>"
       "</head><body>"
       "<h1>Tictactoe</h1>"
       "<form class='menu' action='/new-game' method='GET'>"
       "<div>"
       "<p>Player x</p>"
       "<input type='radio' name='x' value='human' checked>Human"
       "<input type='radio' name='x' value='computer'>Computer"
       "</div>"
       "<div>"
       "<p>Player o</p>"
       "<input type='radio' name='o' value='human' checked>Human"
       "<input type='radio' name='o' value='computer'>Computer"
       "</div>"
       "<div>"
       "<input type='submit' value='Start game'>"
       "</div>"
       "</form>"
       "</body></html>"))
