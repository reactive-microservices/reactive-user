# Reactive user microservice example using Vert.x and RxJava.


## Unit tests.

```
mvn clean test
```

## Integration tests

```
mvn clean test -P integration
```

## Run locally

Using maven:
```
mvn compile vertx:run
```
Using fat jar file:
```
java -jar target/reactive-user-1.0.0-SNAPSHOT.jar
```

Check service running:
```
curl localhost:7070
```

## Run in cluster using Infinispan in-memory data grid.

```
mvn compile vertx:run -Dvertx.runArgs="-cluster -Djava.net.preferIPv4Stack=true"
```

## Related project

https://github.com/redhat-developer/reactive-microservices-in-java
