# Finance API

A RESTful API for personal budget management using the envelope budgeting method. Built with Spring Boot 3 and Java 17, following hexagonal architecture principles.

## Features

- JWT-based authentication (register & login)
- Account management (checking, savings, credit card, cash)
- Category/envelope management with monthly budgets
- Transaction processing (income, expense, transfer)
- Monthly budget summary with spending tracking per category
- Soft delete for accounts and categories
- Input validation and structured error responses

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 17 |
| Framework | Spring Boot 3.5 |
| Security | Spring Security + JWT (jjwt) |
| Database | MySQL 8 |
| ORM | Spring Data JPA / Hibernate |
| Mapping | MapStruct |
| Documentation | SpringDoc OpenAPI (Swagger) |
| Build | Maven |
| Deployment | Docker + Render |

## Architecture

The project follows a **hexagonal architecture** organized by domain modules:

src/main/java/org/gdiazm/finance/
├── auth/          # Authentication (register, login)
├── account/       # Bank accounts management
├── category/      # Envelope categories
├── transaction/   # Financial transactions
├── summary/       # Monthly budget summary
├── user/          # User profile
├── common/        # Shared utilities, exceptions, DTOs
├── security/      # JWT filter, security config
└── config/        # Swagger, CORS

## API Endpoints

### Auth
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/register` | Register a new user |
| POST | `/auth/login` | Login and get JWT token |

### Accounts
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/accounts` | List user accounts |
| GET | `/accounts/{id}` | Get account by ID |
| POST | `/accounts` | Create account |
| PATCH | `/accounts/{id}` | Update account |
| DELETE | `/accounts/{id}` | Deactivate account |

### Categories
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/categories` | List active categories |
| GET | `/categories/{id}` | Get category by ID |
| POST | `/categories` | Create category |
| PATCH | `/categories/{id}` | Update category |
| DELETE | `/categories/{id}` | Deactivate category |

### Transactions
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/transactions` | List transactions (with filters & pagination) |
| GET | `/transactions/{id}` | Get transaction by ID |
| POST | `/transactions` | Create transaction |

### Summary
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/summary/{year}/{month}` | Get monthly budget summary |

### User
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/users/me` | Get current user profile |
| PATCH | `/users/me` | Update profile |
| PATCH | `/users/me/password` | Change password |

## Business Rules

- **EXPENSE**: deducts from account balance; validates monthly category budget
- **INCOME**: adds to account balance; category is optional
- **TRANSFER**: moves funds between accounts; credit cards cannot be transfer source
- Category budgets reset automatically each month (query-based, no scheduled jobs)
- Default categories are seeded for every new user on registration


### API Documentation

Once running, Swagger UI is available at: http://localhost:8080/swagger-ui/index.html
