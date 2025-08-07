FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/book-api-1.0.0.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]