FROM maven:3.9.6-eclipse-temurin-17 AS build

#Set the working directory
WORKDIR /Bookstore-App

#Copy the pom.xml and install the dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

#Copy the source code and build the application
COPY src ./src
RUN mvn clean package -DskipTests

#Use an official JDK image to run the application
FROM openjdk:17-jdk-slim

#Set the working directory
WORKDIR /Bookstore-App

#Copy the built JAR file
COPY --from=build /Bookstore-App/target/Bookstore-Application-0.0.1-SNAPSHOT.jar .

#Expose port
EXPOSE 8080

#Specify the command to run the application
ENTRYPOINT ["java", "-jar", "Bookstore-Application-0.0.1-SNAPSHOT.jar"]

