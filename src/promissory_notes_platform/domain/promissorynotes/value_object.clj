(ns promissory-notes-platform.domain.promissorynotes.value-object
  (:import (java.time LocalDate))
  (:use [clojure.spec.alpha :as s]))

(defn local-date? [date-time]
  (instance? LocalDate date-time))

; 支払期日
(s/def ::maturity-at #(local-date? %))

; 呈示期間
(s/def ::presentation-at #(local-date? %))

