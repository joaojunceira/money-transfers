# Money Transfers API
## Requirements
1. keep it simple and to the point (e.g. no need to implement any authentication, assume the API is invoked by another  internal system/service);
1. use whatever frameworks/libraries you like (except Spring, sorry!) but don't forget about the requirement #1;
1. the datastore should run in-memory for the sake of this test;
1. the final result should be executable as a standalone program (should not require a pre-installed container/server);
1. demonstrate with tests that the API works as expected.

## [API Design](API.md)

Document with API design.

## Discussion

* This solution have clean code practices based on SOLID principles;
* Tests to all layers;
* Executable Application using Jersey with self-contained Grizzly;
* In Memory repositories;
* To make it simple the transfers are not Transactional. 
However to mitigate such problem in production scenario, it would be possible create a compensation action, if something fails.

## How to Run

The application is built with Java 8 and Apache Maven.

* Run Application

```
mvn exec:java
```

* Run Tests

```
mvn clean compile test
```