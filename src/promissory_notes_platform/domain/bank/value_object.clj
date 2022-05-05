(ns promissory-notes-platform.domain.bank.value-object
  (:require [clojure.spec.alpha :as s]))

(s/def ::name string?)

(s/def ::branch string?)
