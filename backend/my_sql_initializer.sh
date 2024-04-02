#!/bin/bash

cleanup() {
  echo "Stopping MySQL container..."
    docker stop some-mysql
    echo "Removing MySQL container..."
    docker rm some-mysql
    echo "Exiting..."
    exit 0
}

trap 'cleanup' SIGINT

# 1) Downloading MySQL image from DockerHub or use local version if it was already installed
echo "Getting MySQL image..."
docker pull mysql

# 2) Executing container and setting env. variables
echo "Executing MySQL container..."
docker run --name some-mysql -e MYSQL_ROOT_PASSWORD=password -p 3306:3306 -d mysql:latest
if [ $? -eq 0 ]; then
    echo "Executing container [SUCCESS]"
else
    echo "Executing container [FAILURE]"
    echo "Exiting..."
    exit 1
fi

# 3) Creating urjc_bank DB if it did not exist
echo "Starting MySQL..."
sleep 10
docker exec -i some-mysql mysql -u root -ppassword -e "CREATE DATABASE IF NOT EXISTS urjc_bank;"
if [ $? -eq 0 ]; then
    echo "Creating urjc_bank database [SUCCESS]"
else
    echo "Creating urjc_bank database [FAILURE]"
fi

# 4) Print container's IP
echo "MySQL's container IP:"
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' some-mysql

while true; do
    sleep 1
done