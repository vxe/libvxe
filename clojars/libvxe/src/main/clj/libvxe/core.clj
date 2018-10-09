(ns libvxe.core
  (:require ;; [yaml.core]
   ;; [clojure.data.xml]
   [clj-http.client]
   [cemerick.pomegranate]
   [java-time]))

(defmacro vxe-hotload-dependency [coordinates]
  (do
    (use '[cemerick.pomegranate :only (add-dependencies)])
    `(cemerick.pomegranate/add-dependencies :coordinates '[~coordinates]
                                            :repositories (merge cemerick.pomegranate.aether/maven-central
                                                                 {"clojars" "https://clojars.org/repo"}))))

;; shell wrappers
(defn pwd []
  (System/getProperty "user.dir"))

(defn ls-ltrh
  ([]
   (println (:out (sh "ls" "-ltrh"))))
  ([value]
   (:out (sh "ls" "-ltrh"))))

(defn date-pp
  ([]
   (println (:out (sh "date"))))
  ([format]
   (println (:out (sh "date" format)))))


(defn copy-uri-to-file [uri file]
  (with-open [in (clojure.java.io/input-stream uri)
              out (clojure.java.io/output-stream file)]
    (clojure.java.io/copy in out)))


;; map manipulation
(defn merge-maps [list-of-maps]
  (->> (apply concat list-of-maps)
       (group-by key)
       (map
        (fn [[k vs]]
          [k (mapv val vs)]))
       (into {})))

(defn assoc-append [m k v]
  (if (contains? m k)
    (assoc m k (into {} [(k m)
                         v]))
    (assoc m k v)))

(defn merge-append [m1 m2]
  (first (remove nil? (for [[k2 v2] m2]
                        (if (contains? m1 k2)
                          (assoc m1 k2 (flatten [v2 (k2 m1)])))))))
