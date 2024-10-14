# E-commerce-system-server
This repository contains an e-commerce system server side code which is made for a job application by Nikolett Müller. 


## Features
- Create new user with role
- Authenticate user with JWT token
- User detail view
- Product management (add, update, delete, get) for admin
- Placing order and view history


## Tech
- Java 21
- Spring Boot 3
- Postgres

## Build

To build, run the following command
```bash
mvn clean install
```

## Running Tests

To run tests, run the following command

```bash
  mvn clean test
```

## Properties
| Property                             | Description                                                    | Example Value                                           |
|-------------------------------------|---------------------------------------------------------------|--------------------------------------------------------|
| `spring.application.name`           | Name of the application for identification purposes.          | `e-commerce-system-server`                             |
| **JWT**                             |                                                               |                                                        |
| `security.jwt.secret-key`           | Secret key used for signing JWT tokens to ensure security.    | `3cfa76ef149serserbsebrrd5765r6zrthtd5d0a7567c272e007b` |
| `security.jwt.expiration-time`      | Duration (in milliseconds) before a JWT token expires.        | `3600000` (1 hour)                                    |
| **Https**                           |                                                               |                                                        |
| `server.ssl.enabled`                | Enables SSL for secure connections over HTTPS.                | `true`                                                 |
| `server.port`                       | Port on which the server will listen for incoming requests.   | `8443`                                                |
| `server.ssl.key-store-type`         | Type of the keystore that holds the SSL certificate.          | `PKCS12`                                              |
| `server.ssl.key-store`              | Path to the keystore file containing the SSL certificate.     | `classpath:e-commerce-system.p12`                     |
| `server.ssl.key-store-password`     | Password to access the keystore file.                         | `password`                                            |
| `server.ssl.key-alias`              | Alias of the key within the keystore for SSL.                | `e-commerce-system`                                    |
| **DB**                              |                                                               |                                                        |
| `spring.datasource.url`             | JDBC URL for the database connection.                         | `jdbc:postgresql://localhost:5432/postgres`          |
| `spring.datasource.username`        | Username for database authentication.                         | `e-commerce-system`                                   |
| `spring.datasource.password`        | Password for database authentication.                         | `password`                                            |
| `spring.jpa.database-platform`      | Specifies the database dialect for JPA.                       | `org.hibernate.dialect.PostgreSQLDialect`            |
| `spring.jpa.hibernate.ddl-auto`     | Strategy for handling database schema generation.             | `none`                                                |
| `spring.jpa.defer-datasource-initialization` | Indicates whether to defer datasource initialization until after the application context is fully initialized. | `true` |
| `spring.sql.init.mode`              | Controls the initialization mode for SQL scripts.             | `always`                                              |

## SCHEMA for postgres
```bash
CREATE TABLE USERS (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(255) NOT NULL,
  created_at TIMESTAMP NOT NULL
);

CREATE TABLE PRODUCTS (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(255) NOT NULL,
  price NUMERIC NOT NULL,
  created_at TIMESTAMP NOT NULL
);

CREATE TABLE ORDERS (
  id SERIAL PRIMARY KEY,
  user_id INT NOT NULL,
  created_at TIMESTAMP NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE ORDER_ITEMS (
  id SERIAL PRIMARY KEY,
  order_id INT NOT NULL,
  product_id INT NOT NULL,
  quantity INT NOT NULL,
  price NUMERIC NOT NULL,
  FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
  FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);
```
## Documentation
[Swagger](https://localhost:8443/swagger-ui/index.html)


## 🔗 Links
[![portfolio](https://img.shields.io/badge/my_portfolio-000?style=for-the-badge&logo=ko-fi&logoColor=white)](https://github.com/nikolettmuller?tab=repositories)