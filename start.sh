#!/bin/bash

echo "Starting Municipal Complaint Management System..."

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Java is not installed. Please install Java 17 or higher."
    exit 1
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Maven is not installed. Please install Maven 3.6 or higher."
    exit 1
fi

# Check if MySQL is running
echo "Checking MySQL connection..."
if ! mysql -h localhost -u root -ppassword -e "SELECT 1;" &> /dev/null; then
    echo "MySQL is not running. Please start MySQL server."
    echo "You can use: sudo systemctl start mysql"
    exit 1
fi

echo "MySQL is running. Starting microservices..."

# Start Config Server
echo "Starting Config Server..."
cd config-server
mvn spring-boot:run > ../logs/config-server.log 2>&1 &
CONFIG_PID=$!
cd ..

# Wait for Config Server to start
echo "Waiting for Config Server to start..."
sleep 10

# Start Eureka Server
echo "Starting Eureka Server..."
cd eureka-server
mvn spring-boot:run > ../logs/eureka-server.log 2>&1 &
EUREKA_PID=$!
cd ..

# Wait for Eureka Server to start
echo "Waiting for Eureka Server to start..."
sleep 15

# Start User Service
echo "Starting User Service..."
cd user-service
mvn spring-boot:run > ../logs/user-service.log 2>&1 &
USER_PID=$!
cd ..

# Start Complaint Service
echo "Starting Complaint Service..."
cd complaint-service
mvn spring-boot:run > ../logs/complaint-service.log 2>&1 &
COMPLAINT_PID=$!
cd ..

# Start Department Service
echo "Starting Department Service..."
cd department-service
mvn spring-boot:run > ../logs/department-service.log 2>&1 &
DEPARTMENT_PID=$!
cd ..

# Start Notification Service
echo "Starting Notification Service..."
cd notification-service
mvn spring-boot:run > ../logs/notification-service.log 2>&1 &
NOTIFICATION_PID=$!
cd ..

# Wait for all services to start
echo "Waiting for all services to start..."
sleep 20

# Start Gateway Service
echo "Starting API Gateway..."
cd gateway-service
mvn spring-boot:run > ../logs/gateway-service.log 2>&1 &
GATEWAY_PID=$!
cd ..

# Save PIDs to file for later cleanup
echo $CONFIG_PID > pids.txt
echo $EUREKA_PID >> pids.txt
echo $USER_PID >> pids.txt
echo $COMPLAINT_PID >> pids.txt
echo $DEPARTMENT_PID >> pids.txt
echo $NOTIFICATION_PID >> pids.txt
echo $GATEWAY_PID >> pids.txt

echo "All services started successfully!"
echo ""
echo "Service URLs:"
echo "API Gateway: http://localhost:8080"
echo "Eureka Server: http://localhost:8761"
echo "Config Server: http://localhost:8888"
echo "User Service: http://localhost:8081"
echo "Complaint Service: http://localhost:8082"
echo "Department Service: http://localhost:8083"
echo "Notification Service: http://localhost:8084"
echo ""
echo "Logs are available in the logs/ directory"
echo "To stop all services, run: ./stop.sh"