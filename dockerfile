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
# Usamos una imagen ligera de Java (sin Maven, para que pese menos).
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# TRUCO: Pasamos el puerto como argumento directo de Java
# Esto sobreescribe cualquier configuración.
CMD ["java", "-Dserver.port=${PORT}", "-jar", "app.jar"]