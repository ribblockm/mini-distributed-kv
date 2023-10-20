(ns kvstore.core
  (:gen-class)
  (:require [aleph.http :as http])
  (:import [java.util.concurrent ConcurrentHashMap]))

(defonce kv-store (atom (ConcurrentHashMap.)))

(defn put-key-value [key value]
  (let [m @kv-store]
    (doto m
      (.put key value))
    (reset! kv-store m))
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
    (http/start-server handler {:port port})))

(defn -main
  [& args]
  (let [port (Integer. (or (first args) 8080))]
    (start-server port)
    (println (str "Distributed KV Store is running on port " port))))
