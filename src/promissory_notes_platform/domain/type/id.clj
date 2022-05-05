(ns promissory-notes-platform.domain.type.id
  (:import (java.util UUID))
  (:require [integrant.core :as ig]))

(defprotocol Id
  (create [this]))

(defrecord BasicId []
  Id
  (create [this]
    (.toString (UUID/randomUUID))))

(defmethod ig/init-key ::basic-id [_]
  (->BasicId))
