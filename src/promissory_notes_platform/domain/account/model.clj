(ns promissory-notes-platform.domain.account.model
  (:require [clojure.spec.alpha :as s]))

(defrecord Account [holder type number balance])

(s/def ::account
  (s/keys :req-un [:promissory-notes-platform.domain.account.value-object/holder
                   :promissory-notes-platform.domain.account.value-object/type
                   :promissory-notes-platform.domain.account.value-object/number
                   :promissory-notes-platform.domain.account.value-object/balance]))
