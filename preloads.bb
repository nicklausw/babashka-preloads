(require '[babashka.fs :as fs])
(require '[clojure.string :as str])

(defn bulk-rename
  "str/replace all file names in directory.
   Optionally supply a path as first arg.
   Otherwise, it's the working path."
  ([replace-this with-this]
   (bulk-rename "." replace-this with-this))
  ([path replace-this with-this]
   (let [files (map fs/file-name (fs/list-dir path))
         renamed-files (map #(str/replace % replace-this with-this) files)
         mapped-files (map vector files renamed-files)
         filtered-files (filter #(not= (first %) (second %)) mapped-files)]
     (if (empty? filtered-files)
       (println "nothing renamed by operation.")
       (do
         (mapv #(println (first %) "->" (second %)) filtered-files)
         (print "Okay to rename these files? [Y/N]: ")
         (flush)
         (let [input (read-line)]
           (if (or (= input "y") (= input "Y"))
             (do
               (mapv #(fs/move (str path "/" (first %)) (str path "/" (second %))) filtered-files)
               (println "operation succeeded."))
             (println "operation aborted."))))))))