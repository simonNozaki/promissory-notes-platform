(ns promissory-notes-platform.domain.value-object
  (:require [clojure.spec.alpha :as s]))

(s/def ::amount nat-int?)
