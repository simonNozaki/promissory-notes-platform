(ns promissory-notes-platform.handler.settlement-controller
  (:use [integrant.core :as ig]
        [ataraxy.response :as response]
        [promissory-notes-platform.usecase.usecase :as usecase]
        [taoensso.timbre :as timbre]))

(defmethod ig/init-key ::execute [_ {:keys [settlement-usecase]}]
  "代金決済コントローラ"
  (fn [{[_ params] :ataraxy/result}]
    (timbre/info str "リクエスト =>" params)
    (try
      (let [result (usecase/execute settlement-usecase params)]
        (timbre/info "決済完了 =>" result)
        (::response/ok {}))
      (catch Exception e
        (timbre/error "予期しないエラー" (.printStackTrace e))
        (::response/internal-server-error {:message "unexpected error"})))))
