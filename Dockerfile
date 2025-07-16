# Use slim Java 21 base image
FROM openjdk:21-jdk-slim

# Label the maintainer
LABEL maintainer="Ram Sharma"

# Set working directory inside container
WORKDIR /app

# Copy the built jar from your local `target/` folder and rename it to journalapp.jar
COPY target/journalapp-0.0.1-SNAPSHOT.jar journalapp.jar

# Expose Spring Boot default port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "journalapp.jar"]
