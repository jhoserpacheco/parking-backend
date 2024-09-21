# Usa una imagen de OpenJDK para compilar y ejecutar la aplicación
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /api-gateway

# Copia el archivo JAR generado por Spring Boot (esto requiere que previamente hayas hecho un build con Maven o Gradle)
COPY build/libs/api-gateway-0.0.1-SNAPSHOT.jar /api-gateway/app.jar

# Exponer el puerto en el que tu aplicación se ejecuta (8079 por defecto)
EXPOSE 8079

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/api-gateway/app.jar"]