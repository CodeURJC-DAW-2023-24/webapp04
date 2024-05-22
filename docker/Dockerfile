# Angular compilation
FROM node:18.13 AS angular
WORKDIR /code
COPY frontend/package*.json frontend/angular.json frontend/tsconfig*.json /code/
RUN npm install
COPY frontend/src /code/src
RUN npm run build -- --configuration production --base-href=/new/

# Compilation
FROM maven:3.8.4-openjdk-17 AS builder
WORKDIR /project
COPY backend/pom.xml /project/
COPY backend/src /project/src
COPY --from=angular /code/dist/frontend/browser/ /project/src/main/resources/static/new
RUN mvn clean package -DskipTests

# Execution
FROM openjdk:17-jdk-slim
WORKDIR /usr/src/app/target
COPY --from=builder /project/target/*.jar /usr/src/app/target/app.jar
EXPOSE 8443
CMD ["java", "-jar", "app.jar"]
