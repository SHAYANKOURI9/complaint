# Microservices Integration Guide

This guide explains how to integrate and start microservices using:
- **Eureka Server** for service discovery
- **JWT Spring Security** for authentication
- **Spring Web** for REST APIs
- **JUnit** for testing

## Architecture Overview

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Eureka Server │    │  Auth Service   │    │  User Service   │
│   (Port: 8761)  │    │  (Port: 8081)   │    │  (Port: 8082)   │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         └───────────────────────┼───────────────────────┘
                                 │
                    ┌─────────────────┐
                    │  API Gateway    │
                    │  (Port: 8080)   │
                    └─────────────────┘
```

## Quick Start

1. **Start Eureka Server first:**
   ```bash
   cd eureka-server
   mvn spring-boot:run
   ```

2. **Start Auth Service:**
   ```bash
   cd auth-service
   mvn spring-boot:run
   ```

3. **Start User Service:**
   ```bash
   cd user-service
   mvn spring-boot:run
   ```

4. **Start API Gateway:**
   ```bash
   cd api-gateway
   mvn spring-boot:run
   ```

## Access Points

- **Eureka Dashboard:** http://localhost:8761
- **API Gateway:** http://localhost:8080
- **Auth Service:** http://localhost:8081
- **User Service:** http://localhost:8082

## Testing

Run tests for each service:
```bash
mvn test
```

## JWT Authentication Flow

1. Client sends credentials to `/auth/login`
2. Auth service validates and returns JWT token
3. Client includes JWT in Authorization header
4. API Gateway validates JWT before routing requests
5. Microservices can validate JWT for additional security