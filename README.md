## Weekly Achievements core service

Some information about this project

##### This service is subscribed to other microservices via a Eureka discovery service

The discovery service is at:
* http://localhost:8273/


### This service is self discoverable via HAL-browser at:

Preferred way of accessing is via the running Gateway via:

* http://localhost:8090/wacs

But you can also access this service directly via:

* http://localhost:8091/


#### H2 Database

The database console is only directly accessible via:

* http://localhost:8091/console


#### Run the application from the terminal:

You can run the application from the terminal with the following command:

`$ ./gradlew bootRun`


#### Build an executable JAR and run it from the terminal:

You can build a single executable JAR file that contains all the necessary dependencies, classes, and resources, and run that. This makes it easy to ship, version, and deploy the service as an application throughout the development lifecycle, across different environments, and so forth.
You can build the JAR file using: 

`$ ./gradlew bootJar`

Then you can run the JAR file:

`$ java -jar build/libs/wa-core-service-${VERSION}.jar`


#### Interesting links:

* https://spring.io/guides/gs/rest-hateoas/
* https://spring.io/guides/gs/accessing-data-rest/
* https://docs.spring.io/spring-data/rest/docs/current/reference/html/#projections-excerpts
* https://martinfowler.com/articles/richardsonMaturityModel.html
* http://blog.ploeh.dk/2013/05/01/rest-lesson-learned-avoid-hackable-urls/
* https://stackoverflow.com/questions/24389733/document-hal-links-from-spring-hateoas-with-swagger
* http://haltalk.herokuapp.com/explorer/browser.html#/
* https://en.wikipedia.org/wiki/OAuth
* https://en.wikipedia.org/wiki/OpenID
