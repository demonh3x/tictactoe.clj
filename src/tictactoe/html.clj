(ns tictactoe.html
  (:require [net.cgrand.enlive-html :as html]))

(html/defsnippet space-model "public/game.html"
                 [[(html/attr? :data-space) (html/nth-of-type 1)]]
                 [move-url user-turn space mark]

                 [:a] (html/do->
                        (html/set-attr :href (str move-url "?space=" space))
                        (html/content (str (+ 1 space))))
                 [(html/attr? :data-space)] (html/do->
                                              (html/set-attr :data-space (name mark))
                                              (if-not user-turn
                                                (html/content (str (+ 1 space)))
                                                identity)
                                              (if (not= :empty mark)
                                                (html/content (name mark))
                                                identity)))

(defn- outcome-message [winner]
  (if (= :none winner)
    "It is a draw!"
    (str "The winner is " (name winner) "!")))

(html/defsnippet outcome-model "public/game.html"
                 [(html/attr? :data-outcome) (html/nth-of-type 1)]
                 [menu-url winner]

                 [:a] (html/set-attr :href menu-url)
                 [(html/attr? :data-outcome-message)] (html/content (outcome-message winner))
                 [(html/attr? :data-outcome)] (html/set-attr :data-outcome (name winner)))

(defn- user-turn? [game]
  (and (not (:finished game))
       (:user-plays-next game)))

(defn- should-refresh? [game]
  (and (not (:finished game))
       (not (:user-plays-next game))))

(defn- refresh-tag [url]
  {:tag   :meta
   :attrs {:http-equiv "refresh"
           :content (str "1; url=" url)}})

(html/deftemplate game-template "public/game.html"
                  [routes game]
                  [:head] (html/append
                            (when (should-refresh? game)
                              (refresh-tag (:computer-move routes))))
                  [(html/attr? :data-board)] (html/content
                                               (let [model (partial space-model
                                                                    (:user-move routes)
                                                                    (user-turn? game))]
                                                 (map-indexed model (:board game))))
                  [(html/attr? :data-outcome)] (html/content
                                                 (when (:finished game)
                                                   (outcome-model (:menu routes) (:winner game)))))

(html/deftemplate menu-template "public/menu.html"
                  [{:keys [:new-game]}]
                  [(html/attr= :data-menu)] (html/set-attr :action new-game))

(defn- render [template & args]
  (apply str (apply template args)))

(defn render-game [routes game]
  (render game-template routes game))

(defn render-menu [routes]
  (render menu-template routes))
