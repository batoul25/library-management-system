
# Library Management System

This is a Spring Boot application for managing books, patrons, and borrowing records in a library. It provides RESTful APIs for performing CRUD operations on books, patrons, and borrowing records.


## Table of Contents
1- Prerequisites

2- Setup

3- Running the Application

4- Endpoints

5- Authentication 

6- Running Tests

7- License
## Prerequisites
Before you begin, ensure you have the following installed:

- Java 23 or

- Gradle 

- MySQL, PostgreSQL, or H2 database (for development, H2 is recommended)

- Postman (for testing the API)

## Setup
1- Clone the repository:
```bash
git clone https://github.com/your-username/library-management-system.git

```

2- Navigate to the project directory:
```bash
cd library-management-system

```



## Running the Application
1- Configure the database in application.properties:
```bash
# H2 Database (for development)
spring.datasource.url=jdbc:h2:mem:librarydb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
```
2- Build the project using Gradle:
```bash
./gradlew build
```
3- Run the application:
```bash
./gradlew bootRun
```
4- Access the application at:
```bash
http://localhost:8080
```


## Endpoints
Book Management
- GET /api/books: Get all books.

- GET /api/books/{id}: Get a book by ID.

- POST /api/books: Add a new book.
```bash
{
  "title": "New Book",
  "author": "Author Name",
  "isbn": "1234567890",
  "publicationYear": "2023-01-01"
}
```
- PUT /api/books/{id}: Update an existing book.
```bash
{
  "title": "Updated Book",
  "author": "Updated Author",
  "isbn": "0987654321",
  "publicationYear": "2023-01-01"
}
```
- DELETE /api/books/{id}: Delete a book.

Patron Management
- GET /api/patrons: Get all patrons.

- GET /api/patrons/{id}: Get a patron by ID.

- POST /api/patrons: Add a new patron.
```bash
{
  "name": "John Doe",
  "contactInfo": "john.doe@example.com"
}
```
- PUT /api/patrons/{id}: Update a patron.
```bash
{
  "name": "Jane Doe",
  "contactInfo": "jane.doe@example.com"
}
```
- DELETE /api/patrons/{id}: Delete a patron.

Borrowing Management
- POST /api/borrow/{bookId}/patron/{patronId}: Borrow a book.

- PUT /api/return/{bookId}/patron/{patronId}: Return a book.
## Authentication
1- Register a new user:
```bash
POST /api/auth/register
{
    "username": "user",
    "password": "password"
}
```
2- Log in to get a JWT token:
```bash
POST /api/auth/login
{
    "username": "user",
    "password": "password"
}
```
3- Include the token in the Authorization header for secured endpoints:
```bash
Authorization: Bearer <token>
```

## Running Tests
Run the tests using Gradle:
```bash
./gradlew test
```
## License

This project is licensed under the [MIT](https://choosealicense.com/licenses/mit/)
 License.


## Contact
For questions or feedback, please contact:
- Batoul Jdid
- Email: batouljdid25@gmail.com
- GitHub: https://github.com/batoul25
