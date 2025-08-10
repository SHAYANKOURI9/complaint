#!/bin/bash

echo "Stopping Municipal Complaint Management System..."

# Check if pids.txt exists
if [ ! -f pids.txt ]; then
    echo "No running services found."
    exit 0
fi

# Read PIDs and kill processes
while IFS= read -r pid; do
    if kill -0 "$pid" 2>/dev/null; then
        echo "Stopping process $pid..."
        kill "$pid"
        sleep 2
        if kill -0 "$pid" 2>/dev/null; then
            echo "Force killing process $pid..."
            kill -9 "$pid"
        fi
    fi
done < pids.txt

# Remove pids file
rm -f pids.txt

echo "All services stopped successfully!"