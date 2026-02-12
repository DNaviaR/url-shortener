# --- ETAPA 1: Construcción (BUILD) ---
# Usamos una imagen que TIENE Maven instalado
FROM maven:3.8.5-openjdk-17 AS build

# Creamos la carpeta de trabajo
WORKDIR /app

# Copiamos todo tu código (src, pom.xml, etc.) dentro del contenedor
COPY . .

# ¡LA MAGIA! Ejecutamos Maven DENTRO del contenedor
# Esto creará la carpeta /app/target y el archivo .jar
RUN mvn clean package -DskipTests

# --- ETAPA 2: Ejecución (RUN) ---
# Usamos una imagen ligera de Java (sin Maven, para que pese menos)
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copiamos SOLO el archivo .jar desde la Etapa 1 (build)
# Fíjate en el "--from=build"
COPY --from=build /app/target/*.jar app.jar

# Le decimos qué puerto vamos a usar (informativo)
EXPOSE 8080

# Arrancamos la app
ENTRYPOINT ["java", "-jar", "app.jar"]