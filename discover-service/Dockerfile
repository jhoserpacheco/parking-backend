# Usa una imagen de OpenJDK para compilar y ejecutar la aplicación
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /discover-service

# Copia el archivo JAR generado por Spring Boot (esto requiere que previamente hayas hecho un build con Maven o Gradle)
COPY build/libs/discover-service-0.0.1-SNAPSHOT.jar /discover-service/app.jar

# Exponer el puerto en el que tu aplicación se ejecuta (8080 por defecto)
EXPOSE 8761

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/discover-service/app.jar"]