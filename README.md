# 📘 Cookbook API - RESTful Service

This is a Java Spring Boot project that exposes a RESTful API for managing cooking recipes. It supports creation, retrieval, updating, deletion, and filtering of recipes based on parameters like vegetarian preference, included/excluded ingredients, and text content within instructions.

---

## 🚀 Features

* **CRUD operations** for recipes
* **Filtering** by:

    * Vegetarian recipes
    * Included ingredients
    * Excluded ingredients
    * Instruction content search
* DTO mapping layer
* Error handling and validations
* Dockerized application and PostgreSQL database

---

## 🛠️ Tech Stack

* Java 17
* Spring Boot 3.5.0
* Spring Data JPA
* PostgreSQL 14.17
* Project Lombok
* Docker & Docker Compose

---

## 📦 Deliverables

This repository delivers:
- A complete Spring Boot application with CRUD and filtering functionality
- Dockerized environment (PostgreSQL + App)
- Sample data automatically loaded at startup
- Modular and scalable architecture
- Basic error handling and custom exception responses

---

## 🧱 Architecture

The project follows a **Layered Architecture** for maintainability and scalability:

* **Controller Layer**: Handles HTTP requests/responses
* **Service Layer**: Business logic and processing
* **Repository Layer**: Data access logic using JPA
* **Model & DTOs**: Separation of domain and data transfer objects
* **Mapper Layer**: Converts between Entity and DTO representations
* **Exception Layer**: Handles errors consistently via @RestControllerAdvice

This separation of concerns makes the codebase modular, testable, and easier to maintain.

---
## 💡 Design Decisions & Assumptions

* **Ingredients** are modeled as `List<String>` within the `Recipe` to simplify persistence and querying.
* **Filtering** is implemented using Criteria API for flexibility and dynamic query building.
* No authentication or authorization is implemented, assuming public access.
* Spring Boot's auto-wiring is used for dependency injection.

---

## 🐳 Dockerized Setup

The application is fully **dockerized** to ensure a consistent development environment across machines. This removes the need to manually install PostgreSQL or configure the environment.

Running `docker-compose up` will:

* Build and run the Spring Boot application
* Start a PostgreSQL 14.17 instance
* **Automatically execute an SQL script** (`cookbook-data.sql`) to:

    * Create the necessary schema
    * Populate it with initial recipe and ingredient data


---

## 🧪 Running the Application

### Requirements:

* Docker
* Docker Compose
* Maven

First build the project
```bash
mvn clean package
```


To run both the application and the PostgreSQL database:

```bash
docker-compose up --build
```

The application will be available at:

```
http://localhost:8080
```

The PostgreSQL database will be running at:

```
localhost:5432 (DB: recipesdb, user: postgres, password: postgres)
```

The API will be available at: `http://localhost:8080/api/recipes`

---

## 🧪 Endpoints Overview

| Method | Endpoint              | Description         |
| ------ | --------------------- | ------------------- |
| GET    | `/api/recipes/`       | Get all recipes     |
| GET    | `/api/recipes/{id}`   | Get recipe by ID    |
| POST   | `/api/recipes`        | Create a new recipe |
| DELETE | `/api/recipes/{id}`   | Delete a recipe     |
| GET    | `/api/recipes/search` | Search with filters |

Body example:

```json
{
  "title": "Pasta Primavera",
  "description": "Fresh pasta with veggies",
  "instruction": "Cook pasta. Add vegetables. Mix.",
  "vegetarian": true,
  "servings": 2,
  "ingredients": ["Pasta", "Tomato", "Zucchini"]
}
```
---

## 🔍 Filters
Query Parameters:

* `servings` : serving integer quantity
* `vegetarian`: true/false
* `includeIngredients`: comma-separated list
* `excludeIngredients`: comma-separated list
* `contentSearch`: keyword inside instruction


Filtering Sample


```
GET /api/recipes/search?vegetarian=true&includeIngredients=Tomato,Onion&contentSearch=boil
```

---

## 🧪 Tests

Basic unit tests were added to validate essential functionality such as recipe creation, retrieval, deletion, and exception handling. These tests cover the RecipeController and RecipeService layers using JUnit 5 and Mockito.

While the current tests ensure core flows are functional, expanding test coverage and depth — including more detailed filtering scenarios, integration tests, and edge case validations — could further improve the reliability and robustness of the project.

The tests are located under src/test/java and organized by layer, with mocks and assertions using JUnit and Mockito.

To execute them:
```
mvn test
```

---

## 🧾 TODO – Next Steps & Improvements

These are some possible improvements that could enhance the application’s robustness and maintainability::

-  **Test Coverage**  
  Implement unit, integration, and end-to-end tests to ensure reliability and increase coverage.

-  **Code Coverage Monitoring**  
  Integrate tools like JaCoCo to monitor and enforce code coverage thresholds for continuous quality assurance.

-  **Authentication & Authorization**  
  Secure the API using JWT or Basic Auth, and introduce role-based access control if needed (e.g., Admin vs User).

-  **Update (PUT/PATCH) Endpoint**  
  Add support for editing existing recipes, both full replacements (`PUT`) and partial updates (`PATCH`).


---
