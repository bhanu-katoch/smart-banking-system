# Smart Banking System & Personal Finance Dashboard

## Project Overview

Smart Banking System is an advanced Java-based backend application combined with a modern React frontend dashboard. It integrates multiple banking features including account management, transactions, loan and interest calculations, stock portfolio tracking, expense budgeting, and personal finance insights.

The React dashboard consumes REST APIs exposed by the Java backend to display real-time data.

---

## Features

### Banking Management

- Multiple account types: Savings, Current, FD.
- Transactions: deposits, withdrawals, transfers.
- Loan and interest management.

### Stock Portfolio Management

- Track investments in stocks.
- Calculate profit/loss.
- Simulate stock trading.

### Personal Finance Assistant

- Track expenses automatically.
- Predict future spending.
- Suggest budgets.
- Categorize transactions.

### Dashboard Features (React + Tailwind)

- Account summary cards.
- Stock portfolio list.
- Recent transactions table.
- Budget cards with progress bars.
- Dark/light theme toggle.

---

## Tech Stack

### Backend

- Java 17
- Spring Boot
- REST APIs
- MySQL (or any relational DB)

### Frontend

- React 18 (Vite)
- TailwindCSS for styling
- React Router for navigation
- Axios for API calls

---

## Folder Structure (React)

```
src/
├── api/                # All API calls (accounts.js, transactions.js, portfolio.js, budget.js)
├── components/         # Reusable UI components (AccountCard, PortfolioList, TransactionsTable, BudgetCard, Navbar)
├── pages/              # Main pages (Dashboard, Accounts, Portfolio, Transactions, Budget)
├── layouts/            # Layouts (MainLayout)
├── App.jsx             # Root component with routing
└── main.jsx
```

---

## 📂 Project Structure (Backend)

```sh
backend/
│── src/
│   └── main/
│       ├── java/
│       │   └── com/bankingsystem/
│       │       ├── Main.java                # Entry point (for terminal simulation)
│       │       │
│       │       ├── config/
│       │       │   └── DatabaseConfig.java  # Handles DB connection
│       │       │
│       │       ├── model/                   # Data Layer (Entities)
│       │       │   ├── User.java            # Abstract class
│       │       │   ├── Student.java         # Extends User
│       │       │   ├── Professional.java    # Extends User
│       │       │   └── Account.java         # Represents Bank Account
│       │       │
│       │       ├── dao/                     # Data Access Layer
│       │       │   ├── UserDAO.java         # CRUD ops for User
│       │       │   ├── AccountDAO.java      # CRUD ops for Account
│       │       │   └── TransactionDAO.java  # For deposits, withdrawals, transfers
│       │       │
│       │       ├── service/                 # Business Logic Layer
│       │       │   ├── AuthService.java     # Login / Signup
│       │       │   ├── AccountService.java  # Account operations
│       │       │   └── TransactionService.java # Deposit, Withdraw, Transfer
│       │       │
│       │       ├── controller/              # Interface Layer
│       │       │   ├── TerminalController.java # Terminal simulation I/O
│       │       │   └── WebController.java   # Future: REST APIs (Spring Boot/Servlets)
│       │       │
│       │       └── util/
│       │           └── PasswordUtils.java   # Hashing, validation, etc.
│       │
│       └── resources/
│           └── application.properties       # DB configs
│
└── pom.xml / build.gradle                   # Dependency management

```

## Installation & Setup

### Backend (Java)

1. Clone the backend repo.
2. Configure database (MySQL) and update `application.properties`.
3. Run the Spring Boot application (`mvn spring-boot:run`).

### Frontend (React)

1. Navigate to the React project folder.
2. Install dependencies:

```bash
npm install
```

3. Start the development server:

```bash
npm run dev
```

4. Open `http://localhost:5173` in your browser.

---

## Notes

- Ensure Java backend is running before starting React frontend.
- `node_modules` is included in `.gitignore`.
- API calls are in `src/api/` folder, making it easy to switch endpoints or mock data.

---

## License

MIT License
