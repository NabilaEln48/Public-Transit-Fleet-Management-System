# Public-Transit-Fleet-Management-System-PTFMS-


👥 Team Member Roles and Task Distribution
🔹 NABILA : Frontend & Authentication
Responsibilities:

Design and implement login/register HTML forms

Implement servlet logic for:

User registration

Authentication & role-based access

Secure password hashing (SHA-256)

Help write Use Case for FR-01 (User Auth)

Delivers:

register.jsp, login.jsp

AuthServlet.java, UserDAO.java

Part of UML + Class Diagram for User model

🔹 PURNIMA : Vehicle & GPS Management
Responsibilities:

CRUD for vehicle registration

Implement GPS logging and out-of-service logging

Contribute to:

FR-02 (Vehicle Management)

FR-03 (GPS Tracking)

DAO + Servlets for vehicle and gps_logs tables

Delivers:

VehicleServlet.java, GPSLogServlet.java

VehicleDAO.java, GPSLogDAO.java

Write Class Diagram and Use Cases for vehicle and GPS modules

🔹 SIMRAN : Maintenance & Fuel Monitoring
Responsibilities:

Fuel/Energy logging and alert system

Predictive Maintenance (FR-04 and FR-05)

Create maintenance and fuel DAOs and servlets

Schedule alert generation logic

Delivers:

FuelServlet.java, MaintenanceServlet.java

FuelDAO.java, MaintenanceDAO.java

Alert logic for excessive fuel or overdue components

🔹 XIE : Reporting, Testing & Final Assembly
Responsibilities:

Dashboards for:

Maintenance

Operator performance

Fuel cost reports

Write JUnit tests for all DAO classes

Compile High-Level Design Doc and slides

ERD + Final SQL schema + .properties file

Delivers:

DashboardServlet.java

OperatorPerformanceDAO.java

JUnit test cases

PowerPoint and final PDF/doc packaging
