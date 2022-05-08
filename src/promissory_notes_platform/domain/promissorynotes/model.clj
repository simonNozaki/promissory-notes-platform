(ns promissory-notes-platform.domain.promissorynotes.model
  (:use [clojure.spec.alpha :as s]
        [promissory-notes-platform.domain.promissorynotes.value-object :as pvo]
        [promissory-notes-platform.domain.account.model :as account]
        [promissory-notes-platform.application.consts.local-date-const]
        [clojure.core :as core])
  (:import (java.time LocalDate)))

; 約束手形レコード仕様
(s/def ::promissory-notes
  (s/keys :req-un [::pvo/maturity-at
                   ::pvo/presentation-at
                   ::account/account]))

; 手形基底プロトコル
(defprotocol Notes
  (can-settle? [this today]))

; 約束手形ドメインモデル
(defrecord PromissoryNotes [maturity-at presentation-at account]
  Notes
  (can-settle? [this today]
    (let [minimum-maturity (.plusDays maturity-at 3)]
      (and
        ; 支払い期日は今日以降
        (.isAfter maturity-at today)
        ; 呈示期間は期日より3日後になっている
        (core/or
          (.isAfter presentation-at minimum-maturity)
          (.isEqual presentation-at minimum-maturity))))))

; 約束手形ファクトリメソッド仕様定義
(s/fdef ::create-promissory-notes
        :args (s/cat :maturity-at string?
                     :presentation-at string?
                     :account ::account/account)
        :ret ::promissory-notes)

; 約束手形ファクトリメソッド。主に日付を文字列からLocalDateに変換
(defn create-promissory-notes [maturity-at presentation-at account]
  (let [maturity-at-local-date (LocalDate/parse
                                 maturity-at
                                 (date-time-formatter date-format-yyyy-mm-dd))
        presentation-at-local-date (LocalDate/parse
                                    presentation-at
                                    (date-time-formatter date-format-yyyy-mm-dd))]
    (->PromissoryNotes
      maturity-at-local-date
      presentation-at-local-date
      account)))
