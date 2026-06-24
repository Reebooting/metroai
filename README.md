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
# MetroAI Project Progress (June 2026)

## Project Overview

MetroAI is a Delhi Metro-inspired intelligent transit system built using Spring Boot, PostgreSQL, Redis, Docker, JWT Authentication, Graph-Based Route Search, and AI integrations planned for future phases.

---

## Tech Stack

### Backend

* Java 21
* Spring Boot 3
* Spring Data JPA
* Spring Security
* JWT Authentication
* PostgreSQL
* Redis Cache
* Docker
* Maven

### Future

* React + TypeScript Frontend
* AWS Deployment
* AI Route Assistant
* Kafka
* Kubernetes

---

## Completed Features

### Authentication

* User Registration
* User Login
* BCrypt Password Encryption
* JWT Token Generation
* Role Enum (USER, ADMIN)
* Protected APIs

### Metro Network

* Create Metro Lines
* Create Stations
* Assign Stations To Lines
* LineStation Mapping
* Interchange Support

### Route Engine

* Graph-Based BFS Route Search
* Source To Destination Routing
* Route Instructions
* Route Segments
* Distance Calculation
* Estimated Travel Time
* Interchange Detection
* Fare Calculation

### Ticketing

* Ticket Booking
* Ticket Entity
* Ticket Status
* Fare Storage
* QR Code Generation
* Ticket Retrieval API

### Performance

* Docker Installed
* Ubuntu WSL2 Installed
* Redis Installed
* Redis Route Caching
* Cached Route Responses
* Reduced BFS Recalculation

---

## Infrastructure Completed

### Docker

* Docker Desktop Installed
* Ubuntu WSL2 Configured
* Redis Container Running

### Redis

* Spring Cache Enabled
* Route Search Cached
* Redis Connected Successfully

---

## APIs Completed

### Auth

POST /api/auth/register
POST /api/auth/login

### Lines

POST /api/lines
GET /api/lines

### Stations

POST /api/stations
GET /api/stations
GET /api/stations/{id}

### Line Stations

POST /api/line-stations

### Routes

GET /api/routes

### Tickets

POST /api/tickets
GET /api/tickets/{id}

---

## Pending Backend Features

### Security

* Admin Authorization
* Role Based Access Control

### Journey Management

* Journey History
* User Journey Tracking

### Ticket Validation

* QR Scan Validation
* Ticket Usage Tracking

### Notifications

* Kafka Integration

---

## Planned AI Features

### AI Route Assistant

User can ask:
"How do I reach Rajiv Chowk before 8 PM?"

AI generates:

* Best Route
* ETA
* Interchanges
* Fare

### Natural Language Search

Example:
"Rajiv Chowk to Kashmere Gate"

Converted automatically to route search.

### Delay Prediction

AI predicts route delays using historical data.

### Metro Chatbot

Answers:

* Fare
* Timings
* Routes
* Closest Station

---

## Planned Frontend

React + TypeScript

Pages:

* Home
* Search Route
* Metro Map
* Book Ticket
* My Tickets
* Journey History
* Admin Dashboard
* AI Assistant

---

## Planned AWS Deployment

### EC2

Spring Boot Hosting

### RDS

Managed PostgreSQL

### ElastiCache

Managed Redis

### S3

QR Codes
Reports
Metro Maps

### CloudWatch

Monitoring

### Bedrock / OpenAI

AI Features

---

## Current Status

Project Completion:
~70% Backend Complete

Next Task:
Admin Authorization & Role Based Access Control
