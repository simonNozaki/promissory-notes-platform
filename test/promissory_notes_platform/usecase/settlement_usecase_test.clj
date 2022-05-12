(ns promissory-notes-platform.usecase.settlement-usecase-test
  (:require [clojure.test :refer :all]
            [promissory-notes-platform.usecase.usecase :refer [execute]])
  (:use [promissory-notes-platform.usecase.settlement-usecase]
        [promissory-notes-platform.infrastructure.repository.bank-repository]))

(def basic-request
  "ユースケースリクエスト基本形"
  {:drawee {:amount 300000
            :holder "藤原道長"
            :bank "三井住友銀行"
            :branch "神戸営業部"
            :type :normal
            :number "0000001"}
   :payee {:holder "中臣鎌足"
           :bank "ゆうちょ銀行"
           :branch "本店"
           :type :normal
           :number "0000001"}
   :maturity-at "2023-05-08"
   :presentation-at "2023-05-11"})

(deftest settlement-usecase-test
  (testing "代金決済できる"
    (let [request basic-request
          usecase (->SettlementUseCase (->InMemoryBankRepository))
          result (execute usecase request)]
      (println result)
      (is (not (nil? (seq result))))
      (is (= (:balance (:account (:payee result))) 1300000))
      (is (= (:balance (:account (:drawee result))) 700000)))))
