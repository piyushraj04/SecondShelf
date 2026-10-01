# SecondShelf

## Second-Hand Book Marketplace

SecondShelf is a full-stack marketplace for **buying, selling, and renting second-hand books**. It is being developed with a **Java 21 + Spring Boot backend** and a **React frontend**, with emphasis on clean architecture, validation, secure APIs, database design, and real-world marketplace business rules.

> **Development status:** Active development. The backend is currently the primary focus; marketplace workflows and the React frontend are being built incrementally.

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

## Implemented Backend Modules

### User Management
- User registration
- Seller registration
- Role handling
- User status validation
- Duplicate email/contact checks
- DTO-based responses
- Database-backed authentication
- BCrypt password encoding
- Role-based authorization

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

## Development Status

| Area | Status |
|---|---|
| Project structure | Complete |
| User registration | Complete |
| Address module | Complete |
| Book module | Complete |
| Validation | Complete |
| DTO mapping | Complete |
| Exception handling | Complete |
| JPA auditing | Complete |
| PostgreSQL configuration | Complete |
| H2 test profile | Complete |
| AOP performance logging | Complete |
| Spring Security authentication & RBAC | Complete |
| Book listing | In progress |
| Cart / Wishlist workflow | Planned |
| Order / Payment workflow | Planned |
| React frontend | In progress |
| API documentation | Planned |
| Automated test coverage | In progress |
| Deployment | Planned |

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
├── docs/
├── diagrams/
├── assets/
└── README.md
```

## Roadmap

1. Complete Book Listing workflow
2. Harden and refine Spring Security
3. Implement Cart and Wishlist workflows
4. Implement Order, Payment and Review workflows
5. Build the React frontend
6. Add API documentation
7. Expand automated tests
8. Dockerize and deploy the application
9. Add practical GenAI features where they provide real product value

## Developer

**Piyush Raj**

Java Backend Developer · Java Full Stack Developer

Java • Spring Boot • Spring Data JPA • Hibernate • Spring Security • PostgreSQL • React

GitHub: https://github.com/piyushraj04
