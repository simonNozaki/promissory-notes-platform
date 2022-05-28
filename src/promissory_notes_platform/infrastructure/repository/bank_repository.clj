(ns promissory-notes-platform.infrastructure.repository.bank-repository
  (:use [promissory-notes-platform.domain.repository.bank-repository]
        [promissory-notes-platform.domain.bank.model]
        [promissory-notes-platform.domain.account.model]
        [promissory-notes-platform.domain.account.value-object]
        [integrant.core :as ig]
        [clojure.spec.alpha :as s]
        [promissory-notes-platform.infrastructure.dao.bank-dao :as dao]))

; データベースからデータをとってくるとして、DAOレイヤを用意する
(defrecord InMemoryBankRepository [bank-dao]
  BankRepository
  (find [this bank branch type number holder]
    (let [account-number (if (s/valid? :promissory-notes-platform.domain.account.value-object/number number)
                           number
                           "000000")]
      (dao/findByBank bank-dao bank branch type account-number holder))))

(defmethod ig/init-key ::inmemory-bank-repository [_ {:keys [bank-dao]}]
  (->InMemoryBankRepository bank-dao))
