# Use a slim Java base image with JDK 17
FROM openjdk:17.0.2-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY build/libs/kunden-service-1.0.jar kunden-service-app.jar

# Expose the port on which the Spring Boot app runs
EXPOSE 9001

# Start the Spring Boot application
ENTRYPOINT ["java", "-jar", "kunden-service-app.jar"]