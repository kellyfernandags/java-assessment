#
# Build stage
#
FROM maven:3.6.0-jdk-8-alpine AS build
COPY src /src
COPY target /target
COPY pom.xml .
RUN mvn -f pom.xml clean package

#
# Package stage
#
FROM openjdk:8-jre-alpine
RUN addgroup -S spring && adduser -S spring	-G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY --from=build ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]