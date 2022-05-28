(ns promissory-notes-platform.domain.bank.value-object
  (:require [clojure.spec.alpha :as s]))

(s/def ::name (and string? not (empty? %)))

(s/def ::branch string?)
