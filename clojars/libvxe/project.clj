(defproject libvxe "0.3.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :min-lein-version "2.7.1"

  :dependencies [[clomacs "0.0.3-SNAPSHOT"]
                 [org.clojure/data.codec "0.1.1"]
                 ;; clj-vxe
                 [com.cemerick/pomegranate "0.4.0"]
                 [com.cemerick/url "0.1.1"]
                 [org.clojure/data.xml "0.0.8"]
                 [me.raynes/conch "0.8.0"]
                 ;; ad
                 [duratom "0.4.1"]
                 ;; wiggle board
                 [metosin/compojure-api "1.1.11"]
                 [ring/ring-core "1.6.3"]
                 [ring/ring-jetty-adapter "1.6.3"]
                 [ring/ring-devel "1.6.3"]
                 [ring/ring-json "0.4.0"]
                 [ring-middleware "0.1.0-SNAPSHOT"]
                 [ring "1.6.3"]
                 [org.clojure/tools.logging "0.2.4"]
                 [log4j/log4j "1.2.17" :exclusions [javax.mail/mail
                                                    javax.jms/jms
                                                    com.sun.jmdk/jmxtools
                                                    com.sun.jmx/jmxri]]
                 [clojure.java-time "0.3.2"]
                 [doric "0.9.0"]
                 [io.forward/yaml "1.0.9"]
                 [com.rpl/specter "1.1.1"]
                 ;; nzgb
                 ;; [clj-vxe "0.4.0-SNAPSHOT"]
                 [cheshire "5.8.0"]
                 [clj-http "3.8.0"] 
                 [org.clojure/clojurescript "1.10.238"]
                 [org.clojure/core.async  "0.4.474"]
                 [reagent "0.7.0"]
                 ;; java
                 [org.apache.commons/commons-math3 "3.6.1"]
                 ;; core
                 [org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.238"]
                 [org.clojure/core.async  "0.4.474"]
                 [reagent "0.7.0"]]

  :plugins [[lein-ring "0.12.0"]
            [lein-figwheel "0.5.16"]
            [lein-cljsbuild "1.1.7" :exclusions [[org.clojure/clojure]]]]


  :source-paths ["src/main/clj"]
  :java-source-paths ["src/main/java"]
  ;; :main server.handler
  :repl-options {:init-ns libvxe.core}
  :ring {:handler libvxe.handler/app
         :nrepl {:start? true
                 :port 9003}
         }

  ;; :cljsbuild {:builds
  ;;             [{:id "dev"
  ;;               :source-paths ["src"]

  ;;               ;; The presence of a :figwheel configuration here
  ;;               ;; will cause figwheel to inject the figwheel client
  ;;               ;; into your build
  ;;               :figwheel {:on-jsload "libvxe.core/on-js-reload"
  ;;                          ;; :open-urls will pop open your application
  ;;                          ;; in the default browser once Figwheel has
  ;;                          ;; started and compiled your application.
  ;;                          ;; Comment this out once it no longer serves you.
  ;;                          :open-urls ["http://localhost:3449/index.html"]}

  ;;               :compiler {:main libvxe.core
  ;;                          :asset-path "js/compiled/out"
  ;;                          :output-to "resources/public/js/compiled/libvxe.js"
  ;;                          :output-dir "resources/public/js/compiled/out"
  ;;                          :source-map-timestamp true
  ;;                          ;; To console.log CLJS data-structures make sure you enable devtools in Chrome
  ;;                          ;; https://github.com/binaryage/cljs-devtools
  ;;                          :preloads [devtools.preload]}}
  ;;              ;; This next build is a compressed minified build for
  ;;              ;; production. You can build this with:
  ;;              ;; lein cljsbuild once min
  ;;              {:id "min"
  ;;               :source-paths ["src"]
  ;;               :compiler {:output-to "resources/public/js/compiled/libvxe.js"
  ;;                          :main libvxe.core
  ;;                          :optimizations :advanced
  ;;                          :pretty-print false}}]}

  ;; :figwheel { ;; :http-server-root "public" ;; default and assumes "resources"
  ;;            ;; :server-port 3449 ;; default
  ;;            ;; :server-ip "127.0.0.1"

  ;;            :css-dirs ["resources/public/css"] ;; watch and update CSS

  ;;            ;; Start an nREPL server into the running figwheel process
  ;;            ;; :nrepl-port 7888

  ;;            ;; Server Ring Handler (optional)
  ;;            ;; if you want to embed a ring handler into the figwheel http-kit
  ;;            ;; server, this is for simple ring servers, if this

  ;;            ;; doesn't work for you just run your own server :) (see lein-ring)

  ;;            ;; :ring-handler hello_world.server/handler

  ;;            ;; To be able to open files in your editor from the heads up display
  ;;            ;; you will need to put a script on your path.
  ;;            ;; that script will have to take a file path and a line number
  ;;            ;; ie. in  ~/bin/myfile-opener
  ;;            ;; #! /bin/sh
  ;;            ;; emacsclient -n +$2 $1
  ;;            ;;
  ;;            ;; :open-file-command "myfile-opener"

  ;;            ;; if you are using emacsclient you can just use
  ;;            ;; :open-file-command "emacsclient"

  ;;            ;; if you want to disable the REPL
  ;;            ;; :repl false

  ;;            ;; to configure a different figwheel logfile path
  ;;            ;; :server-logfile "tmp/logs/figwheel-logfile.log"

  ;;            ;; to pipe all the output to the repl
  ;;            ;; :server-logfile false
  ;;            }


  ;; ;; Setting up nREPL for Figwheel and ClojureScript dev
  ;; ;; Please see:
  ;; ;; https://github.com/bhauman/lein-figwheel/wiki/Using-the-Figwheel-REPL-within-NRepl
  ;; :profiles {:dev {:dependencies [[binaryage/devtools "0.9.9"]
  ;;                                 [figwheel-sidecar "0.5.16"]
  ;;                                 [cider/piggieback "0.3.1"]]
  ;;                  ;; need to add dev source path here to get user.clj loaded
  ;;                  :source-paths ["src" "dev"]
  ;;                  ;; for CIDER
  ;;                  ;; :plugins [[cider/cider-nrepl "0.12.0"]]
  ;;                  :repl-options {:nrepl-middleware [cider.piggieback/wrap-cljs-repl]}
  ;;                  ;; need to add the compliled assets to the :clean-targets
  ;;                  :clean-targets ^{:protect false} ["resources/public/js/compiled"
  ;; :target-path]}}

  )
