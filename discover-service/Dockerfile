# Etapa 1: Compilación con Gradle y JDK 17
FROM gradle:8.10-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle bootJar --no-daemon

# Etapa 2: Imagen de ejecución con Eclipse Temurin JRE 17
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/build/libs/discover-service-*.jar /app/app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "/app/app.jar"]