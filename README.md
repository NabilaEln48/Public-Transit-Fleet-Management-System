# Public Transit Fleet Management System

A backend-driven system for managing public transit fleet operations, including vehicle tracking, fuel monitoring, and role-based access control. The application is designed to support core fleet management workflows while maintaining a clean separation between presentation, business logic, and data access layers.

---

## 🛠 Tech Stack

* **Backend:** Java (Servlets, JDBC)
* **Frontend:** JSF (JavaServer Faces), JSP
* **Desktop Module:** Java Swing (administrative tools)
* **Database:** Relational SQL Database
* **Architecture:** MVC, n-tier architecture
* **Data Access:** DAO (Data Access Object) pattern

---

## 🏗 Architecture Overview

The system follows an **n-tier architecture** with clear responsibility boundaries:

* **Presentation Layer**

  * JSF / JSP web interfaces
  * Swing-based desktop administration module

* **Controller Layer**

  * Java Servlets acting as request handlers
  * Centralized request routing and access validation

* **Business Logic Layer**

  * Fleet operations (vehicle status, fuel updates, trip management)
  * Reporting and calculation logic

* **Data Access Layer**

  * JDBC-based DAO classes
  * SQL database persistence and querying

---

## 🚀 Core Features

* User authentication and role-based access control
* Vehicle registration and fleet tracking
* GPS data recording and route history
* Fuel usage monitoring and efficiency tracking
* Fleet status and operational reporting
* Administrative management tools

---

## 🔐 Security & Access Control

* Centralized authentication through servlet controllers
* Role-based authorization for administrative and operational users
* Controlled access to fleet data and reporting modules

---

## 📁 Project Structure

```
src/
├── controllers/        # Servlets and request handlers
├── services/           # Business logic
├── dao/                # JDBC data access objects
├── models/             # Domain entities (Vehicle, Driver, Trip)
├── views/              # JSF / JSP pages
└── admin-client/       # Java Swing desktop module
```

---

## 📊 Design Documentation

* **Class Diagrams:** Define relationships between vehicles, drivers, trips, and fuel records
* **Sequence Diagrams:** Illustrate request flow from client interfaces to database operations

---

## ⚙️ Setup & Execution

1. Clone the repository:

```bash
git clone https://github.com/NabilaEln48/Public-Transit-Fleet-Management-System.git
```

2. Configure the database connection in the JDBC configuration file.
3. Deploy the web application to an application server (Apache Tomcat / GlassFish).
4. Run the Swing-based administrative module from the desktop client package.

---

## 📌 Project Scope

This project focuses on **core fleet management functionality** rather than advanced real-time analytics. It is intended as a demonstration of:

* Java web application architecture
* MVC and n-tier design
* JDBC-based persistence
* Modular, maintainable backend structure

---
