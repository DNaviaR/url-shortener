# 🔗 URL Shortener API

Un servicio de acortamiento de URLs (estilo Bit.ly) robusto y escalable. Construido con **Java Spring Boot** y **PostgreSQL**, incluye generación de códigos únicos, redirección automática (HTTP 302) y sistema de analítica de clics. Todo el entorno está contenerizado con **Docker**.

## 🚀 Tecnologías

* **Core:** Java 17, Spring Boot 3
* **Base de Datos:** PostgreSQL 15 (Docker)
* **ORM:** Spring Data JPA
* **Contenerización:** Docker & Docker Compose
* **Utilidades:** Lombok

## ⚙️ Funcionalidades

1.  **Acortar URL:** Recibe una URL larga y genera un código alfanumérico único de 6 caracteres.
2.  **Redirección Rápida:** Al acceder al código corto, el servidor redirige automáticamente al destino original (Status 302).
3.  **Analítica:** Contador de visitas persistente. Cada redirección suma +1 a las estadísticas del enlace.
4.  **Persistencia:** Los datos sobreviven a reinicios gracias a PostgreSQL.

## 🛠️ Instalación y Uso

### Requisitos
* Docker Desktop instalado.

### Ejecución con Docker

1.  **Clonar repositorio:**
    ```bash
    git clone [https://github.com/DNaviaR/url-shortener](https://github.com/DNaviaR/url-shortener)
    cd url-shortener
    ```

2.  **Construir el proyecto:**
    ```bash
    ./mvnw clean package -DskipTests
    ```

3.  **Levantar servicios:**
    ```bash
    docker-compose up --build
    ```
    * La API estará disponible en: `http://localhost:8080`

---

## 🔌 Endpoints

| Método | Endpoint | Body (JSON) | Descripción |
| :--- | :--- | :--- | :--- |
| **POST** | `/shorten` | `{"url": "https://google.com"}` | Crea un enlace corto. Devuelve la URL acortada. |
| **GET** | `/{code}` | N/A | **Redirecciona** a la web original y suma 1 visita. |
| **GET** | `/stats/{code}` | N/A | Devuelve info del enlace y el contador de **clicks**. |

### 🧪 Ejemplo de uso

**1. Acortar un enlace:**
```bash
POST http://localhost:8080/shorten
{
  "url": "[https://www.youtube.com/watch?v=dQw4w9WgXcQ](https://www.youtube.com/watch?v=dQw4w9WgXcQ)"
}
# Respuesta: http://localhost:8080/aX9j21
```

**2. Ver estadísticas:**
```bash
GET http://localhost:8080/stats/aX9j21
# Respuesta:
{
  "id": 1,
  "shortCode": "aX9j21",
  "longUrl": "[https://www.youtube.com/](https://www.youtube.com/)...",
  "clicks": 5,
  "createdDate": "2026-01-27T10:00:00"
}



