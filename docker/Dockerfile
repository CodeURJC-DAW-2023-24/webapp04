# Compilation
FROM maven:3.8.4-openjdk-17 AS builder
WORKDIR /project
COPY backend/pom.xml /project/
COPY backend/src /project/src
RUN mvn clean package -DskipTests

# Execution
FROM openjdk:17-jdk-slim
WORKDIR /usr/src/app/target
COPY --from=builder /project/target/*.jar /usr/src/app/target/app.jar
CMD ["java", "-jar", "app.jar"]
