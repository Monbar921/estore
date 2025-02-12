# Use a base image with JDK
FROM maven:3.8.4-openjdk-11-slim AS build

# Set working directory inside container
WORKDIR /app

# Copy the pom.xml (Maven build file) and the rest of the source code
COPY pom.xml /app
COPY src /app/src

# Build the Spring Boot JAR file inside the container
RUN mvn clean package -DskipTests

# Use a smaller base image for running the app
FROM openjdk:11-jre-slim

# Set working directory inside container
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/estore-microservice-1.0.0.jar /app/estore-microservice-1.0.0.jar

# Expose the port Spring Boot will run on (default is 8080)
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "estore-microservice-1.0.0.jar"]
