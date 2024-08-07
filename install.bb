#!/usr/bin/env bb

(require '[babashka.fs :as fs]
         '[clojure.string :as str]
         '[babashka.process :refer [shell]])

(def bin-dir (str (fs/home) "/bin/"))

(when-not (fs/exists? bin-dir)
  (fs/create-dir bin-dir))

(defn create-symlink
  [file link]
  (println file "->" link)
  (shell "ln -sf" file link)
  (shell "chmod +x" file)
  (shell "chmod +x" link))

(let [files (->> (fs/list-dir ".")
                 (map str)
                 (filter #(str/ends-with? % ".bb"))
                 (filter #(not (or (str/ends-with? % "preloads.bb") (str/ends-with? % "install.bb")))))
      symlinks (->> files
                    (map fs/file-name)
                    (map #(subs % 0 (- (count %) 3)))
                    (map #(str bin-dir %)))
      canons (map (comp str fs/canonicalize) files)]
  (mapv create-symlink canons symlinks))