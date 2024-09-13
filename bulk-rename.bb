#!/usr/bin/env bb

(require '[babashka.fs :as fs])
(require '[clojure.string :as str])

(let [argc (count *command-line-args*)]
  (when-not (or (= argc 2) (= argc 3))
    (println "args: [string/regex to replace] [string replacement]")
    (System/exit 1)))

(defn bulk-rename
  "str/replace all file names in directory.
   Optionally supply a path as first arg.
   Otherwise, it's the working path."
  ([replace-this with-this]
   (bulk-rename "." replace-this with-this))
  ([path replace-this with-this]
   (let [files (map fs/file-name (fs/list-dir path))
         renamed-files (map #(str/replace % replace-this with-this) files)
         mapped-files (map list files renamed-files)
         filtered-files (filter #(not= (first %) (second %)) mapped-files)]
     (if (empty? filtered-files)
       (println "nothing renamed by operation.")
       (do
         (doseq [f filtered-files] (println (first f) "->" (second f)))
         (print "Okay to rename these files? [Y/N]: ")
         (flush)
         (let [input (read-line)]
           (if (or (= input "y") (= input "Y"))
             (do
               (doseq [f filtered-files]
                 (fs/move (str path "/" (first f)) (str path "/" (second f))))
               (println "operation succeeded."))
             (println "operation aborted."))))))))

(let [[arg1 arg2 arg3] *command-line-args*
      arg1 (if (str/starts-with? arg1 "reg:") (re-pattern (subs arg1 4)) arg1)
      arg2 (if (str/starts-with? arg2 "reg:") (re-pattern (subs arg2 4)) arg2)]
  (if arg3
    (bulk-rename arg1 arg2 arg3)
    (bulk-rename arg1 arg2)))