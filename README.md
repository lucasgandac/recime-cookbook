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

## 🧱 Architecture

The project follows a **Layered Architecture** for maintainability and scalability:

* **Controller Layer**: Handles HTTP requests/responses
* **Service Layer**: Business logic and processing
* **Repository Layer**: Data access logic using JPA
* **Model & DTOs**: Separation of domain and data transfer objects
* **Mapper Layer**: Converts between Entity and DTO representations

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

First compile the project
```bash
./gradlew clean build
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


FIltering Sample


```
GET /api/recipes/search?vegetarian=true&includeIngredients=Tomato,Onion&contentSearch=boil
```

---

## 🧾 TODO (Optional Improvements)

* Add Swagger documentation
* Add input validation with exception handling
* Add integration tests with Testcontainers
* Add authentication (e.g. JWT or Basic Auth)

---
