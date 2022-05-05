(ns promissory-notes-platform.domain.bank.model
  (:require [clojure.test :refer :all]
            [clojure.spec.alpha :as s])
  (:use [promissory-notes-platform.domain.account.model]
        [promissory-notes-platform.domain.bank.model]))

(deftest bank-test
  (testing "仕様を満たす"
    (is
      (s/valid? ::bank (->Bank "三井住友銀行" "神戸営業部" (->Account "渋沢栄一" :normal "100000" 1000000)))
      true))
  (testing "取り立てができる"))