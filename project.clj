(defproject onetab-to-pinboard "0.1.0-SNAPSHOT"
  :description "upload OneTab url dumps to Pinboard"
  :url "http://github.com/danneu/onetab-to-pinboard"
  :main onetab-to-pinboard.core
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :aot [onetab-to-pinboard.core]
  :plugins [[lein-bin "0.3.4"]]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [clj-http "0.9.1"]
                 [org.clojure/tools.cli "0.3.1"]]
  :bin {:name "onetab-to-pinboard"})
