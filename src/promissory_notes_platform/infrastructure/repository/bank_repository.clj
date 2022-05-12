(ns promissory-notes-platform.infrastructure.repository.bank-repository
  (:use [promissory-notes-platform.domain.repository.bank-repository]
        [promissory-notes-platform.domain.bank.model]
        [promissory-notes-platform.domain.account.model]
        [promissory-notes-platform.domain.account.value-object]
        [integrant.core :as ig]
        [clojure.spec.alpha :as s]))

; データベースからデータをとってくるとして、DAOレイヤを用意する
(defrecord InMemoryBankRepository []
  BankRepository
  (find [this bank branch type number holder]
    (let [account-number (if (s/valid? :promissory-notes-platform.domain.account.value-object/number number)
                           number
                           "000000")]
      (->Bank bank branch (->Account holder :normal account-number 1000000)))))

(defmethod ig/init-key ::inmemory-bank-repository [_ {}]
  (->InMemoryBankRepository))
