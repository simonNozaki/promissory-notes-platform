(ns promissory-notes-platform.handler.healthcheck-controller
  (:require [ataraxy.response :as response]
            [integrant.core :as ig]))

(defmethod ig/init-key ::execute [_ {}]
  (fn [_] [::response/ok "OK"]))
