(ns promissory-notes-platform.infrastructure.dao.bank-dao
  (:use [promissory-notes-platform.domain.account.model]
        [promissory-notes-platform.domain.bank.model]
        [taoensso.timbre :as timbre])
  (:require [integrant.core :as ig]))

; 銀行講座DAOプロトコル
(defprotocol BankDao
  (findByBank [this bank branch type number holder]))

; インメモリ銀行口座DAOレコード
(defrecord InMemoryBankDao []
  BankDao
  (findByBank [this bank branch type number holder]
    (timbre/info "[find] インメモリDAO")
    (->Bank bank branch (->Account holder type number 1000000))))

(defmethod ig/init-key ::inmemory-bank-dao [_ {}]
  (->InMemoryBankDao))
