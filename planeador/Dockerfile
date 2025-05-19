# Etapa 1: Construcción del JAR
FROM gradle:8.5-jdk17 AS builder

WORKDIR /app

# Copiamos los archivos necesarios primero (para aprovechar la cache)
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
RUN gradle build || return 0  # para cachear las dependencias sin fallar

# Ahora copiamos el código fuente
COPY . .

# Construimos el JAR
RUN gradle clean build -x test

# Etapa 2: Imagen ligera con el JAR
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copiamos el JAR desde la etapa anterior
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
