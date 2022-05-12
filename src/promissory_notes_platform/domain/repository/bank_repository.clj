(ns promissory-notes-platform.domain.repository.bank-repository
  (:use [clojure.spec.alpha :as s]
        [promissory-notes-platform.domain.bank.model]))

(s/fdef find
        :args (s/cat :bank   :promissory-notes-platform.domain.bank.model/bank
                     :branch :promissory-notes-platform.domain.bank.value-object/branch
                     :type   :promissory-notes-platform.domain.account.value-object/type
                     :number :promissory-notes-platform.domain.account.value-object/number)
        :ret :promissory-notes-platform.domain.bank.model/bank)

(defprotocol BankRepository
  (find [this bank branch type number holder]))