# Etapa 1: Build con Maven
FROM maven:3.9.5-eclipse-temurin-17 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Etapa 2: Imagen liviana con JRE
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copiamos solo el .jar desde el build
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

# Mejor práctica: usuario no root
RUN adduser -D springuser
USER springuser

ENTRYPOINT ["java", "-jar", "app.jar"]
