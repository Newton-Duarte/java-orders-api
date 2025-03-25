# Spring Boot Orders Management API

A REST API for managing orders, products, users, and authentication with comprehensive security and validation features.

## Overview

The Orders Management API is a full-featured Spring Boot application designed to provide secure and efficient management of orders, products, and user interactions. It offers complete CRUD operations with comprehensive validation.

## Technology Stack

- **Java**: Primary programming language
- **Spring Boot**: Application framework
- **Spring Security**: Authentication and authorization
- **Spring Validation**: Input validation and constraints
- **Spring Doc Swagger**: API documentation
- **PostgreSQL**: Relational database
- **JUnit & Mockito**: Testing frameworks

## Key Features

- **Authentication**
    - User registration and login
    - JWT-based bearer token authentication
    - Secure endpoints

- **Product Management**
    - Create, Read, Update, and Delete (CRUD) operations
    - Product details include name and price

- **User Management**
    - User profile CRUD operations
    - Secure user information handling

- **Order Management**
    - Comprehensive order tracking
    - Order status management (PENDING, COMPLETE)
    - Multiple products per order
    - Product quantity and price tracking

## API Endpoints

### Authentication Endpoints
- `POST /auth/sign-up`: Register a new user
- `POST /auth/sign-in`: Authenticate and receive JWT token

### User Profile Endpoints
- `GET /user-profile`: Retrieve the current user profile
- `PUT /user-profile`: Update the current user profile

### Product Endpoints
- `GET /products`: List all products
- `POST /products`: Create a new product
- `GET /products/{id}`: Retrieve a specific product
- `PUT /products/{id}`: Update an existing product
- `DELETE /products/{id}`: Delete a product

### User Endpoints
- `GET /users`: List all users
- `POST /users`: Create a new user
- `GET /users/{id}`: Retrieve a specific user
- `PUT /users/{id}`: Update an existing user
- `DELETE /users/{id}`: Delete a user

### Order Endpoints
- `GET /orders`: List all orders
- `POST /orders`: Create a new order
- `GET /orders/{id}`: Retrieve a specific order
- `PUT /orders/{id}`: Update an existing order
- `DELETE /orders/{id}`: Delete an order

## Authentication Process

1. Register a new user via `/auth/sign-up`
2. Login using `/auth/sign-in` to receive a JWT token
3. Include the token in the Authorization header for protected routes
   ```
   Authorization: Bearer <your_jwt_token>
   ```

## Testing

The project includes comprehensive test coverage:
- **Unit Tests**: Focused testing of individual components
- **Integration Tests**: Verify interaction between components and system integration

### Running Tests

```bash
# Run all tests
./mvnw test

# Run unit tests
./mvnw test -Dtest=*UnitTest

# Run integration tests
./mvnw test -Dtest=*IntegrationTest
```

## Swagger Documentation

API documentation is generated using Spring Doc Swagger:
- Access Swagger UI at: `http://localhost:8080/swagger-ui.html`
- View API documentation at: `http://localhost:8080/v3/api-docs`

## Getting Started

### Prerequisites

- Java 17+
- Maven
- PostgreSQL

### Installation

1. Clone the repository
   ```bash
   git clone https://github.com/newton-duarte/java-orders-api
   cd java-orders-api
   ```

2. Configure `application.properties`:
   ```properties
    # Database Connection
    spring.datasource.url=jdbc:postgresql://localhost:5432/ordersapidb
    spring.datasource.username=docker
    spring.datasource.password=docker
    spring.datasource.driver-class-name=org.postgresql.Driver
    
    # JPA Configuration
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.format_sql=true
    spring.jpa.properties.hibernate.check_nullability=true
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
    
    # JWT Configuration
    jwt.secret=your-256-bit-secret-key-here-make-it-at-least-32-bytes-long
    
    # Swagger UI Configuration
    springdoc.default-produces-media-type=application/json
   ```

3. Build the project
   ```bash
   ./mvnw clean install
   ```

4. Run the application
   ```bash
   ./mvnw spring-boot:run
   ```

## Future Enhancements

- Implement advanced filtering and searching
- Create a front-end client application

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License.

---

Made with ❤️ by Newton Duarte