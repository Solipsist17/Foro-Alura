# Fase de construcción
FROM maven:3.8.7-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests

# Fase de ejecución
FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/foro-0.0.1.jar
COPY --from=build /app/${JAR_FILE} app_foro.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app_foro.jar"]
