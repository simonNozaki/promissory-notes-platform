(ns promissory-notes-platform.domain.promissorynotes.model-test
  (:require [clojure.test :refer :all]
            [promissory-notes-platform.application.consts.local-date-const :refer [date-time-formatter date-format-yyyy-mm-dd]])
  (:use [promissory-notes-platform.domain.promissorynotes.model]
        [promissory-notes-platform.domain.account.model])
  (:import (java.time LocalDate)))

(defn get-local-date [local-date-string]
  (LocalDate/parse
    local-date-string
    (date-time-formatter date-format-yyyy-mm-dd)))

(deftest promissory-notes-test
  (testing "仕様を満たすこと: ファクトリメソッド"
    (let [account (->Account "" :normal "000000" 100000)
          promissory-notes (create-promissory-notes "2023-05-08" "2023-05-11" account)]
      (println (class (:maturity-at promissory-notes)))
      (is (= (instance? LocalDate (:maturity-at promissory-notes)) true)
          (= (instance? LocalDate (:presentation-at promissory-notes)) true))))
  (testing "手形を決済できること"
    (let [account (->Account "" :normal "000000" 100000)
          promissory-notes (create-promissory-notes "2023-05-08" "2023-05-11" account)
          today (get-local-date "2022-05-08")]
      (is (= (can-settle? promissory-notes today) true))))
  (testing "手形を決済できない: 支払期日が過ぎている"
    (let [account (->Account "" :normal "000000" 100000)
          promissory-notes (create-promissory-notes "2022-05-07" "2023-05-11" account)
          today (get-local-date "2022-05-08")]
      (is (= (can-settle? promissory-notes today) false))))
  (testing "手形を決済できない: 呈示期間が過ぎている"
    (let [account (->Account "" :normal "000000" 100000)
          promissory-notes (create-promissory-notes "2023-05-08" "2023-05-10" account)
          today (get-local-date "2022-05-08")]
      (is (= (can-settle? promissory-notes today) false)))))
