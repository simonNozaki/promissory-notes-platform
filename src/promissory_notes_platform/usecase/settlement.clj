(ns promissory-notes-platform.usecase.settlement
  [:use [promissory-notes-platform.usecase.usecase]
        [integrant.core :as ig]])

(defrecord SettlementUseCase []
  UseCase
  (execute [this request]
    (println str "リクエスト =>" request)))


(defmethod ig/init-key ::settlement-usecase [_ {}]
  (->SettlementUseCase))
