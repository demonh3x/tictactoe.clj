(ns tictactoe.web
  (:require [tictactoe.game :refer [game players]]
            [tictactoe.ai :refer [computer-player]]
            [tictactoe.html :refer [render-game]]
            [compojure.core :refer [defroutes GET]]
            [compojure.route :refer [resources not-found]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.util.response :refer [redirect]]))

(def user-selected-space (atom nil))
(def running-game (atom nil))
(def playing-players (atom []))

(defn player-moving-to [space]
  (fn [board]
    @space))

(def user-player (player-moving-to user-selected-space))

(defn- user-plays? [mark]
  (= :human (@playing-players mark)))

(defn- get-game []
  (let [game (first @running-game)]
    (assoc game :user-plays-next (user-plays? (:next-mark game)))))

(defn- initialize-game-with [player-types]
  (let [player-fns (map {:human user-player
                         :computer computer-player}
                        [(:x player-types) (:o player-types)])]
    (swap! playing-players (fn [prev] player-types))
    (swap! running-game (fn [prev] (game (apply players player-fns))))))

(defn- advance-game []
  (swap! running-game (fn [running-game] (next running-game))))

(defn- set-space [space]
  (swap! user-selected-space (fn [prev] space)))

(defn- user-will-move-to [space]
  (set-space space)
  (advance-game))

(defn- space-from [request]
  (Integer. (-> request :query-params (get "space"))))

(defn- players-from [request]
  (let [params (:query-params request)]
    {:x (keyword (get params "x"))
     :o (keyword (get params "o"))}))

(defn- respond-with-game-view []
  {:body (render-game (get-game))})

(defn- board [request]
  (respond-with-game-view))

(defn- new-game [request]
  (initialize-game-with (players-from request))
  (redirect "/board"))

(defn- move [request]
  (user-will-move-to (space-from request))
  (redirect "/board"))

(defn- computer-move [request]
  (advance-game)
  (redirect "/board"))

(defn- options [request]
  {:body ""})

(defroutes routes
           (GET "/" [] options)
           (GET "/new-game" [] new-game)
           (GET "/move" [] move)
           (GET "/computer-move" [] computer-move)
           (GET "/board" [] board)
           (resources "/")
           (not-found "Not found"))

(def webapp (wrap-params routes))
