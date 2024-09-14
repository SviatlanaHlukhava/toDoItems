# Todo Application

This is a Spring Boot-based RESTful Todo application that allows users to create, read, update, and delete (CRUD) todo items. The application uses MySQL for data persistence, Hibernate for ORM, and exposes a REST API for interacting with todo items.

## Features

- **Create a Todo**: Add a new todo item with a title and description.
- **Read Todos**: Retrieve a list of all todo items or view a specific todo by its ID.
- **Update a Todo**: Modify an existing todo item's title and description.
- **Delete a Todo**: Remove a todo item by its ID.

## Technologies Used

- Java 17
- Spring Boot 3.1.0
- Hibernate (JPA)
- MySQL
- Gradle (Build Tool)
- JUnit 5 (Testing)
- MockMvc (Controller Testing)
- PMD (Code Quality)
- Checkstyle (Code Style)
- Jacoco (Test Coverage)

## Project Structure

- **Controller**: Handles REST API endpoints.
- **Service**: Contains the business logic. `TodoService` is the interface, and `TodoServiceImpl` is its implementation.
- **Repository**: Manages database interactions using Spring Data JPA.
- **Entity**: Defines the `TodoItem` entity.

## Prerequisites

Before running the application, make sure you have the following installed:

1. **Java 17** or higher
2. **MySQL** (running locally or on a server)
3. **Gradle**

## Configuration

You can configure the database and other application settings using environment variables to keep sensitive information like database credentials secure. The application uses [Spring Boot's support for externalized configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config).

### Environment Variables

- `MYSQL_DB_URL`: URL for the MySQL instance.
- `MYSQL_DB_USERNAME`: Username for the MySQL database.
- `MYSQL_DB_PASSWORD`: Password for the MySQL database.

Example `.env` file:

```bash
MYSQL_DB_URL=jdbc:mysql://localhost:3306/todoapp
MYSQL_DB_USERNAME=root
MYSQL_DB_PASSWORD=yourpassword
```

To run the application with these environment variables, you can use the following command:

```bash
export MYSQL_DB_URL=jdbc:mysql://localhost:3306/todoapp
export MYSQL_DB_USERNAME=root
export MYSQL_DB_PASSWORD=yourpassword
```

Alternatively, you can set these variables directly in your IDE or container orchestration tool.

## How to Run the Application

### Step 1: Clone the Repository

```bash
git clone https://github.com/your-repo/todo-app.git
cd todo-app
```

### Step 2: Set Up MySQL

1. Ensure MySQL is installed and running.
2. Create a database:

```sql
CREATE DATABASE todoapp;
```

### Step 3: Run the Application

With the environment variables set, you can now run the application using Gradle.

#### Run Locally

```bash
./gradlew bootRun
```

#### Run Tests

You can run the test suite, including unit and integration tests, using:

```bash
./gradlew test
```

#### Generate Test Coverage Report

```bash
./gradlew jacocoTestReport
```

The coverage report will be generated in `build/reports/jacoco/test/html/index.html`.

#### Code Quality Check

Run code quality checks (PMD and Checkstyle):

```bash
./gradlew check
```

#### Access the Application

Once the application is running, you can access the API at:

- **Base URL**: `http://localhost:8080/api/todos`

### API Endpoints

- **GET /api/todos**: Get a list of all todos.
- **GET /api/todos/{id}**: Get a specific todo by ID.
- **POST /api/todos**: Create a new todo. (JSON payload)
- **PUT /api/todos/{id}**: Update a specific todo. (JSON payload)
- **DELETE /api/todos/{id}**: Delete a todo by ID.

### Example API Request (Creating a Todo)

```bash
curl -X POST http://localhost:8080/api/todos \
-H "Content-Type: application/json" \
-d '{"title": "New Todo", "description": "Todo description"}'
```

## Code Style and Quality

This project uses **PMD** for complexity checks and **Checkstyle** for enforcing coding standards. You can view reports by running:

- **PMD Report**: `./gradlew pmdMain`
- **Checkstyle Report**: `./gradlew checkstyleMain`

Reports are available in the `build/reports/pmd/main.html` and `build/reports/checkstyle/main.html` respectively.



Short feedback
- It was easy to complete with task with AI.
- It takes me ~2.5h to complete this task.
- Code was not ready to run after generation. However, some parts didn't require changes (entity, the test for the service) 
But there were other parts that needs improvement (application,properties, service, exception handling) and
parts that required research and fix (checkstyle.xml, pmd, controller test, seems to be because of library version changes)
- Which specific prompts you learned as a good practice to complete the task?
- The prompts as "Can you update project structure?" was useful as it helped to understand what should be updated in general.
Also "Can you provide readme.md file content with the application description and instructions on how to run it?" prompt helped get well-structured and formatted Readme.
