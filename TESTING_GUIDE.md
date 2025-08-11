# Microservices Testing Guide

This guide shows you how to test all the microservices using JUnit and manual API testing.

## Running JUnit Tests

### 1. Test Auth Service
```bash
cd auth-service
mvn test
```

**Expected Output:**
- Authentication with valid credentials should return a JWT token
- Authentication with invalid credentials should throw an exception
- Token validation should work correctly

### 2. Test User Service
```bash
cd user-service
mvn test
```

**Expected Output:**
- Should retrieve all users (at least 2 demo users)
- Should create, read, update, and delete users successfully
- Should handle user not found scenarios

## Manual API Testing

### Prerequisites
1. Start all services using `./start-services.sh`
2. Wait for all services to be registered in Eureka (check http://localhost:8761)

### 1. Authentication Flow

#### Step 1: Login to get JWT token
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

**Expected Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

#### Step 2: Validate token
```bash
curl -X POST http://localhost:8080/auth/validate \
  -H "Authorization: Bearer YOUR_JWT_TOKEN_HERE"
```

**Expected Response:**
```json
true
```

### 2. User Service Testing

#### Get all users
```bash
curl -X GET http://localhost:8080/users
```

**Expected Response:**
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com"
  },
  {
    "id": 2,
    "name": "Jane Smith",
    "email": "jane.smith@example.com"
  }
]
```

#### Get user by ID
```bash
curl -X GET http://localhost:8080/users/1
```

#### Create new user
```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "New User",
    "email": "newuser@example.com"
  }'
```

#### Update user
```bash
curl -X PUT http://localhost:8080/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Updated John Doe",
    "email": "updated.john@example.com"
  }'
```

#### Delete user
```bash
curl -X DELETE http://localhost:8080/users/2
```

### 3. Health Checks

#### Check service health
```bash
# Auth Service
curl -X GET http://localhost:8081/auth/health

# User Service
curl -X GET http://localhost:8082/users/health

# API Gateway
curl -X GET http://localhost:8080/actuator/health
```

## Demo Credentials

### Auth Service Users
- **Username:** `admin`, **Password:** `admin123`
- **Username:** `user`, **Password:** `user123`

## Troubleshooting

### Common Issues

1. **Port already in use:**
   ```bash
   # Check what's using the port
   lsof -i :8080
   
   # Kill the process
   kill -9 <PID>
   ```

2. **Service not registering with Eureka:**
   - Check if Eureka server is running on port 8761
   - Verify service configuration in `application.yml`
   - Check service logs in the `logs` directory

3. **JWT token validation fails:**
   - Ensure you're using the correct secret key
   - Check if the token hasn't expired (24 hours)
   - Verify the token format (should start with "Bearer ")

### Log Analysis

Check service logs in the `logs` directory:
```bash
# View Eureka server logs
tail -f logs/eureka-server.log

# View Auth service logs
tail -f logs/auth-service.log

# View User service logs
tail -f logs/user-service.log

# View API Gateway logs
tail -f logs/api-gateway.log
```

## Performance Testing

### Load Testing with Apache Bench

```bash
# Test user service with 100 requests, 10 concurrent
ab -n 100 -c 10 http://localhost:8080/users

# Test auth service login
ab -n 50 -c 5 -p login_data.json -T application/json http://localhost:8080/auth/login
```

Create `login_data.json`:
```json
{
  "username": "admin",
  "password": "admin123"
}
```

## Integration Testing

### Test Complete Flow

1. **Start all services**
2. **Get authentication token**
3. **Use token to access protected resources**
4. **Verify service discovery works**
5. **Test API Gateway routing**

This comprehensive testing approach ensures your microservices are working correctly together.