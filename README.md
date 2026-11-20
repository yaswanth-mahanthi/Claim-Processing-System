# Claim Processing System

## Overview

Claim Processing System is a monolithic backend application for insurance claim processing. It uses a layered architecture:

`Controller -> Service -> Repository -> Entity`

DTOs shape API requests and responses, while filters handle request authentication. The application uses an H2 in-memory database for zero-setup local execution.

## Key Architectural Highlights

- **Layered REST API Architecture:** Clean separation across Controller, Service, Repository, DTO, Filter, and Entity layers, with centralized exception handling and request validation.
- **Redis Caching:** `ClaimService` uses Redis for fast claim-status retrieval and updates the cached status when claim state changes.
- **Event-Driven Notifications (Apache Kafka):** A Kafka producer-consumer pipeline publishes claim status change events and processes them to trigger automated email notifications.
- **Persistence (Hibernate / Spring Data JPA):** Relational entity mappings use JPA annotations, with H2 in-memory database support for zero-setup local execution.
- **Security & Interactive API Docs:** Stateless JWT authentication (demo account: `demo` / `Demo123!`) and interactive OpenAPI 3 / Swagger UI.

## Quick Start & Local Execution

Requirements: Java 17. From the project root, run:

```bash
./mvnw spring-boot:run
```

On Windows, run `mvnw.cmd spring-boot:run`.

- Application: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- H2 console: `http://localhost:8080/h2-console`

The application seeds a demo account:

| Username | Password |
|---|---|
| `demo` | `Demo123!` |

Authenticate with `POST /users/login`. The JWT is returned in the `Authorization` response header; use it as a bearer token in Swagger UI's **Authorize** dialog.

### Optional Kafka and Redis services

`spring.kafka.listener.auto-startup=false` is set by default, so the app starts without a Kafka broker. To test the full notification pipeline, start a local or Docker Kafka broker accessible at `localhost:9092`, then enable the listener:

```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments=--spring.kafka.listener.auto-startup=true
```

The producer and consumer are configured for `localhost:9092`. For Docker, expose the broker's listener on host port `9092` so that address is reachable from the application.

Redis is optional for local startup. To test distributed caching, start a local or Docker Redis service with port `6379` exposed on `localhost:6379`; the application is configured for that address.

Email delivery by the Kafka consumer requires SMTP credentials. Supply `MAIL_USERNAME` and `MAIL_PASSWORD` as environment variables before enabling Kafka; no mail password is stored in the repository.

## REST API Endpoints

All endpoints require JWT authentication except user registration/login and the Swagger/OpenAPI documentation routes. The claim status route includes the `/claims` controller prefix and its `/claims/{claimId}/status` method path.

| HTTP Method | Path | Auth Required | Description |
|---|---|---|---|
| POST | `/users/register` | No | Register a user |
| POST | `/users/login` | No | Authenticate and receive a JWT |
| GET | `/users/{userId}` | Yes | Retrieve a user |
| PUT | `/users/{userId}` | Yes | Update a user |
| DELETE | `/users/{userId}` | Yes | Delete a user |
| POST | `/claims` | Yes | Submit a claim |
| GET | `/claims/{claimId}` | Yes | Retrieve a claim |
| PUT | `/claims/{claimId}` | Yes | Update a claim |
| DELETE | `/claims/{claimId}` | Yes | Delete a claim |
| GET | `/claims/user/{userId}` | Yes | List claims for a user |
| GET | `/claims/claims/{claimId}/status` | Yes | Retrieve a claim status from Redis |
| GET | `/reports/claims/status` | Yes | Generate a report grouped by claim status |
| GET | `/reports/claims/summary` | Yes | Retrieve a claims summary |
| POST | `/notifications/subscribe` | Yes | Subscribe to notifications |
| POST | `/notifications/unsubscribe` | Yes | Unsubscribe from notifications |

## Build

```bash
./mvnw clean compile
```
