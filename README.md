# SecondShelf

## Second-Hand Book Marketplace

SecondShelf is a full-stack marketplace for **buying, selling, and renting second-hand books**. It is being developed with a **Java 21 + Spring Boot backend** and a **React frontend**, with emphasis on clean architecture, validation, secure APIs, database design, and real-world marketplace business rules.

## Architecture

```text
React Frontend
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

### Frontend
- React
- Vite

### Database
- PostgreSQL — primary database
- H2 — test profile

### Engineering Practices
- Layered architecture
- DTO-based API design
- Global exception handling
- Custom business exceptions
- JPA auditing with a shared BaseEntity
- Role- and status-based business rules
- Ownership checks
- AOP-based performance logging
- Environment-specific configuration with Spring Profiles
- JUnit / Spring Boot testing foundation
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
- Get all addresses for a user
- Get address by ID
- Update address
- Delete address
- Default-address business rule
- User ownership checks

### Book Management
- Create book
- Bulk book creation
- Get all books
- Get book by ID
- Update book
- Delete book
- Category validation

### Book Listing
The listing module manages seller listings with:
- Price
- Book condition
- Quantity
- Available quantity
- Description
- Listing status
- Listing type

Business rules include seller status validation, ownership checks, and listing-specific validation.

### Additional Domain Model
The project also contains domain models for:
- Cart and Cart Items
- Wishlist and Wishlist Items
- Orders and Order Items
- Payments
- Reviews

These models provide the foundation for the remaining marketplace workflows.

## Security

Spring Security has been integrated with database-backed authentication and role-based authorization.

Current security implementation includes:
- Database-backed user authentication
- Custom UserDetails implementation
- BCrypt password encoding
- Password hashing during registration
- BUYER and SELLER role-based authorization
- ACTIVE user-status validation
- Protected buyer and seller APIs
- Public registration endpoints
- 401/403 security testing

JWT authentication is **not yet claimed here**; it will be added only after implementation and verification.

## Implemented Engineering Features

- Request validation
- DTO mapping
- Global exception handling
- Custom business exceptions
- JPA auditing
- PostgreSQL configuration
- H2 test profile
- Spring Security authentication and authorization
- BCrypt password hashing
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
| Spring Security authentication & RBAC | ✅ |
| Book listing | 🔄 |
| Cart / Wishlist workflow | 🔄 |
| Order / Payment workflow | 🔄 |
| React frontend | 🔄 |
| API documentation | 🔄 |
| Deployment | ⏳ |

## Project Goal

The goal is to evolve SecondShelf into a complete, demonstrable marketplace application with secure authentication and authorization, buying and renting workflows, inventory and listing management, cart and wishlist workflows, orders and payments, reviews and ratings, a React-based user interface, automated tests, Dockerized deployment, and production-oriented documentation.

## Developer

**Piyush Raj**

Java Backend Developer · Java Full Stack Developer  
Java • Spring Boot • Spring Data JPA • Hibernate • PostgreSQL • React

GitHub: https://github.com/piyushraj04
