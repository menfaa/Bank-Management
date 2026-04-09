# Use a slim Java base image with JDK 17
FROM openjdk:17.0.2-jdk
WORKDIR /app
COPY build/libs/admin-server-0.0.1-SNAPSHOT.jar admin-server.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "admin-server.jar"]