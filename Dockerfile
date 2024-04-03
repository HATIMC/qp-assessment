# Base image
FROM ubuntu:latest

# Install required packages
RUN apt-get update && \
    apt-get install -y \
        openjdk-17-jdk \
        maven \
        git \
        mysql-server \
        && rm -rf /var/lib/apt/lists/*

# Create MySQL user, password, and database
RUN service mysql start && \
    mysql -e "CREATE USER 'qp'@'localhost' IDENTIFIED BY 'qp';" && \
    mysql -e "CREATE DATABASE qp;" && \
    mysql -e "GRANT ALL PRIVILEGES ON qp.* TO 'qp'@'localhost';" && \
    mysql -e "FLUSH PRIVILEGES;"

# Clone your application
RUN git clone https://github.com/HATIMC/qp-assessment.git /app

# Set working directory
WORKDIR /app

EXPOSE 8080

# Build your application with Maven
RUN mvn clean install -Dmaven.test.skip=true

# Define the command to run your application
CMD service mysql start && java -jar target/qp-assessment-1.jar

