# java-assessment
This project was created as part of recruiting process to Java developer position. Project was built using Spring Boot for new services and consuming existing data from /users and /teams existing endpoints that were provided.

## Items on assessment description
#### Create a new role:
- available at /role using POST method:
- fields on body request (JSON): 
  - 'name' - for role name
#### Assign a role to a team member
- available at /assignment using POST method:
- fields on body request (JSON): 
  - 'userId' - identifier from user
  - 'roleId' - identifier from role
#### Look up a role for a membership
- search available users by role to start a membership:
- available at /users/role using GET method
- can provide and additional request parameter 'roleId' to get just users related to that role.
  - for instance /users/role?roleId=1
#### Create membership
- available at /membership using POST method:
- fields on body request (JSON): 
  - 'userId' - identifier from user
  - 'teamId' - identifier from team
#### Look up memberships for a role
- search all existing memberships related to a role:
- available at /memberships using GET method
- can provide and additional request parameter 'roleId' to get just memberships that have users with that specific role.
  - for instance /memberships?roleId=1
 
### Question: What happens if the data you are using gets deleted?
Before assigning new roles or creating membership relations I'm checking that data is valid on /users /teams endpoint. 
However, if a data is deleted after this process is done no action is taken on this system side. 
I think this is something to be implemented on existing application side (like a queue that my system can subscribe). 
Other option is create a nightly routine to check and cleanup existing assignments and memberships.

## Docker
Create docker image:

<code>$ docker build -t assessment .</code>

Run image to up application:

<code>$ docker run -p 8080:8080 assessment</code>
then access application on localhost:8080/

## Tests
You can run tests on command line:
<code>$ mvn clean test</code>

## Enhancements / known issues
- Create 'profile' for test execution and configuration
- while assigning role to user: add 'Developer' as default if no roleId provided
