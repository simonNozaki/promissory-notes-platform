(ns promissory-notes-platform.handler.healthcheck
  (:require [ataraxy.response :as response]
            [integrant.core :as ig]))

(defmethod ig/init-key ::execute [_ {:keys [id]}]
  (println id)
  (fn [_] [::response/ok id]))
