# Event Management

Event Management is a Spring Boot application for managing events such as concerts and theater performances. The app supports creating, updating, and listing events via REST APIs.

## Features

- Event and Performer management  
- JWT-based authentication  
- Database versioning using Flyway  
- Input validation using Bean Validation  
- Unit tests written for service layer only  

## Technologies and Dependencies

| Technology          | Version         |
|---------------------|-----------------|
| Java                | 17              |
| Spring Boot         | 3.4.5           |
| Spring Data JPA     | 3.x             |
| Spring Security     | 3.x             |
| Flyway              | 10.x            |
| MySQL Connector/J   | 8.x             |
| JJWT (JWT Library)  | 0.11.5          |
| Lombok              | Provided scope  |

## Database Schema

The database schema is managed using Flyway migrations. The initial migration file: `V1__init.sql`

### `events` Table

| Field               | Type            | Description                             |
|---------------------|-----------------|-----------------------------------------|
| id                  | BIGINT          | Auto-incremented ID                     |
| name                | VARCHAR         | Name of the event                       |
| summary             | TEXT            | Short summary                           |
| description         | TEXT            | Detailed description                    |
| event_type          | VARCHAR         | Enum: `CONCERT` or `THEATER`            |
| start_date_time     | TIMESTAMP       | Start date and time                     |
| end_date_time       | TIMESTAMP       | End date and time                       |
| created_by_user     | INT             | ID of the user who created the event    |
| created_at          | TIMESTAMP       | Timestamp when the event was created    |
| updated_by_user     | INT (nullable)  | ID of the user who updated the event    |
| updated_at          | TIMESTAMP (nullable) | Last update timestamp               |

### `performers` Table

| Field     | Type     | Description                                  |
|-----------|----------|----------------------------------------------|
| id        | BIGINT   | Auto-incremented ID                          |
| name      | VARCHAR  | Name of the performer                        |
| role      | VARCHAR  | Role in the event                            |
| biography | TEXT     | Short biography                              |

### `event_performer` Table

This table defines the many-to-many relationship between events and performers.

| Field         | Type   | Description                                 |
|---------------|--------|---------------------------------------------|
| event_id      | BIGINT | Foreign key referencing `events(id)`        |
| performer_id  | BIGINT | Foreign key referencing `performers(id)`    |

**Primary Key:** `(event_id, performer_id)`

## How to Run

```bash
# Build the project with Maven
./mvnw clean install

# Run the Spring Boot application
./mvnw spring-boot:run
```

## Example API Usage

### 1. Authenticate and Get JWT Token

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "userId=1"
  ```
  
### 2. Create a New Event

```bash
curl -X POST http://localhost:8080/events \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -d '{
    "name": "Spring Music Night",
    "summary": "An evening of acoustic and vocal performances",
    "description": "Live performances from x and y.",
    "eventType": "CONCERT",
    "startDateTime": "2025-05-04 20:00",
    "endDateTime": "2025-05-04 23:00",
    "performerIds": [1, 2]
  }'
  ```

### 3. Get Events

```bash  
curl -X GET http://localhost:8080/events \
  -H "Authorization: Bearer <JWT_TOKEN>"
  ```
