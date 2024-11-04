# usamos una imagen de jdk
FROM openjdk:17-jdk-slim
# argumentos
ARG JAR_FILE=target/foro-0.0.1.jar
# creamos una copia con un nombre
COPY ${JAR_FILE} app_foro.jar
# puerto para comunicarnos con el contenedor (instancia de la imagen docker)
EXPOSE 8080
# indicamos los procesos que van a ejecutarse al iniciarse el contenedor
ENTRYPOINT ["java", "-jar", "app_foro.jar"]