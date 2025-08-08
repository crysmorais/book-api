# Etapa 1: build com Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copia tudo e faz o build
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: imagem final apenas com o .jar
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/book-api-1.0.0.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]