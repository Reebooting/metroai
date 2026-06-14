# metroai
# MetroAI Backend

## Project Goal

MetroAI is a Spring Boot backend application for a metro management and route planning system.

The long-term goal is to support:

* User Authentication
* Metro Lines
* Stations
* Route Planning
* Journey Search
* Fare Calculation
* Ticketing
* AI-powered travel assistance

---

# Tech Stack

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* PostgreSQL
* JWT Authentication
* Lombok
* Maven

---

# Current Architecture

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

### Controller

Handles HTTP requests and responses.

Examples:

* AuthController
* StationController
* LineController

### Service

Contains business logic.

Examples:

* Register user
* Login user
* Validate station uniqueness
* Validate line existence

### Repository

Handles database access using Spring Data JPA.

Examples:

* UserRepository
* StationRepository
* LineRepository

### Entity

Represents database tables.

Examples:

* User
* Station
* Line

### DTO

Used for API requests and responses.

Examples:

* RegisterRequest
* LoginRequest
* CreateStationRequest
* StationResponse

---

# Authentication Flow

User Registration:

```text
Request
    ↓
AuthController
    ↓
AuthService
    ↓
PasswordEncoder (BCrypt)
    ↓
UserRepository
    ↓
PostgreSQL
```

User Login:

```text
Request
    ↓
AuthController
    ↓
AuthService
    ↓
Verify Password
    ↓
Generate JWT
    ↓
Return Token
```

Protected Endpoint:

```text
JWT Token
    ↓
JwtFilter
    ↓
Validate Token
    ↓
Allow Request
```

---

# Station Module

Implemented CRUD operations.

Features:

* Create Station
* Get All Stations
* Get Station By Id
* Search Station
* Update Station
* Delete Station

Business Rules:

* Station code must be unique
* Station must belong to a Line
* Station responses use DTOs

---

# Line Module

Purpose:

Store metro line information.

Fields:

* id
* code
* name
* color
* createdAt

Examples:

* BL → Blue Line
* YL → Yellow Line
* RD → Red Line

Business Rules:

* Line code must be unique

---

# Database Relationship

Relationship implemented:

```text
One Line
    ↓
Many Stations
```

JPA Mapping:

```java
Line
    @OneToMany

Station
    @ManyToOne
```

Database:

```text
lines
-----
id
code
name
color

stations
--------
id
name
code
line_id
```

Station references Line using:

```java
@ManyToOne
@JoinColumn(name = "line_id")
private Line line;
```

---

# Exception Handling

Custom Exceptions:

* ResourceNotFoundException
* DuplicateResourceException

Global Handler:

* GlobalExceptionHandler

Benefits:

* Consistent API responses
* Proper HTTP status codes
* Cleaner controllers

---

# Spring Concepts Learned

* Dependency Injection
* Constructor Injection
* Lombok
* DTO Pattern
* JPA Repositories
* Derived Query Methods
* Validation
* BCrypt Password Hashing
* JWT Authentication
* Global Exception Handling
* One-To-Many Relationships
* Many-To-One Relationships

---

# API Endpoints

Authentication:

* POST /auth/register
* POST /auth/login
* GET /auth/profile

Lines:

* POST /api/lines

Stations:

* POST /api/stations
* GET /api/stations
* GET /api/stations/{id}
* GET /api/stations/search
* PUT /api/stations/{id}
* DELETE /api/stations/{id}

---

# Future Roadmap

* Complete Line CRUD
* Route Planning
* Station Ordering
* Journey Search
* Fare Calculation
* Ticket Booking
* Admin Dashboard
* AI Travel Assistant

```
```
