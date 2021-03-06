(defproject ednstore "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [log4j/log4j "1.2.17" :exclusions [javax.mail/mail
                                                    javax.jms/jms
                                                    com.sun.jmdk/jmxtools
                                                    com.sun.jmx/jmxri]]
                 [nio "1.0.3"]
                 [com.taoensso/nippy "2.8.0"]

                 ;[com.stuartsierra/component "0.3.2"]


                 ;ednserver
                 [compojure "1.4.0"]
                 [http-kit "2.1.19"]
                 [ring "1.4.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/tools.cli "0.3.5"]
                 ]
  :aot [ednstore.core]
  )
