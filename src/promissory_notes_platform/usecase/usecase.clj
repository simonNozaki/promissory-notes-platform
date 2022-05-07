(ns promissory-notes-platform.usecase.usecase)

(defprotocol UseCase
  "ユースケース基底プロトコル"
  (execute [this request]))
