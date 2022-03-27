(defproject org.clojars.rorokimdim/mindra "0.0.2"
  :description "A clojure client library for mindra."
  :url "https://github.com/rorokimdim/mindra-clj"
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"}

  :plugins [[cider/cider-nrepl "0.28.1"]
            [mx.cider/enrich-classpath "1.5.2"]
            [lein-shell "0.5.0"]]

  :dependencies [[org.clojure/clojure "1.10.1"]
                 [babashka/process "0.1.1"]]

  :repl-options {:init-ns mindra.repl}
  :aliases {"lint" ["shell" "clj-kondo" "--lint" "src" "test" "dev"]}


  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}
             :repl {:source-paths ["dev"]
                    :dependencies [[criterium "0.4.6"]]}})
