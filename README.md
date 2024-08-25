## Overview

This project is a Spring Boot application running on version 3.3.2 with Java 21. It uses PostgreSQL as its primary
database and leverages Testcontainers for database integration tests. The application is fully monitored using
Prometheus and Grafana, with security handled via JWT tokens. All services can be easily started using Docker.

## Prerequisites

- **Docker**: Ensure that Docker is installed and running on your machine.
- **Java 21**: The application is built using Java 21. Ensure that you have the correct Java version installed if you
  plan to run it outside of Docker.

## Services

### 1. **Spring Boot Application**

- **URL**: [http://localhost:8080](http://localhost:8080)
- The application is secured using JWT tokens and runs on Spring Boot 3.3.2.
- It connects to a PostgreSQL database for data storage.
- A Postman collection for interacting with the API is included in the `postman` folder.

### 2. **Swagger UI**

- **URL**: [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)
- The Swagger UI provides a user-friendly interface to interact with the API endpoints.

### 3. **Prometheus**

- **URL**: [http://localhost:9090](http://localhost:9090)
- Prometheus is used for monitoring and alerting. It scrapes metrics from the Spring Boot application and other
  services.

### 4. **Grafana**

- **URL**: [http://localhost:3000](http://localhost:3000)
- **Login**:
    - **Username**: `admin`
    - **Password**: `admin`
- Grafana is set up to visualize the metrics collected by Prometheus. A pre-configured dashboard is automatically
  included after startup.
- **Pre-configured Dashboard**: Grafana is set up to visualize the metrics collected by Prometheus.
    - **IMPORTANT**: A **pre-configured dashboard** is automatically included after startup. This dashboard provides
      critical insights into the application's performance and health, giving you real-time visibility into key metrics.
      Make sure to explore this dashboard to monitor your application's status effectively.

### 5. **PostgreSQL Database**

- The application uses a PostgreSQL database to store its data.
- The database service is included in the Docker Compose setup and will automatically start with the application.

## Security

- The application uses JWT tokens for security.
- **Token Retrieval**:
    - First, register a new user by calling the `/register` endpoint with the required details.
    - After successfully registering, obtain a JWT token by hitting the `/login` endpoint with the same credentials.
    - Use this token to authenticate subsequent requests to secured endpoints in the application.

## Testing

- **Testcontainers**: The project uses Testcontainers for integration testing. This allows tests to run in isolated
  Docker containers, ensuring that the tests are consistent and do not interfere with your local environment.
- Tests will automatically spin up a PostgreSQL container to mimic the production database environment.

## Running the Application

To start the application and all associated services, navigate to the root directory of the project and execute the
following command:

```bash
docker-compose up --build
```

This will build and start the Spring Boot application along with Prometheus, Grafana, and PostgreSQL using Docker.

## Postman Collection

- The Postman collection for the API is located in the postman folder.
- Import this collection into Postman to easily interact with the API.

## Monitoring and Dashboards

Once the application is running:

- Access Prometheus at http://localhost:9090 to explore metrics.
- Access Grafana at http://localhost:3000 to view the pre-configured dashboard for monitoring the application's
  performance and health.

## Troubleshooting

- If any service fails to start, check the Docker logs for errors using the following command:

```bash
docker-compose logs
```

- Ensure that no other services are running on the default ports (8080, 9090, 3000) before starting the application.

## Conclusion

This setup provides a full-featured environment with database integration, monitoring, API interaction, and security
features. With the included Docker configuration, starting and managing the application and its services is
straightforward and efficient.