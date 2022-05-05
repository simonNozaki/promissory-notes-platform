(ns promissory-notes-platform.main
  (:gen-class)
  (:require [duct.core :as duct])
  (:import (java.time LocalDateTime)))

(duct/load-hierarchy)

(defn -main [& args]
  (let [keys     (or (duct/parse-keys args) [:duct/daemon])
        profiles [:duct.profile/prod]]
    (println str (.toString (LocalDateTime/now)) "アプリケーションを開始")
    (-> (duct/resource "promissory_notes_platform/config.edn")
        (duct/read-config)
        (duct/exec-config profiles keys))))
