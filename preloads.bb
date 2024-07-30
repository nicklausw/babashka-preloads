(require '[babashka.fs :as fs])
(require '[clojure.string :as str])

(defn bulk-rename
  "str/replace all file names in directory.
   Optionally supply a path as first arg.
   Otherwise, it's the working path."
  ([replace-this with-this]
   (bulk-rename "." replace-this with-this))
  ([path replace-this with-this]
   (let [files (fs/list-dir path)
         renamed-files (map #(str/replace % replace-this with-this) files)]
     (mapv #(fs/move %1 %2) files renamed-files))))