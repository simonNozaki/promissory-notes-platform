(ns promissory-notes-platform.domain.value-object
  (:require [clojure.spec.alpha :as s]
            [clojure.core :as core]))

(s/def ::amount (core/and integer? nat-int?))
