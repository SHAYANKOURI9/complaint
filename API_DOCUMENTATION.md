# Municipal Complaint Management System - API Documentation

## Overview

This document provides comprehensive API documentation for the Municipal Complaint Management System. All API endpoints are accessible through the API Gateway at `http://localhost:8080`.

## Authentication

The system uses JWT (JSON Web Token) authentication. Include the JWT token in the Authorization header:

```
Authorization: Bearer <your-jwt-token>
```

## Base URL

All API requests should be made to: `http://localhost:8080`

## User Service APIs

### 1. User Registration

**POST** `/api/auth/register`

Register a new user in the system.

**Request Body:**
```json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "username": "johndoe",
  "password": "password123",
  "role": "CITIZEN"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "username": "johndoe",
  "name": "John Doe",
  "email": "john.doe@example.com",
  "role": "CITIZEN"
}
```

### 2. User Login

**POST** `/api/auth/login`

Authenticate a user and receive a JWT token.

**Request Body:**
```json
{
  "username": "johndoe",
  "password": "password123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "username": "johndoe",
  "name": "John Doe",
  "email": "john.doe@example.com",
  "role": "CITIZEN"
}
```

### 3. Get User Profile

**GET** `/api/users/profile`

Get the profile of the currently authenticated user.

**Headers:**
```
Authorization: Bearer <jwt-token>
```

**Response:**
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "username": "johndoe",
  "role": "CITIZEN",
  "createdAt": "2024-01-15T10:30:00"
}
```

### 4. Get User by ID (Admin Only)

**GET** `/api/users/{id}`

Get user details by ID (Admin access only).

**Headers:**
```
Authorization: Bearer <jwt-token>
```

**Response:**
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "username": "johndoe",
  "role": "CITIZEN",
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00",
  "active": true
}
```

## Complaint Service APIs

### 1. Create Complaint

**POST** `/api/complaints`

Create a new complaint (Citizens only).

**Headers:**
```
Authorization: Bearer <jwt-token>
```

**Request Body:**
```json
{
  "category": "WATER",
  "description": "Water supply is not working in my area for the past 3 days"
}
```

**Response:**
```json
{
  "id": 1,
  "category": "WATER",
  "description": "Water supply is not working in my area for the past 3 days",
  "status": "PENDING",
  "assignedDepartment": null,
  "userId": 1,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

### 2. Get All Complaints

**GET** `/api/complaints`

Get all complaints (filtered by user role).

**Headers:**
```
Authorization: Bearer <jwt-token>
```

**Query Parameters:**
- `status` (optional): Filter by status (PENDING, ASSIGNED, IN_PROGRESS, RESOLVED, CLOSED, REJECTED)
- `category` (optional): Filter by category (WATER, SANITATION, ROADS, etc.)

**Response:**
```json
[
  {
    "id": 1,
    "category": "WATER",
    "description": "Water supply is not working in my area for the past 3 days",
    "status": "PENDING",
    "assignedDepartment": null,
    "userId": 1,
    "createdAt": "2024-01-15T10:30:00",
    "updatedAt": "2024-01-15T10:30:00"
  }
]
```

### 3. Get Complaint by ID

**GET** `/api/complaints/{id}`

Get a specific complaint by ID.

**Headers:**
```
Authorization: Bearer <jwt-token>
```

**Response:**
```json
{
  "id": 1,
  "category": "WATER",
  "description": "Water supply is not working in my area for the past 3 days",
  "status": "PENDING",
  "assignedDepartment": null,
  "userId": 1,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

### 4. Update Complaint Status

**PUT** `/api/complaints/{id}/status`

Update the status of a complaint (Staff/Admin only).

**Headers:**
```
Authorization: Bearer <jwt-token>
```

**Query Parameters:**
- `status`: New status (PENDING, ASSIGNED, IN_PROGRESS, RESOLVED, CLOSED, REJECTED)

**Response:**
```json
{
  "id": 1,
  "category": "WATER",
  "description": "Water supply is not working in my area for the past 3 days",
  "status": "IN_PROGRESS",
  "assignedDepartment": "Water Works",
  "userId": 1,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T11:00:00"
}
```

### 5. Assign Complaint to Department

**PUT** `/api/complaints/{id}/assign`

Assign a complaint to a specific department (Staff/Admin only).

**Headers:**
```
Authorization: Bearer <jwt-token>
```

**Query Parameters:**
- `department`: Department name

**Response:**
```json
{
  "id": 1,
  "category": "WATER",
  "description": "Water supply is not working in my area for the past 3 days",
  "status": "ASSIGNED",
  "assignedDepartment": "Water Works",
  "userId": 1,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T11:00:00"
}
```

### 6. Add Comment to Complaint

**POST** `/api/complaints/{id}/comments`

Add a comment to a complaint.

**Headers:**
```
Authorization: Bearer <jwt-token>
```

**Request Body:**
```json
{
  "content": "We have started working on this issue. Expected resolution time: 2 days."
}
```

**Response:**
```json
{
  "id": 1,
  "complaint": {
    "id": 1
  },
  "userId": 2,
  "content": "We have started working on this issue. Expected resolution time: 2 days.",
  "createdAt": "2024-01-15T11:30:00"
}
```

### 7. Get Comments for Complaint

**GET** `/api/complaints/{id}/comments`

Get all comments for a specific complaint.

**Headers:**
```
Authorization: Bearer <jwt-token>
```

**Response:**
```json
[
  {
    "id": 1,
    "userId": 2,
    "content": "We have started working on this issue. Expected resolution time: 2 days.",
    "createdAt": "2024-01-15T11:30:00"
  }
]
```

## Department Service APIs

### 1. Get All Departments

**GET** `/api/departments`

Get all departments (Staff/Admin only).

**Headers:**
```
Authorization: Bearer <jwt-token>
```

**Response:**
```json
[
  {
    "id": 1,
    "name": "Water Works",
    "description": "Handles water supply and maintenance",
    "staffIds": [2, 3, 4],
    "createdAt": "2024-01-15T10:00:00",
    "updatedAt": "2024-01-15T10:00:00"
  }
]
```

### 2. Get Department by ID

**GET** `/api/departments/{id}`

Get a specific department by ID (Staff/Admin only).

**Headers:**
```
Authorization: Bearer <jwt-token>
```

**Response:**
```json
{
  "id": 1,
  "name": "Water Works",
  "description": "Handles water supply and maintenance",
  "staffIds": [2, 3, 4],
  "createdAt": "2024-01-15T10:00:00",
  "updatedAt": "2024-01-15T10:00:00"
}
```

### 3. Create Department

**POST** `/api/departments`

Create a new department (Admin only).

**Headers:**
```
Authorization: Bearer <jwt-token>
```

**Request Body:**
```json
{
  "name": "Sanitation Department",
  "description": "Handles waste management and sanitation"
}
```

**Response:**
```json
{
  "id": 2,
  "name": "Sanitation Department",
  "description": "Handles waste management and sanitation",
  "staffIds": [],
  "createdAt": "2024-01-15T12:00:00",
  "updatedAt": "2024-01-15T12:00:00"
}
```

### 4. Update Department

**PUT** `/api/departments/{id}`

Update department details (Admin only).

**Headers:**
```
Authorization: Bearer <jwt-token>
```

**Request Body:**
```json
{
  "name": "Water Works Department",
  "description": "Handles water supply, maintenance, and quality control"
}
```

**Response:**
```json
{
  "id": 1,
  "name": "Water Works Department",
  "description": "Handles water supply, maintenance, and quality control",
  "staffIds": [2, 3, 4],
  "createdAt": "2024-01-15T10:00:00",
  "updatedAt": "2024-01-15T12:30:00"
}
```

### 5. Assign Staff to Department

**PUT** `/api/departments/{id}/staff`

Assign staff members to a department (Admin only).

**Headers:**
```
Authorization: Bearer <jwt-token>
```

**Request Body:**
```json
[2, 3, 4, 5]
```

**Response:**
```json
{
  "id": 1,
  "name": "Water Works Department",
  "description": "Handles water supply, maintenance, and quality control",
  "staffIds": [2, 3, 4, 5],
  "createdAt": "2024-01-15T10:00:00",
  "updatedAt": "2024-01-15T12:30:00"
}
```

### 6. Delete Department

**DELETE** `/api/departments/{id}`

Delete a department (Admin only).

**Headers:**
```
Authorization: Bearer <jwt-token>
```

**Response:** 204 No Content

## Notification Service APIs

### 1. Send Notification

**POST** `/api/notifications/send`

Send a custom notification.

**Request Body:**
```json
{
  "subject": "Complaint Update",
  "message": "Your complaint has been resolved.",
  "email": "user@example.com",
  "phoneNumber": "+1234567890",
  "type": "EMAIL"
}
```

**Response:**
```json
{
  "message": "Notification sent successfully",
  "type": "EMAIL"
}
```

### 2. Send Complaint Status Update

**POST** `/api/notifications/complaint-status`

Send a complaint status update notification.

**Query Parameters:**
- `userEmail`: User's email address
- `complaintId`: Complaint ID
- `status`: New status

**Response:**
```json
{
  "message": "Status update notification sent successfully"
}
```

### 3. Send Complaint Assignment

**POST** `/api/notifications/complaint-assignment`

Send a complaint assignment notification.

**Query Parameters:**
- `userEmail`: User's email address
- `complaintId`: Complaint ID
- `department`: Department name

**Response:**
```json
{
  "message": "Assignment notification sent successfully"
}
```

## Error Responses

### 400 Bad Request
```json
{
  "error": "Validation failed",
  "message": "Field validation errors",
  "details": [
    {
      "field": "email",
      "message": "Email should be valid"
    }
  ]
}
```

### 401 Unauthorized
```json
{
  "error": "Unauthorized",
  "message": "Invalid or missing JWT token"
}
```

### 403 Forbidden
```json
{
  "error": "Forbidden",
  "message": "Insufficient permissions to access this resource"
}
```

### 404 Not Found
```json
{
  "error": "Not Found",
  "message": "Resource not found"
}
```

### 500 Internal Server Error
```json
{
  "error": "Internal Server Error",
  "message": "An unexpected error occurred"
}
```

## Rate Limiting

The API Gateway implements rate limiting:
- 10 requests per second per user
- 20 requests burst capacity

## Data Models

### User Roles
- `CITIZEN`: Can create complaints and view their own complaints
- `STAFF`: Can view and manage assigned complaints
- `ADMIN`: Full access to all features

### Complaint Categories
- `WATER`: Water supply issues
- `SANITATION`: Sanitation and waste management
- `ROADS`: Road maintenance and construction
- `ELECTRICITY`: Electrical issues
- `GARBAGE`: Garbage collection
- `STREET_LIGHTING`: Street lighting issues
- `OTHER`: Other municipal issues

### Complaint Statuses
- `PENDING`: Complaint submitted, awaiting assignment
- `ASSIGNED`: Complaint assigned to department
- `IN_PROGRESS`: Work has started on the complaint
- `RESOLVED`: Issue has been resolved
- `CLOSED`: Complaint closed
- `REJECTED`: Complaint rejected

## Testing the APIs

You can use tools like:
- Postman
- cURL
- Insomnia
- Any HTTP client

### Example cURL Commands

1. **Register a new user:**
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "username": "johndoe",
    "password": "password123",
    "role": "CITIZEN"
  }'
```

2. **Login:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "johndoe",
    "password": "password123"
  }'
```

3. **Create a complaint:**
```bash
curl -X POST http://localhost:8080/api/complaints \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your-jwt-token>" \
  -d '{
    "category": "WATER",
    "description": "No water supply for 3 days"
  }'
```

4. **Get all complaints:**
```bash
curl -X GET http://localhost:8080/api/complaints \
  -H "Authorization: Bearer <your-jwt-token>"
```