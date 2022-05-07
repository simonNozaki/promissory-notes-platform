(ns promissory-notes-platform.handler.settlement-controller
  (:use [integrant.core :as ig]
        [ataraxy.response :as response]
        [promissory-notes-platform.usecase.usecase :as usecase]))

(defmethod ig/init-key ::execute [_ {:keys [settlement-usecase]}]
  (fn [{[_ params] :ataraxy/result}]
    (println params)
    (usecase/execute settlement-usecase params)
    (::response/ok #{})))
