# Usa una imagen de OpenJDK para compilar y ejecutar la aplicación
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /mail-ms

# Copia el archivo JAR generado por Spring Boot (esto requiere que previamente hayas hecho un build con Maven o Gradle)
COPY build/libs/mail-0.0.1-SNAPSHOT.jar /mail-ms/app.jar

# Exponer el puerto en el que tu aplicación se ejecuta (8080 por defecto)
EXPOSE 8082

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/mail-ms/app.jar"]