# Stage 1: Build the application
FROM maven:3.8.4-openjdk-11 as BUILD_IMAGE
WORKDIR /app
COPY pom.xml .
COPY src src

RUN mvn clean package -DskipTests
# Stage 2: Create the runtime image
FROM openjdk:11-jre
WORKDIR /app
COPY --from=BUILD_IMAGE /app/target/ScotiaBank-0.0.1-SNAPSHOT.jar ScotiaBank.jar
# Expose the port that the application will run on
EXPOSE 8080
# Set the entry point to run the application
ENTRYPOINT ["java", "-jar", "ScotiaBank.jar"]
