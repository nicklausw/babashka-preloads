#!/usr/bin/env bb

(require '[clojure.string :as str])

(let [argc (count *command-line-args*)]
  (when-not (or (= argc 2) (= argc 3))
    (println "args: [string/regex to replace] [string replacement]")
    (System/exit 1)))

(let [[arg1 arg2 arg3] *command-line-args*
      arg1 (if (str/starts-with? arg1 "reg:") (re-pattern (subs arg1 4)) arg1)
      arg2 (if (str/starts-with? arg2 "reg:") (re-pattern (subs arg2 4)) arg2)]
  (if arg3
    (bulk-rename arg1 arg2 arg3)
    (bulk-rename arg1 arg2)))