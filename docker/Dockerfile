# Image for compiling application
FROM maven:3.8.4-openjdk-17 AS builder

WORKDIR /project

COPY backend/pom.xml /project/
RUN mvn clean verify --fail-never

COPY backend/src /project/src
# Compile webapp
RUN mvn package -DskipTests

# Image for running application
FROM openjdk:17-jdk-slim

WORKDIR /usr/src/app/
COPY --from=builder /project/target/*.jar /usr/src/app/

# Exposed container port
EXPOSE 8443

# Command to execute when performing docker run
CMD [ "java", "-jar", "backend-0.0.1-SNAPSHOT.jar" ]