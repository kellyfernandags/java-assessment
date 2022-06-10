# java-assessment
This project was created as part of recruiting process to Java developer position. Project was built using Spring Boot for new services and consuming existing data from /users and /teams existing endpoints that were provided.

### Question: What happens if the data you are using gets deleted?
Before assigning new roles or creating membership relations I'm checking that data is valid on /users /teams endpoint.
However, if a data is deleted after this process is done no action is taken on this system side.
I think this is something to be implemented on existing application side like a queue that my system can subscribe, so we can take action to delete on this system side.

## Docker
Create docker image:

<code>$ docker build -t assessment .</code>

Run image to up application:

<code>$ docker run -p 8080:8080 assessment</code>
then access application on localhost:8080/

## Documentation
Information about API can be found on Swagger page that was added to project:
- http://localhost:8080/swagger-ui.html#

## Tests
You can run tests on command line:

<code>$ mvn clean test</code>

## Next release
- Create 'profile' for test execution and configuration
- usage of pagination/order on API endpoints
