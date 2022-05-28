(ns promissory-notes-platform.domain.bank.model-test
  (:require [clojure.test :refer :all]
            [clojure.spec.alpha :as s])
  (:use [promissory-notes-platform.domain.account.model]
        [promissory-notes-platform.domain.bank.model]))

(deftest bank-test
  (testing "仕様を満たす"
    (is
      (s/valid? :promissory-notes-platform.domain.bank.model/bank
                (->Bank "三井住友銀行" "神戸営業部" (->Account "渋沢栄一" :normal "100000" 1000000)))
      true))
  (testing "仕様を満たさない: 銀行名が空"
    (let [bank (->Bank "" "神戸営業部" (->Account "渋沢栄一" :time-deposit "100000" 1000000))]
      (is
        (s/valid? :promissory-notes-platform.domain.bank.model/bank
                  bank)
        false)))
  (testing "仕様を満たさない: 口座種別が不正"
    (let [bank (->Bank "三井住友銀行" "神戸営業部" (->Account "渋沢栄一" :time-deposit "100000" 1000000))]
      (is
        (s/valid? :promissory-notes-platform.domain.bank.model/bank
                  bank)
        false)))
  (testing "仕様を満たさない: 口座番号が6桁ではない"
    (let [bank (->Bank "三井住友銀行" "神戸営業部" (->Account "渋沢栄一" :normal "00001" 1000000))]
      (is
        (= (s/valid? :promissory-notes-platform.domain.bank.model/bank
                     bank)
        true))))
  (testing "取り立てができる"
    (let [payee (->Bank "三井住友銀行" "神戸営業部" (->Account "渋沢栄一" :normal "100000" 1000000))
          payed (bill payee 500000)]
      (is
        (:balance (:account payed))
        1500000)))
  (testing "引き落とし: できる"
    (let [drawee (->Bank "三井住友銀行" "神戸営業部" (->Account "渋沢栄一" :normal "100000" 1000000))
          drawn (draw drawee 700000)
          actual (:balance (:account drawn))]
      (println actual)
      (is (= actual 300000))))
  (testing "引き落とし: 残高不足"
    (let [drawee (->Bank "三井住友銀行" "神戸営業部" (->Account "渋沢栄一" :normal "100000" 1000000))
          drawn (draw drawee 1700000)
          actual (:balance (:account drawn))]
      (println actual)
      (is (= actual nil)))))
