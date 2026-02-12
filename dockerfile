# --- Etapa 1: Compilar el código (Builder) ---
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
# Compilamos el proyecto y creamos el .jar (saltando los tests para ir rápido)
RUN mvn clean package -DskipTests

# --- Etapa 2: Ejecutar la App (Runner) ---
FROM openjdk:17-jdk-slim
WORKDIR /app
# Copiamos solo el archivo .jar cocinado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Configuración de puerto (Render nos pasará la variable PORT)
ENV SERVER_PORT=${PORT}

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]