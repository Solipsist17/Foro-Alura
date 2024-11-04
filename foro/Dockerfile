# Fase de construcción
FROM maven:3.8.8-eclipse-temurin-17 AS build
WORKDIR /app
COPY foro/pom.xml .
COPY foro/src ./src
RUN mvn clean install -DskipTests

# Fase de ejecución
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/foro-0.0.1.jar app_foro.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app_foro.jar"]
