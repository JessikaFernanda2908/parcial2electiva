# Etapa 1: Build con Maven
FROM maven:3.9.5-eclipse-temurin-17 AS builder

WORKDIR /app

# Copiamos los archivos del proyecto
COPY pom.xml .
COPY src ./src

# Construimos el proyecto sin correr los tests
RUN mvn clean package -DskipTests

# Etapa 2: Imagen liviana con JRE
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copiamos el .jar construido en la etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Copiamos el script wait-for-it.sh
COPY wait-for-it.sh .

# Exponemos el puerto de la aplicación
EXPOSE 8080

# Creamos un usuario no root
RUN chmod +x wait-for-it.sh && adduser -D springuser
USER springuser

# Usamos el script para esperar a MariaDB antes de ejecutar el jar
ENTRYPOINT ["java", "-jar", "app.jar"]
