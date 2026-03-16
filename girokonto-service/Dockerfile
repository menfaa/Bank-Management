# Use a slim Java base image with JDK 17
FROM openjdk:17.0.2-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY build/libs/girokonto-service-0.0.1-SNAPSHOT.jar girokonto-service-app.jar

# Expose the port on which the Spring Boot app runs
EXPOSE 9000

# Start the Spring Boot application
ENTRYPOINT ["java", "-jar", "girokonto-service-app.jar"]