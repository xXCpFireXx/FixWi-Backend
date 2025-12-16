# FixWi-Backend

Backend service for FixWi, a ticket management system. This project is a Java Spring Boot application following a Ports & Adapters (Hexagonal) architecture, with PostgreSQL for persistence, Flyway for DB migrations, JWT-based security, and OpenAPI documentation.

## Tech stack
- Language: Java 21
- Framework: Spring Boot 3.5.8 (Web, Validation, Security, Data JPA)
- Persistence: PostgreSQL + Spring Data JPA
- Database migrations: Flyway
- Security: Spring Security + JWT (jjwt)
- API docs: springdoc-openapi (Swagger UI)
- AI integration: google-genai (used in the AI suggestion adapter)
- Build tool / package manager: Maven (with Maven Wrapper)
- Containerization: Dockerfile provided; docker compose for PostgreSQL

## Project structure
```
FixWi-Backend/
├─ pom.xml
├─ Dockerfile
├─ compose.yaml                      # PostgreSQL container for local/dev
├─ mvnw, mvnw.cmd                    # Maven Wrapper scripts
└─ src/
   ├─ main/
   │  ├─ java/com/fixwi/fixwi_backend/
   │  │  ├─ FixWiBackendApplication.java   # Application entry point
   │  │  ├─ application/                   # Use cases (application layer)
   │  │  ├─ domain/                        # Domain models, ports (hexagonal)
   │  │  └─ infrastructure/                # Adapters, configuration, security
   │  │     ├─ adapter/
   │  │     │  ├─ in/web/controller        # REST controllers
   │  │     │  ├─ in/web/dto               # Request/response DTOs
   │  │     │  └─ out/persistence/jpa      # JPA entities, mappers, adapters
   │  │     ├─ config                      # Spring and OpenAPI configs
   │  │     └─ security                    # JWT filter, token provider, users
   │  └─ resources/
   │     ├─ application.properties         # Externalized configuration
   │     └─ db/migration/                  # Flyway migrations (V1__*.sql ...)

```

## Rest Endpoints

- REST controllers (base paths):
  - `/auth` — authentication (login, register)
  - `/tickets` — ticket CRUD operations (secured)
  - `/metrics` — ticket metrics (secured for ADMIN/TI)

- [Swagger Doc Link](https://fixwi-backend.onrender.com/swagger-ui/index.html)  
  
    

## Configuration
Application properties are externalized and use environment variables. The following are referenced in `src/main/resources/application.properties`:

- Database
  - `SPRING_DATASOURCE_URL` (required)
  - `SPRING_DATASOURCE_USERNAME` (required)
  - `SPRING_DATASOURCE_PASSWORD` (required)
  - `SPRING_JPA_HIBERNATE_DDL_AUTO` (optional; default `none` if unset)
- Flyway
  - `spring.flyway.baseline-on-migrate=true`
  - `spring.flyway.baseline-version=0`
  - `spring.flyway.baseline-description=baseline`
- Security (JWT)
  - `jwt.secret` (default present in properties; you should override for non-dev)
  - `jwt.expiration` (default `86400000` ms)
- AI integration
  - `APIKEY_AI` — API key used by the AI suggestion adapter

Optionally:
```
SPRING_JPA_HIBERNATE_DDL_AUTO=none
APIKEY_AI=your_api_key_here
# It is recommended to set a different secret for non-dev usage
JWT_SECRET=your-strong-secret
JWT_EXPIRATION=86400000
```

## Prerequisites
- Java 21 (JDK)
- Maven (or use the Maven Wrapper: `./mvnw`)
- Docker (optional, for containerized DB and app builds)

## Running locally
1) Start PostgreSQL with Docker Compose (recommended for local dev):
```
docker compose up -d
```
This starts a Postgres 15 container on port 5332 with DB `fixwi`.

2) Export required environment variables (example):
```
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5332/fixwi
export SPRING_DATASOURCE_USERNAME=admin
export SPRING_DATASOURCE_PASSWORD=admin123
# optional
export SPRING_JPA_HIBERNATE_DDL_AUTO=none
export APIKEY_AI=your_api_key_here
```

3) Run the application:
```
./mvnw spring-boot:run
```
The service listens on port 8080 by default.

## Build and run
- Build JAR:
```
./mvnw clean package
```
- Run JAR:
```
java -jar target/*.jar
```

### Docker
Build and run the app container (uses multi-stage Dockerfile):
```
# Build image
docker build -t fixwi-backend:local .

# Run container (bind 8080)
docker run --rm -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5332/fixwi \
  -e SPRING_DATASOURCE_USERNAME=admin \
  -e SPRING_DATASOURCE_PASSWORD=admin123 \
  fixwi-backend:local
```
Adjust datasource host if your Docker environment does not support `host.docker.internal`.

## API documentation
Springdoc OpenAPI is enabled. When the app is running, visit:
- Swagger UI: `/swagger-ui/index.html`
- OpenAPI JSON: `/v3/api-docs`

Secured endpoints require a Bearer JWT. Obtain a token via `/auth/login`.

## Notes on architecture
- Domain-driven layering with Ports & Adapters (hexagonal):
  - `domain` contains entities and inbound/outbound ports
  - `application` implements use cases
  - `infrastructure` provides adapters such as web controllers, JPA persistence, security, and configuration
- Flyway migrations are applied on startup.



## Live deployments
- Frontend (Vercel): https://fix-wi.vercel.app/
- Backend: Hosted on Render
- Database: PostgreSQL on Railway (production)

## Team
- Camila Acosta
- Cristian Penagos
- Juan Pablo Jimenez 
- Brisbany Puerta
- Menelik Puerta
