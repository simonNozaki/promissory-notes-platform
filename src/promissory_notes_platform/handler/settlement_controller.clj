(ns promissory-notes-platform.handler.settlement-controller
  (:require [promissory-notes-platform.application.consts.error-const :refer [error-messages]])
  (:use [integrant.core :as ig]
        [ataraxy.response :as response]
        [promissory-notes-platform.usecase.usecase :as usecase]
        [taoensso.timbre :as timbre])
  (:import (java.time LocalDateTime)))

(defmethod ig/init-key ::execute [_ {:keys [settlement-usecase]}]
  "代金決済コントローラ"
  (fn [{[_ params] :ataraxy/result}]
    (timbre/info "リクエスト =>" params)
    (try
      (let [result (usecase/execute settlement-usecase params)
            response (if (nil? result)
                       {:message (:e.domain.value-object.spec-error error-messages)}
                       {})]
        (timbre/info "決済完了 =>" result)
        (timbre/info "レスポンス => " response)
        [::response/ok (assoc response :settled-at (LocalDateTime/now))])
      (catch Exception e
        (timbre/error "予期しないエラー" (.printStackTrace e))
        [::response/internal-server-error {:message (:e.system.unexpected-error error-messages)}]))))
