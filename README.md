# TeleEat 🍽️

**TeleEat** is a lightweight food reservation system that integrates with **Telegram** via a bot. Users can browse a food menu and place orders directly from Telegram, while administrators manage foods, users, and orders through a REST-based admin API.

The project is intentionally designed as a **clean**, **well-structured monolithic application**, with package separation that prepares it for future modularization if required.

---

## 📌 Features

- **Telegram bot** for food browsing and ordering

- REST API–based admin panel

- **CRUD** operations for foods, users, and orders

- Default supervisor admin created at startup

  - Credentials are logged on application startup

  - Supervisor password changes on every restart

- **Clean Architecture** with clear separation of concerns

- Domain models implemented using **Builder and Factory** patterns

- Fully **Dockerized** deployment

---

## 🧰 Technologies

- **Java 21**

- **Spring Boot**

- **MySQL**

- **JWT for authentication**

- **BcryptPasswordEncoder** for password hashing

- **Docker & Docker Compose**

- **Telegram Bot API (Polling)**

- **Clean Architecture (Application / Domain / Infrastructure / Presentation)**

---

## 🏗️ Architecture Overview

TeleEat follows **Clean Architecture principles** and is organized by packages and responsibilities, not full multi-module separation.

---

### Layers

- **Application** – Services, use cases, orchestration logic

- **Domain** – Business models and logic (pure Java, Builder & Factory patterns)

- **Infrastructure** – Database access and external integrations

- **Presentation** – REST APIs and DTOs

- **Common** – Exception handling, configurations, cross-cutting concerns

---

### Project Structure

```md
TeleEat/
├─ src/main/java/
│  ├─ application/      # Services and use cases
│  ├─ domain/           # Business models (Builder & Factory patterns)
│  ├─ infrastructure/   # Database & external integrations
│  ├─ presentation/     # REST APIs and DTOs
│  └─ common/           # Exception handling, config, etc.
├─ docker/
│  └─ Dockerfile        # Multi-stage Dockerfile
├─ docker-compose.yml
└─ README.md
```

---

### Monolith vs Microservices

This project is intentionally built as a monolith:

- The scope does not justify microservice complexity

- Faster development and easier debugging

- Package separation makes future modularization or service extraction easier if needed

---

## 🚀 Running the Project with Docker Compose

### Prerequisites

- Docker

- Docker Compose

### Steps

**1. Clone the repository**


```bash
git clone https://github.com/your-username/TeleEat.git
cd TeleEat
```
**2. Set API TOKEN**

1.To work with user side an telegram chat bor , you need to create an telegram bor and insert the AUTH TOKEN in the application under
`org/radon/teleeat/integration/telegram/application/service/TelegramPollingService`

```java
private final String token = "YOUR-API-TOKEN-HERE";
```

**3. Build and start the application**

```bash
docker-compose up --build
```

**4. Startup behavior**

- The application waits for MySQL health check

- A default supervisor admin is created

- Supervisor credentials are printed in the application logs

- The password changes on every startup

**5. Stop the application**

```bash
docker-compose down
```

---

## 🐳 Docker Notes

- Multi-stage Dockerfile (build + runtime separation)

- Maven + Eclipse Temurin used for build/runtime

- `.dockerignore` included to reduce image size

- Docker Compose includes:

  - Application service

  - MySQL service

  - Shared network and persistent DB volume

---

## 🔐 Security

- JWT-based authentication

- Passwords hashed using Bcrypt

- Role-based admin authorization (future improvement: authority-based permissions)

- Telegram users currently unauthenticated (planned improvement)

---

## ⚠️ Known Limitations

- No unit or integration tests (time constraints)

- No caching layer (Redis planned)

---

## 🛣️ Roadmap / Improvements

- Add unit and integration tests

- Introduce Redis caching

- Add cart system for orders

- Persist user addresses

- Improve admin authorization with fine-grained authorities

- Add rate-limiting and abuse protection

---

## 📄 Documentation

- A full technical project documentation is available, including:

- Architecture overview

- Database design (ERD)

- Telegram flow

- Deployment details

(See `/docs` or project documentation file if included.)

---

## 🤝 Contributing

This project was built as a learning and demonstration project.
Feedback, suggestions, and improvements are welcome.

---

## 🧠 Final Note

TeleEat was built with a strong focus on **code clarity**, **clean design**, and **maintainability**. While there is room for improvement, the project provides a solid and extensible foundation.

Thank you for reviewing and working with this project.
