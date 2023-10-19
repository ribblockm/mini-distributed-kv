# mini-distributed-kv
Implementing a kv store in Clojure, mainly for studying.

For this implementation, we will use Leiningen as our build tool.

I want to make some observations:

### Why `(:gen-class)`
It tells the Clojure compiler to create a corresponding Java class, to make it accessible to Java projects and interfaces.
The `-main` function serves as an entry point for the Java application, and it is marked with a `-` prefix, indicating it should be made accessible to Java code as an instance method.

### The importance of `defonce`
When you implement a distributed system you must ensure consistency and shared state across all parts of your application.
`defonce` creates a single shared reference to a mutable data structure. A shared and mutable reference allows you to share and modify data across your application without the need to pass the reference explicitly.

### `atom` and `ConcurrentHashMap`
By wrapping a `ConcurrentHashMap` in an `atom` you create `kv-store` as a thread-safe data structure.
This is useful in a concurrent or multi-threaded environment, where multiple threads or processes may be accessing and modifying the kv-store simultaneously.
Therefore, all operations on it are atomic and consistent.

### The dot in `ConcurrentHashMap.`
The dot is used to call the constructor of the class without arguments, creating a new instance of `ConcurrentHashMap`. It is a common way of creating Java objects from Clojure.

### Why `ConcurrentHashMap` as a data structure?
It is a class in the Java Standard Library that provides a concurrent and thread-safe implementation of the `Map` interface.
It is concurrent (obviously), have high performance, is thread-safe: you don't need additional sync mechanisms like explicit locks to ensure data integrity;
it is scalable: it uses a mechanism called segmentation to divide the map into multiple segments and threads can access them concurrently.
