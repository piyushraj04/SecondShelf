# SecondShelf

## Second-Hand Book Marketplace

SecondShelf is a full-stack marketplace project for buying, selling, and renting second-hand books. The project is being developed as a Java 21 + Spring Boot backend with a React frontend, with a focus on clean architecture, validation, security, database design, and real-world marketplace business rules.

## Architecture

```text
Client / React Frontend
        |
        v
Spring Boot REST API
        |
        +--> Controller
        +--> Service
        +--> Repository
        |
        v
PostgreSQL

DTOs • Validation • Exception Handling • Security • JPA Auditing
```

## Technology Stack

### Backend
- Java 21
- Spring Boot 4.1.0
- Spring MVC / REST APIs
- Spring Data JPA
- Hibernate
- Spring Security
- Bean Validation
- Lombok
- Maven

### Database
- PostgreSQL — primary database
- H2 — test profile / local testing

### Engineering Practices
- Layered architecture
- DTO-based API design
- Global exception handling
- JPA auditing with a shared BaseEntity
- Role and status based business rules
- AOP-based performance logging
- Environment-specific configuration with Spring Profiles
- JUnit / Spring Boot test foundation
- Git and GitHub

## Backend Modules

### User Management
- User registration
- Seller registration
- Role handling
- User status validation
- Duplicate email/contact checks
- DTO-based responses

### Address Management
- Add address
- Get all user addresses
- Get address by ID
- Update address
- Delete address
- Default-address business rule with user ownership checks

### Book Management
- Create book
- Bulk book creation
- Get all books
- Get book by ID
- Update book
- Delete book
- Category validation

### Book Listing
The marketplace listing module handles seller and book listing information such as price, condition, quantity, available quantity, description, listing status, and listing type.

The implementation also includes seller status checks, ownership checks, and marketplace-specific validation rules.

### Additional Domain Model
The backend already contains domain entities for:
- Cart and Cart Items
- Wishlist and Wishlist Items
- Orders and Order Items
- Payments
- Reviews

These modules form the foundation for the remaining marketplace workflow.

## Security

The backend includes Spring Security configuration and application-level user details support. Authentication and authorization are being developed as part of the application's security layer, with role-aware access and protected operations.

## Implemented Engineering Features

- Request validation
- DTO mapping
- Global exception handling
- Custom business exceptions
- JPA auditing
- PostgreSQL configuration
- H2 test profile
- Spring Security foundation
- AOP performance logging
- Seller/user status checks
- Ownership checks
- Layered service architecture

## Project Structure

```text
SecondShelf/
├── backend/
│   └── secondshelf-api/
│       ├── src/main/java/com/secondshelf/
│       │   ├── aspect/
│       │   ├── controller/
│       │   ├── dto/
│       │   ├── entity/
│       │   ├── enums/
│       │   ├── exception/
│       │   ├── repository/
│       │   ├── security/
│       │   └── service/
│       ├── src/main/resources/
│       └── pom.xml
│
├── frontend/
│   └── secondshelf-web/
│
├── docs/
├── diagrams/
├── assets/
└── README.md
```

## Development Status

| Area | Status |
|---|---|
| Project structure | ✅ |
| User registration | ✅ |
| Address module | ✅ |
| Book module | ✅ |
| Validation | ✅ |
| DTO mapping | ✅ |
| Exception handling | ✅ |
| JPA auditing | ✅ |
| PostgreSQL configuration | ✅ |
| H2 test profile | ✅ |
| AOP performance logging | ✅ |
| Book listing | 🔄 |
| Security hardening | 🔄 |
| Cart / Wishlist workflow | 🔄 |
| Order / Payment workflow | 🔄 |
| React frontend | 🔄 |
| API documentation | 🔄 |
| Deployment | ⏳ |

## Project Goal

The goal is to evolve SecondShelf from a backend-heavy project implementation into a complete, demonstrable marketplace application with secure authentication and authorization, buying and renting workflows, inventory and listing management, cart and wishlist workflows, orders and payments, reviews and ratings, a React-based user interface, automated tests, Dockerized deployment, and production-oriented documentation.

## Developer

**Piyush Raj**

Java Full Stack Developer
Java • Spring Boot • Spring Data JPA • Hibernate • PostgreSQL • React

GitHub: https://github.com/piyushraj04