(ns promissory-notes-platform.domain.bank.model
  (:require [clojure.spec.alpha :as s])
  (:use [promissory-notes-platform.domain.account.model]
        [promissory-notes-platform.domain.value-object]))

(s/def ::bank
  (s/keys :req-un [:value-object/name
                   :value-object/branch
                   :promissory-notes-platform.domain.account.model/account]))

(defprotocol Collectable
  (bill [this amount]))

(s/fdef bill
        :args (s/cat :this ::bank
                     :amount :promissory-notes-platform.domain.value-object/amount)
        :ret ::bank)

(defrecord Bank [name branch account]
  Collectable
  (bill [this amount]
    (let [name (:name this)
          branch (:branch this)
          account (:account this)
          current-balance (:balance account)
          net-amount (+ current-balance amount)]
      (->Bank name branch (assoc account :balance net-amount)))))

