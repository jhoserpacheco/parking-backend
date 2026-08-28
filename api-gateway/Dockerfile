# Etapa 1: Compilación con Gradle y JDK 17
FROM gradle:8.10-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle bootJar --no-daemon

# Etapa 2: Imagen de ejecución
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/build/libs/api-gateway-*.jar /app/app.jar
EXPOSE 8079
ENTRYPOINT ["java", "-jar", "/app/app.jar"]