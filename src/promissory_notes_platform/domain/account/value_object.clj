(ns promissory-notes-platform.domain.account.value-object
  (:require [clojure.spec.alpha :as s]))

(s/def ::holder (and string? not (empty? %)))

; 他にも口座種別はあるけど、必要になったら
(s/def ::type #{:normal})

(s/def ::number (and string? (fn [elm] (let [matcher (re-matcher #"[0-9]{6}" elm)]
                                         (.matches matcher)))))

(s/def ::balance nat-int?)

