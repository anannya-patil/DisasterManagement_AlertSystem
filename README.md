# Disaster Management and Emergency Response System

A full-stack Disaster Management System built using Spring Boot, JWT Authentication, MySQL, and Angular.  
The system manages disaster monitoring, alert broadcasting, emergency reporting, responder coordination, and response tracking through a secure, role-based architecture.

---

## Project Overview

This system supports the complete disaster management workflow:

- Disaster event monitoring and verification
- Administrative alert broadcasting
- Citizen emergency request submission
- Automatic responder assignment
- Responder acknowledgment tracking
- Response time logging
- Role-based secure access

The backend follows a clean layered architecture for maintainability and parallel development.

---

## System Architecture

```
src/main/java/com/disaster/Disaster_Management/

auth/
config/
entity/
repository/
disaster/
monitoring/
alert/
emergency/
acknowledgment/
controller/
service/
```

### Layered Design

- Entity Layer – Database models
- Repository Layer – Data access (Spring Data JPA)
- Service Layer – Business logic
- Controller Layer – REST API endpoints
- Security Layer – JWT authentication and authorization
- Frontend Layer – Angular dashboards

---

## Authentication and Security

- Stateless JWT-based authentication
- Role-based access control
- BCrypt password encryption
- Custom JWT filter
- Spring Security configuration

### User Roles

| Role      | Permissions |
|-----------|------------|
| ADMIN     | Manage disasters and broadcast alerts |
| RESPONDER | Acknowledge alerts and handle assigned requests |
| CITIZEN   | View alerts and submit emergency requests |

All protected endpoints require a valid JWT token.

---

## Disaster Monitoring Module

This module handles disaster event tracking and verification.

### Features

- Synchronization with external disaster APIs
- Automatic verification checks
- Disaster severity and magnitude tracking
- Admin approval workflow
- Filtering and retrieval of disaster events

---

## Alert Broadcasting Module

Admins can broadcast alerts to specific regions affected by disasters.

### Features

- Create alerts linked to disaster events
- Region-based alert targeting
- Alert lifecycle management
- Resolve active alerts

### Alert Status Flow

```
ACTIVE → RESOLVED
```

### Alert Fields

- id
- disasterId
- title
- message
- region
- broadcastTime
- status
- createdBy
- createdAt
- updatedAt

---

## Responder Acknowledgment Module

Responders confirm receipt of alerts and log response times.

### Features

- View active alerts by region
- Acknowledge alerts
- Prevent duplicate acknowledgments
- Calculate response time automatically

### Tracked Fields

- alertId
- responderId
- acknowledgedAt
- responseTimeSeconds

Response time is calculated from alert broadcast time to acknowledgment time.

---

## Citizen Emergency Request Module

Citizens can report emergencies with location details.

### Features

- Submit emergency request with:
  - Description
  - Latitude
  - Longitude
  - Region
- Automatic responder assignment
- Request lifecycle management

### Emergency Request Status Flow

```
PENDING → ASSIGNED → RESOLVED
```

### Routing Logic

- Responders filtered by role
- Region-based matching
- First available responder assignment (extendable to geospatial logic)

---

## Core Business Logic

- Region-based filtering of alerts
- Automatic responder assignment
- Response time computation
- Status validation before updates
- Role-restricted endpoint access
- Duplicate acknowledgment prevention

---

## Database Design

### Tables

- users
- profiles
- disaster_events
- alerts
- alert_acknowledgments
- emergency_requests

### Relationships

- One-to-One: User ↔ Profile
- One-to-Many: Disaster Event → Alerts
- One-to-Many: Alert → Acknowledgments
- One-to-Many: User → Emergency Requests

The schema is managed using Hibernate with JPA annotations.

---

## REST API Summary

### Authentication

```
POST /auth/login
```

### Admin

```
POST /admin/alerts
PUT /admin/alerts/{id}/resolve
GET /admin/disasters
```

### Responder

```
GET /responder/alerts
POST /responder/alerts/{id}/acknowledge
GET /responder/emergency-requests
```

### Citizen

```
POST /citizen/emergency-request
GET /citizen/alerts
```

---

## Technology Stack

### Backend

- Java 17
- Spring Boot 3
- Spring Security
- JWT
- Hibernate (JPA)
- MySQL
- HikariCP

### Frontend

- Angular
- TypeScript
- REST API integration

---

## Setup Instructions

### 1. Clone Repository

```
git clone <repository-url>
cd DisasterManagement_AlertSystem
```

### 2. Environment Configuration

This project uses environment variables for sensitive configuration.

Before running the backend, create a `.env` file in the project root (same level as `pom.xml`).

You can copy the template file:

```
cp .env.example .env
```

Or manually create a `.env` file with the following variables:

```
DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password
```

Update these values according to your local MySQL setup.

Important:
- The `.env` file is ignored by Git and must not be committed.
- The application will not start if these variables are missing.

### 3. Run Backend

```
.\mvnw.cmd spring-boot:run
```

Server runs at:

```
http://localhost:8080
```

### 4. Run Frontend

```
cd frontend
ng serve
```

Frontend runs at:

```
http://localhost:4200
```

---

## Milestone Coverage

The system includes:

- Disaster monitoring and approval
- Alert broadcasting
- Responder acknowledgment tracking
- Citizen emergency reporting
- Automatic responder routing
- Role-based access enforcement
- Layered backend architecture
