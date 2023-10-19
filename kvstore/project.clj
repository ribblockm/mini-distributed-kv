(defproject kvstore "0.1.0-SNAPSHOT"
  :description "Mini Distributed Key-Value Store"
  :url "https://github.com/ribblockm/mini-distributed-kv"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [aleph "0.7.0-alpha2"]]
  :main ^:skip-aot kvstore.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
  ;; :repl-options {:init-ns kvstore.core})
