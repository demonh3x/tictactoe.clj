(ns tictactoe.web
  (:require [tictactoe.game :refer [game]]
            [tictactoe.board :refer [finished?]]
            [tictactoe.html :refer [render-game]]
            [compojure.core :refer [defroutes GET]]
            [compojure.route :refer [resources not-found]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.util.response :refer [redirect]]))

(defn player-moving-to [space]
  (fn [board]
    @space))

(def user-selected-space (atom nil))
(def running-game (atom nil))

(defn- get-game []
  (let [game (first @running-game)]
    (assoc game :finished (finished? (:board game)))))

(defn- initialize-game []
  (swap! running-game (fn [prev] (game (player-moving-to user-selected-space)))))

(defn- advance-game []
  (swap! running-game (fn [running-game] (next running-game))))

(defn- set-space [space]
  (swap! user-selected-space (fn [prev] space)))

(defn- user-will-move-to [space]
  (set-space space)
  (advance-game))

(defn- space-from [request]
  (Integer. (-> request :query-params (get "space"))))

(defn- respond-with-game-view []
  {:body (render-game (get-game))})

(defn- board [request]
  (respond-with-game-view))

(defn- new-game [request]
  (initialize-game)
  (redirect "/board"))

(defn- move [request]
  (user-will-move-to (space-from request))
  (redirect "/board"))

(defroutes routes
           (GET "/" [] new-game)
           (GET "/move" [] move)
           (GET "/board" [] board)
           (resources "/")
           (not-found "Not found"))

(def webapp (wrap-params routes))
