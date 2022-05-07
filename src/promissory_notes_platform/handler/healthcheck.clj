(ns promissory-notes-platform.handler.healthcheck
  (:require [ataraxy.response :as response]
            [integrant.core :as ig]))

(defmethod ig/init-key ::execute [_ {}]
  (fn [_] [::response/ok "OK"]))
