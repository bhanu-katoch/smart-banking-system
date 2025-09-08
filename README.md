# 💳 Smart Banking System & Personal Finance Dashboard

## 📖 Project Overview

Smart Banking System is an advanced Java-based backend application combined with a modern React frontend dashboard. It integrates multiple banking features including account management, transactions, loan and interest calculations, stock portfolio tracking, expense budgeting, and personal finance insights.

The React dashboard consumes REST APIs exposed by the Java backend to display real-time data.

---

## ✨ Features

### 🏦 Banking Management

- 💰 Multiple account types: Savings, Current, FD.
- 🔄 Transactions: deposits, withdrawals, transfers.
- 🏦 Loan and interest management.

### 📈 Stock Portfolio Management

- 📊 Track investments in stocks.
- 💹 Calculate profit/loss.
- 🎮 Simulate stock trading.

### 💡 Personal Finance Assistant

- 📝 Track expenses automatically.
- 🔮 Predict future spending.
- 📊 Suggest budgets.
- 🗂️ Categorize transactions.

#### 🎯 User-specific Smart Banking Services

The system offers tailored services for different types of users:

1. **🎓 Student Account Holder**

   - 💸 **Smart Budgeting Tool** → Tracks expenses, categorizes them (food, travel, study), and provides monthly savings tips.
   - 🏦 **Micro-Savings** → Rounds up transactions to the nearest ₹10/₹50 and puts extra into savings automatically.

2. **👨‍💼 Working Professional**

   - 💵 **Salary Splitter** → Automatically divides salary into bills, savings, and investments.
   - 📈 **AI Expense Analyzer** → Generates monthly financial health reports and credit score insights.

3. **🏢 Business Owner**

   - 🏦 **Multi-Account Management** → Handles both personal and business accounts with a single login.
   - 💼 **Smart Loan Advisor** → Suggests optimal loan offers (business, equipment, expansion) based on cash flow.

4. **👴 Senior Citizen**

   - 🔌 **Auto Bill Payments** → Automatically pays utilities (electricity, gas, water) with reminders.
   - 🏥 **Pension & Medical Tracker** → Tracks pension deposits and medical insurance claims.

5. **🌏 Traveler / NRI**
   - 💱 **Currency Converter & Smart Forex Wallet** → Converts balances into required currency with best rates.
   - 🛡️ **Travel Security Alerts** → Flags unusual transactions abroad and provides instant freeze/unfreeze card option.

> 💡 **Implementation Idea (Backend Java)**
>
> - Define classes for each user type: `Student`, `Professional`, `BusinessOwner`, `SeniorCitizen`, `Traveler`.
> - Implement service interfaces like `BudgetingService`, `LoanService`, `ForexService`.
> - Use polymorphism to provide tailored services for each user.
> - Store user data in a relational database (MySQL/PostgreSQL) or use HashMaps for a prototype.

### 🖥️ Dashboard Features (React + Tailwind)

- 🗂️ Account summary cards.
- 📊 Stock portfolio list.
- 🔄 Recent transactions table.
- 📉 Budget cards with progress bars.
- 🌗 Dark/light theme toggle.

---

## 🛠️ Tech Stack

### Backend

- 🔗 REST APIs
- 🗄️ sqlite3 (or any relational DB)

### Frontend

- ⚛️ React 18 (Vite)
- 🎨 TailwindCSS for styling
- 🌐 React Router for navigation
- 📡 Axios for API calls

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
│       │       │   ├── BusinessOwner.java   # Extends User
│       │       │   ├── SeniorCitizen.java   # Extends User
│       │       │   ├── Traveler.java        # Extends User
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
│       │       │   ├── BudgetingService.java   # Student / Professional
│       │       │   ├── LoanService.java        # Business Owner
│       │       │   ├── ForexService.java       # Traveler/NRI
│       │       │   └── BillPaymentService.java # Senior Citizen
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
2. [run terminal mode](backend/how.md)

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
