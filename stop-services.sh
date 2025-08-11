#!/bin/bash

echo "Stopping Microservices..."

# Function to kill process by PID file
kill_service() {
    local service_name=$1
    local pid_file="logs/$2.pid"
    
    if [ -f "$pid_file" ]; then
        local pid=$(cat "$pid_file")
        if ps -p $pid > /dev/null; then
            echo "Stopping $service_name (PID: $pid)..."
            kill $pid
            sleep 5
            if ps -p $pid > /dev/null; then
                echo "Force killing $service_name..."
                kill -9 $pid
            fi
        else
            echo "$service_name is not running"
        fi
        rm -f "$pid_file"
    else
        echo "PID file for $service_name not found"
    fi
}

# Stop services in reverse order
kill_service "API Gateway" "gateway"
kill_service "User Service" "user"
kill_service "Auth Service" "auth"
kill_service "Eureka Server" "eureka"

echo "All services stopped!"