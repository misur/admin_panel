(defproject admin-panel "0.1.0-SNAPSHOT"
            :description "FIXME: write description"
            :url "http://example.com/FIXME"
            :license {:name "Eclipse Public License"
                      :url "http://www.eclipse.org/legal/epl-v10.html"}
            :dependencies [[org.clojure/clojure "1.8.0"]
                           [org.clojure/java.jdbc "0.7.7"]
                           [mysql/mysql-connector-java "5.1.18"]
                           [compojure "1.5.1"]
                           [ring/ring-core "1.6.3"]
                           [ring/ring-jetty-adapter "1.6.3"]])
