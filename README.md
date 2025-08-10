# Municipal Complaint Management System

A comprehensive microservices-based online complaint management system for municipalities that supports citizen complaints on public issues like water, sanitation, and roads.

## Architecture Overview

The system consists of the following microservices:

1. **API Gateway Service** - Central entry point with routing, security, and rate limiting
2. **User Service** - User registration, authentication, and role management
3. **Complaint Service** - Complaint creation, tracking, and management
4. **Department Service** - Department management and staff assignment
5. **Notification Service** - Email and SMS notifications
6. **Eureka Server** - Service discovery
7. **Config Server** - Centralized configuration management

## Technology Stack

- **Spring Boot** - Microservices framework
- **Spring Cloud Gateway** - API Gateway
- **Spring Security** - Authentication and authorization
- **Spring Cloud Netflix Eureka** - Service discovery
- **Spring Cloud Config** - Configuration management
- **JWT** - Token-based authentication
- **MySQL** - Database
- **BCrypt** - Password encryption
- **Maven** - Build tool

## Features

### User Management
- Secure registration and login for Citizens, Municipal Staff, and Admins
- Role-based access control with JWT authentication
- BCrypt password encryption
- Custom dashboards for different user roles

### Complaint Management
- File complaints with descriptions and categories (water, sanitation, roads)
- Track complaint status and add comments
- Assign complaints to relevant departments
- Role-based complaint access (citizens see their complaints, staff handle assigned complaints)

### Department Management
- Manage municipal departments (sanitation, water works)
- Assign staff to departments
- Department information management

### Notifications
- Email and SMS notifications for complaint status changes
- Real-time updates to users

### Security & Validation
- JWT token authentication across all services
- Role-based access control with @PreAuthorize annotations
- Bean validation for all inputs
- Rate limiting at the gateway level

## Getting Started

### Prerequisites
- Java 17+
- Maven 3.6+
- MySQL 8.0+
- Docker (optional)

### Running the System

1. **Start MySQL Database**
   ```bash
   # Create database
   CREATE DATABASE complaint_management;
   ```

2. **Start Config Server**
   ```bash
   cd config-server
   mvn spring-boot:run
   ```

3. **Start Eureka Server**
   ```bash
   cd eureka-server
   mvn spring-boot:run
   ```

4. **Start Microservices** (in separate terminals)
   ```bash
   # User Service
   cd user-service
   mvn spring-boot:run

   # Complaint Service
   cd complaint-service
   mvn spring-boot:run

   # Department Service
   cd department-service
   mvn spring-boot:run

   # Notification Service
   cd notification-service
   mvn spring-boot:run

   # API Gateway
   cd gateway-service
   mvn spring-boot:run
   ```

### API Endpoints

#### Gateway Service (Port 8080)
- All API requests go through the gateway at `http://localhost:8080`

#### User Service
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `GET /api/users/profile` - Get user profile
- `GET /api/users/{id}` - Get user by ID

#### Complaint Service
- `POST /api/complaints` - Create complaint
- `GET /api/complaints` - Get complaints (filtered by role)
- `GET /api/complaints/{id}` - Get complaint by ID
- `PUT /api/complaints/{id}` - Update complaint
- `POST /api/complaints/{id}/comments` - Add comment
- `PUT /api/complaints/{id}/assign` - Assign complaint to department

#### Department Service
- `GET /api/departments` - Get all departments
- `POST /api/departments` - Create department
- `PUT /api/departments/{id}/staff` - Assign staff to department

## Data Flow Example

1. Citizen registers and logs in via Gateway → User Service
2. Citizen submits complaint via Gateway → Complaint Service
3. Complaint assignment triggered within Complaint Service updates Department Service
4. Staff updates complaint status; Notification Service sends real-time updates

## Security Features

- JWT token authentication with Spring Security
- Role-based access control using @PreAuthorize annotations
- BCrypt password encryption
- Bean validation for all inputs
- Rate limiting at the gateway level
- Secure endpoints for different user roles

## Database Schema

### Users Table
- id, name, email, password (hashed), username, role

### Complaints Table
- id, category, description, status, assignedDepartment, userId, createdAt, updatedAt

### Comments Table
- id, complaintId, userId, content, createdAt

### Departments Table
- id, name, description, staffIds

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License.