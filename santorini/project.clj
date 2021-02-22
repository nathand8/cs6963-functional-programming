(defproject santorini "0.1.0-SNAPSHOT"
  :description "Santorini Game"
  :url "https://github.com/nathand8/cs6963-functional-programming"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/data.json "1.0.0"]
                 [org.clojure/math.combinatorics "0.1.6"]]
  :main ^:skip-aot santorini.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
