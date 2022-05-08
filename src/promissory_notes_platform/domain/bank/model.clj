(ns promissory-notes-platform.domain.bank.model
  (:require [clojure.spec.alpha :as s])
  (:use [promissory-notes-platform.domain.account.model]
        [promissory-notes-platform.domain.value-object]))

(s/def ::bank
  (s/keys :req-un [:value-object/name
                   :value-object/branch
                   :promissory-notes-platform.domain.account.model/account]))

; 金融機関基底プロトコル
(defprotocol Balanceable
  (draw [this amount])
  (bill [this amount]))

(s/fdef bill
        :args (s/cat :this ::bank
                     :amount :promissory-notes-platform.domain.value-object/amount)
        :ret ::bank)

(s/fdef draw
        :args (s/cat :this ::bank
                     :amount :promissory-notes-platform.domain.value-object/amount)
        :ret ::bank)

(defrecord Bank [name branch account]
  Balanceable
  (draw [this amount]
    (let [name (:name this)
          branch (:branch this)
          account (:account this)
          current-balance (:balance account)
          net-amount (- current-balance amount)]
      ; 残高はマイナスにならない
      (if (< net-amount 0)
        nil
        (->Bank name branch (assoc account :balance net-amount)))))
  (bill [this amount]
    (let [name (:name this)
          branch (:branch this)
          account (:account this)
          current-balance (:balance account)
          net-amount (+ current-balance amount)]
      (->Bank name branch (assoc account :balance net-amount)))))

