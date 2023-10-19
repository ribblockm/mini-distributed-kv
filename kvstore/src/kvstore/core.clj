(ns kvstore.core
  (:gen-class)
  (:require [aleph.http :as http]
            [aleph.tcp :as tcp])
  (:import [java.util.concurrent ConcurrentHashMap]))

(defonce kv-store (atom (ConcurrentHashMap.)))

(defn put-key-value [key value]
  (swap! kv-store assoc key value)
  "OK")

(defn get-value [key]
  (get @kv-store key))

(defn remove-key [key]
  (swap! kv-store dissoc key)
  "OK")

(defn start-server [port]
  (let [handler (fn [req]
                  (let [path (:uri req)
                        response (case path
                                   "/get" (get-value (:query-params req))
                                   "/put" (let [params (:query-params req)]
                                            (put-key-value (:key params) (:value params)))
                                   "/remove" (remove-key (:query-params req))
                                   :else "Invalid Request")]
                    {:status 200 :headers {"Content-Type" "text/plain"} :body response}))]
    (tcp/start-server handler {:port port})))

(defn -main
  [& args]
  (let [port (Integer. (or (first args) 8080))]
    (start-server port)
    (println (str "Distributed KV Store is running on port " port))))
