(ns promissory-notes-platform.application.consts.local-date-const
  (:import (java.time.format DateTimeFormatter)))

; 日付フォーマット
(def date-format-yyyy-mm-dd "yyyy-MM-dd")

; 日付フォーマッタ
(defn date-time-formatter [format]
  (DateTimeFormatter/ofPattern format))
