#!/bin/bash

echo "Starting Microservices..."

# Function to check if a port is available
check_port() {
    local port=$1
    if lsof -Pi :$port -sTCP:LISTEN -t >/dev/null ; then
        echo "Port $port is already in use. Please stop the service using this port first."
        return 1
    fi
    return 0
}

# Check if ports are available
echo "Checking port availability..."
check_port 8761 || exit 1
check_port 8080 || exit 1
check_port 8081 || exit 1
check_port 8082 || exit 1

echo "All ports are available. Starting services..."

# Start Eureka Server first
echo "Starting Eureka Server..."
cd eureka-server
mvn spring-boot:run > ../logs/eureka-server.log 2>&1 &
EUREKA_PID=$!
cd ..

# Wait for Eureka to start
echo "Waiting for Eureka Server to start..."
sleep 30

# Start Auth Service
echo "Starting Auth Service..."
cd auth-service
mvn spring-boot:run > ../logs/auth-service.log 2>&1 &
AUTH_PID=$!
cd ..

# Start User Service
echo "Starting User Service..."
cd user-service
mvn spring-boot:run > ../logs/user-service.log 2>&1 &
USER_PID=$!
cd ..

# Start API Gateway
echo "Starting API Gateway..."
cd api-gateway
mvn spring-boot:run > ../logs/api-gateway.log 2>&1 &
GATEWAY_PID=$!
cd ..

# Create logs directory if it doesn't exist
mkdir -p logs

# Save PIDs to file for later cleanup
echo $EUREKA_PID > logs/eureka.pid
echo $AUTH_PID > logs/auth.pid
echo $USER_PID > logs/user.pid
echo $GATEWAY_PID > logs/gateway.pid

echo "All services started!"
echo "PIDs saved to logs directory"
echo ""
echo "Access URLs:"
echo "Eureka Dashboard: http://localhost:8761"
echo "API Gateway: http://localhost:8080"
echo "Auth Service: http://localhost:8081"
echo "User Service: http://localhost:8082"
echo ""
echo "To stop all services, run: ./stop-services.sh"
echo "To view logs, check the logs directory"