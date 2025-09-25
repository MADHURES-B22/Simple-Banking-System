# **Banking System Project | Java**

### **Project Overview**

This project is a **console-based Banking System** implemented in **Java**.  
 It simulates a real-world banking environment, allowing customers and admins to perform banking operations securely.  
 The system demonstrates **Object-Oriented Programming (OOP), Java Collections, encapsulation, inheritance, polymorphism, and exception handling**.

---

### **Project Features**

#### **Customer Features**

* Login using account number and PIN.

* Deposit money into the account.

* Withdraw money:

  * **Savings Account** → respects minimum balance (1000).

  * **Current Account** → allows overdraft up to a limit (2000).

* Check account balance.

* Change PIN securely.

#### **Admin Features**

* Login using admin credentials.

* View details of all accounts.

* Reset a user’s PIN.

* Create new accounts (Savings or Current).

#### **Security**

* PIN verification is required before any transaction.

* Encapsulation is used to protect account data.

---

### **Classes and OOP Concepts Used**

#### **1\. BankAcc (Abstract Class)**

* **Encapsulation:** Private fields `accNo`, `name`, `balance`, `pin`.

* **Abstraction:** Abstract method `withdraw(double amt)` implemented differently by subclasses.

* **Methods:**

  * `deposit(double amt)` → adds money.

  * `showBalance()` & `showDetails()` → display info.

  * `verifyPin(int)` → checks PIN.

  * `changePin()` & `resetPin()` → secure PIN management.

#### **2\. SavingsAcc (Subclass of BankAcc)**

* **Inheritance:** Inherits BankAcc.

* **Polymorphism:** Overrides `withdraw()` to enforce minimum balance.

#### **3\. CurrentAcc (Subclass of BankAcc)**

* **Inheritance:** Inherits BankAcc.

* **Polymorphism:** Overrides `withdraw()` to allow overdraft up to limit.

#### **4\. BankingSystem (Main Class)**

* Handles program flow, menus, and user interactions.

* Uses **Java Collections (`HashMap<Integer, BankAcc>`)** to store all accounts efficiently.

* Demonstrates **loops, conditional statements, and exception handling**.

---

### **Collections Used**

* **HashMap\<Integer, BankAcc\> accounts**

  * Stores all bank accounts using **account number as key**.

  * Enables **fast lookup** for login, transactions, and admin operations.

---

### **Exception Handling**

* Withdrawal in Savings Account → throws Exception if min balance is violated.

* Withdrawal in Current Account → throws Exception if overdraft limit exceeded.

* PIN change → throws Exception if old PIN is incorrect.

* Invalid inputs handled gracefully with `try-catch`.

---

### **Java Concepts Used**

* **OOP Concepts:**

  * **Encapsulation:** Private account details.

  * **Inheritance:** SavingsAcc & CurrentAcc extend BankAcc.

  * **Polymorphism:** `withdraw()` behaves differently per account type.

  * **Abstraction:** BankAcc as abstract base class.

* **Collections:** HashMap for storing and managing accounts.

* **Exception Handling:** `try-catch` blocks for invalid operations.

* **Control Structures:** Loops, switch-case, and conditional statements for menus.

* **Scanner Class:** For reading input from console.

### **Sample Data**

**Accounts:**

* Savings → Acc No: 101, Name: Arjun, Balance: 5000, PIN: 1111

* Current → Acc No: 102, Name: Meera, Balance: 8000, PIN: 2222

**Admin Credentials:**

* Username: `Loga`

* Password: `admin@1234`

