# 💰 Expense Tracker Backend (Spring Boot)

A **RESTful backend API** built with **Java Spring Boot** to manage user expenses and budgets efficiently.
It supports CRUD operations for users and expenses, expense categorization, and filtering by user, month, or category.

---

## 📘 Table of Contents

* [Overview](#overview)
* [Tech Stack](#tech-stack)
* [Dependencies](#dependencies)
* [Project Structure](#project-structure)
* [Models](#models)
* [APIs](#apis)
* [DTOs](#dtos)
* [Services](#services)
* [Global Exception Handling](#global-exception-handling)
* [Swagger Documentation](#swagger-documentation)
* [How to Run](#how-to-run)
* [Future Enhancements](#future-enhancements)
* [License](#license)

---

## 🚀 Overview

This backend provides APIs to:

* Manage **Users** (create, update, delete, retrieve)
* Manage **Expenses** (CRUD operations)
* Filter expenses by **user**, **category**, or **month/year**
* Automatically track **timestamps**
* Handle exceptions gracefully

---

## ⚙️ Tech Stack

| Layer         | Technology                     |
| ------------- | ------------------------------ |
| Language      | Java 17                        |
| Framework     | Spring Boot 3.5.7              |
| ORM           | Hibernate / JPA                |
| Validation    | Jakarta Validation (3.1.1)     |
| Database      | MySQL                          |
| Documentation | Springdoc OpenAPI (Swagger UI) |
| Build Tool    | Maven                          |
| Lombok        | For boilerplate reduction      |
| Dev Tool      | Spring Boot DevTools           |

---

## 🧩 Dependencies

| Dependency                              | Purpose                                     |
| --------------------------------------- | ------------------------------------------- |
| **spring-boot-starter-web**             | Build RESTful APIs                          |
| **spring-boot-starter-data-jpa**        | ORM with Hibernate                          |
| **mysql-connector-j**                   | MySQL database driver                       |
| **jakarta.validation-api**              | Input validation annotations                |
| **spring-boot-devtools**                | Auto-reload during development              |
| **lombok**                              | Generate boilerplate code (getters/setters) |
| **springdoc-openapi-starter-webmvc-ui** | Swagger UI for API documentation            |
| **spring-boot-starter-test**            | Unit and integration testing                |

---

## 📁 Project Structure

```
src/
 ├── main/java/com/example/expensetracker/
 │    ├── controller/        # REST Controllers
 │    ├── dto/               # Data Transfer Objects
 │    ├── exception/         # Custom exceptions and global handler
 │    ├── model/             # JPA entities and enums
 │    ├── repository/        # JPA repositories
 │    ├── service/           # Business logic interfaces & implementations
 │    └── ExpenseTrackerApplication.java
 └── resources/
      ├── application.properties
      └── data.sql (optional)
```

---

## 🧱 Models

### 👤 User

Stores user details and references their expenses.

```java
@Entity
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String password;
    private Double monthlyBudget;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Expense> expenses;
}
```

---

### 💰 Expense

Represents an expense entry.

```java
@Entity
public class Expense {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Double amount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @JsonProperty("userId")
    public Long getUserId() {
        return user != null ? user.getId() : null;
    }

    @PrePersist
    protected void onCreate() { ... }

    @PreUpdate
    protected void onUpdate() { ... }
}
```

---

### 🏷️ Category Enum

```java
public enum Category {
    FOOD, TRAVEL, GADGET, HEALTH, EDUCATION, ENTERTAINMENT,
    BILLS, RENT, SHOPPING, PERSONAL_CARE, OTHER
}
```

---

## 📦 DTOs

Used to validate API inputs and decouple request/response from entities.

### UserDTO

```java
public class UserDTO {
    @NotBlank private String fullName;
    @Email private String email;
    @NotBlank private String password;
    private Double monthlyBudget;
}
```

### ExpenseDTO

```java
public class ExpenseDTO {
    private String description;
    @Min(1) private Double amount;
    @NotNull private Category category;
    @NotNull private Long userId;
}
```

---

## ⚙️ Services

### ExpenseService

Handles all expense operations.

```java
public interface ExpenseService {
    List<Expense> getAllExpenses();
    Expense addExpense(ExpenseDTO expense);
    List<Expense> getAllExpenseByUserId(Long userId);
    Expense getExpenseById(Long id);
    Expense updateExpenseById(Long id, ExpenseDTO expense);
    void deleteExpenseById(Long id);
    List<Expense> getExpenseByCategory(Category category);
    List<Expense> getMonthlyExpense(int month, int year);
}
```

### UserService

Manages user CRUD operations.

```java
public interface UserService {
    List<User> getAllUser();
    User create(UserDTO user);
    User getUserById(Long userId);
    User getUserByEmail(String email);
    User updateUserById(Long userId, UserDTO user);
    void deleteUserById(Long userId);
}
```

---

## ⚠️ Global Exception Handling

A centralized handler for custom exceptions like:

* `InvalidRequestException`
* `NotFoundException`
* `UserAlreadyExistsException`
* `NoDataException`

Each maps to specific HTTP status codes such as `400`, `404`, or `204`.

---

## 📘 Swagger Documentation

The project integrates **Springdoc OpenAPI**, allowing you to explore and test all endpoints visually.

After running the project, open:

```
http://localhost:8080/swagger-ui/index.html
```

---

## ▶️ How to Run

### Prerequisites

* Java 17+
* MySQL installed
* Maven

### Steps

```bash
# Clone repository
git clone https://github.com/AdityaKumar-2501/Expense-Tracker.git
cd expense-tracker-backend

# Build
mvn clean install

# Run
mvn spring-boot:run
```

The server starts at:
`http://localhost:8080`

---

## 🌱 Future Enhancements

* 🔐 Add JWT authentication & authorization
* 📊 Add reporting & statistics (monthly/yearly insights)
* 🧾 Export expenses as CSV or PDF
* 🌍 Add currency and localization support
* 💬 Integrate with a React/Next.js frontend

---

Made By [Aditya Kumar](https://github.com/AdityaKumar-2501)