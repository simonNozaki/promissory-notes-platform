(ns promissory-notes-platform.usecase.settlement-usecase
  (:require [promissory-notes-platform.domain.bank.model :refer [bill draw]]
            [promissory-notes-platform.domain.promissorynotes.model
             :refer [create-promissory-notes can-settle?]])
  (:use [promissory-notes-platform.usecase.usecase]
        [promissory-notes-platform.domain.repository.bank-repository :as repository]
        [integrant.core :as ig]
        [taoensso.timbre :as timbre]
        [clojure.spec.alpha :as s]
        [clojure.core :as core])
  (:import (java.time LocalDate)))

; 代金決済ユースケース
(defrecord SettlementUseCase [bank-repository]
  UseCase
  (execute [this request]
    (timbre/info  "ユースケースの開始")
    ; 金額 値オブジェクトの仕様チェック
    (let [amount (:amount (:drawee request))]
      (when (core/or (not (s/valid? :promissory-notes-platform.domain.value-object/amount
                          amount))
                (nil? amount))
        nil))
    (let [maturity-at (:maturity-at request)
          presentation-at (:presentation-at request)
          amount (:amount (:drawee request))
          drawee (repository/find bank-repository
                                  (:bank (:drawee request))
                                  (:branch (:drawee request))
                                  (:type (:drawee request))
                                  (:number (:drawee request))
                                  (:holder (:drawee request)))
          promissory-notes (create-promissory-notes
                             maturity-at
                             presentation-at
                             (:account drawee))
          payee (repository/find bank-repository
                                 (:bank (:payee request))
                                 (:branch (:payee request))
                                 (:type (:payee request))
                                 (:number (:payee request))
                                 (:holder (:drawee request)))
          drawn-drawee (draw drawee amount)]
      (timbre/info "決済金額: " amount)
      (timbre/info "振出人" drawee)
      (timbre/info "受取人" payee)
      ; 決済金額が入力されてない、ないしは約束手形の日付が有効ではない
      (when (or
              (nil? drawn-drawee)
              (not (can-settle? promissory-notes (LocalDate/now))))
        nil)
      (bill payee amount)
      (timbre/info "振出人残高: " (:balance (:account drawn-drawee)))
      (timbre/info "ユースケースの終了"))))


(defmethod ig/init-key ::settlement-usecase [_ {:keys [bank-repository]}]
  (->SettlementUseCase bank-repository))
