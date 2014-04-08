(ns onetab-to-pinboard.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clj-http.client :as client]
            [clojure.tools.cli :as cli])
  (:gen-class))

;; Return http response.
(defn submit-bookmark! [{:keys [url title] :as bookmark} token]
  (client/get "https://api.pinboard.in/v1/posts/add"
              {:query-params {:auth_token token
                              :url url
                              :description title
                              :format "json"}
               :as :json
               :throw-exceptions false}))

;; Returns set of {:url _ :title _}, title defaults to empty string.
(defn slurp-bookmarks [file-path]
  (with-open [reader (io/reader file-path)]
    (->> (for [line (->> (line-seq reader)
                         (remove empty?))]
           (let [[url title] (->> (str/split line #"\|" 2)
                                  (map str/trim))]
             {:url url :title (or title "")}))
         (into #{}))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def cli-opts
  [["-h" "--help"]
   ["-t" "--token TOKEN" "Your Pinboard auth token (Found here: https://pinboard.in/settings/password)"]
   ["-f" "--file FILE" "Path to OneTab url dump"]])

(defn -main [& args]
  (let [user-opts (cli/parse-opts args cli-opts)]
    (if (or (empty? (:options user-opts)) (:help (:options user-opts)))
      ;; User wanted help
      (println (str "USAGE:" \newline
                    "  ./onetab-to-pinboard --token danneu:AABBCCDDEEFFAABBCCDD --file ./url-export.txt" \newline
                    "OPTIONS:" \newline
                    (:summary user-opts)))
      ;; Submit the bookmarks
      (let [{:keys [token file]} (:options user-opts)
            bookmarks (slurp-bookmarks file)]
        (println (str "Found " (count bookmarks) " bookmarks to submit."))
        (doseq [bookmark bookmarks]
          (println (str \newline "=> " (:url bookmark)))
          (let [response (submit-bookmark! bookmark token)]
            (println (str "<= " (select-keys response [:request-time
                                                       :status
                                                       :body])))
            ;; Pinboard requests one API hit per three seconds
            (dotimes [_ 3]
              (Thread/sleep 1000)
              (print ".") (flush))))))))
