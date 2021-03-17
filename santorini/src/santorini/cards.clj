(ns santorini.cards
  (:require [santorini.game :as game]))
   

;; Apollo — A token’s move can optionally swap places with an adjacent opponent token, 
;; as long as the token would be able to move to the opponent’s space if the opponent 
;; token were not there; otherwise, the move must be to an unoccupied space as usual.
(defn haveCard?Apollo
  "Determine if I have the card Apollo"
  [g]
  (game/ownCardIs? g :Apollo))

(defn cardOptionsApollo
  "Starting from a game state, return the possible states unique to using the Apollo card"
  [g]
  (if (not (haveCard?Apollo g))
      g
      (game/swapTokens c1 c2)))


;; Artemis — The moved token can optionally move a second time (i.e., the same token), 
;; as long as the first move doesn’t win, and as long as the second move doesn’t 
;; return to the original space.
(defn haveCard?Artemis
  "Determine if I have the card Artemis"
  [g]
  (game/ownCardIs? g :Artemis))

(defn cardOptionsArtemis
  "Starting from a game state, return the possible states unique to using the Artemis card"
  [g]
  (if (not (haveCard?Artemis g))
      g
      ()))


;; Atlas — The build phase can build a space currently at level 0, 1, 2 to make it level 4, 
;; instead of building to exactly one more than the space’s current level.
(defn haveCard?Atlas
  "Determine if I have the card Atlas"
  [g]
  (game/ownCardIs? g :Atlas))

(defn cardOptionsAtlas
  "Starting from a game state, return the possible states unique to using the Atlas card"
  [g]
  (if (not (haveCard?Atlas g))
      g
      ()))


;; Demeter — The moved token can optionally build a second time, but not on the same space 
;; as the first build within a turn.
(defn haveCard?Demeter
  "Determine if I have the card Demeter"
  [g]
  (game/ownCardIs? g :Demeter))

(defn cardOptionsDemeter
  "Starting from a game state, return the possible states unique to using the Demeter card"
  [g]
  (if (not (haveCard?Demeter g))
      g
      ()))


;; Hephastus — The moved token can optionally build a second time, but only on the same space 
;; as the first build within a turn, and only if the second build does not reach level 4.
(defn haveCard?Hephastus
  "Determine if I have the card Hephastus"
  [g]
  (game/ownCardIs? g :Hephastus))

(defn cardOptionsHephastus
  "Starting from a game state, return the possible states unique to using the Hephastus card"
  [g]
  (if (not (haveCard?Hephastus g))
      g
      ()))


;; Minotaur — A token’s move can optionally enter the space of an opponent’s token, but only if 
;; the token can be pushed back to an unoccupied space, and only as long as the token would be able 
;; to move to the opponent’s space if the opponent token were not there. The unoccupied space where 
;; the opponent’s token is pushed can be at any level less than 4. Note that the opponent does not 
;; win by having a token forced to level 3; furthermore, such a token will have to move back down 
;; before it can move to level 3 for a win.
(defn haveCard?Minotaur
  "Determine if I have the card Minotaur"
  [g]
  (game/ownCardIs? g :Minotaur))

(defn cardOptionsMinotaur
  "Starting from a game state, return the possible states unique to using the Minotaur card"
  [g]
  (if (not (haveCard?Minotaur g))
      g
      ()))


;; Pan — A token can win either by moving up to level 3 or by moving down two or more levels. 
;; (Moving down three levels is possible if a token was pushed by a Minotaur.)
(defn haveCard?Pan
  "Determine if I have the card Pan"
  [g]
  (game/ownCardIs? g :Pan))

(defn cardOptionsPan
  "Starting from a game state, return the possible states unique to using the Pan card"
  [g]
  (if (not (haveCard?Pan g))
      g
      ()))


;; Prometheus — A token can optionally build before moving, but then the move is constrained to 
;; the same level or lower (i.e., the level of the token’s new space can be no larger than the 
;; level of the token’s old space) . The moved token must still build after moving.
(defn haveCard?Prometheus
  "Determine if I have the card Prometheus"
  [g]
  (game/ownCardIs? g :Prometheus))

(defn cardOptionsPrometheus
  "Starting from a game state, return the possible states unique to using the Prometheus card"
  [g]
  (if (not (haveCard?Prometheus g))
      g
      ()))