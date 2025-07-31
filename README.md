# Public Transit Fleet Management System (PTFMS)

## Team Member Roles and Task Distribution

---

### **Nabila Msiah – Frontend Development & Authentication Module**

Nabila Msiah – Frontend Development & Authentication Module
Responsibilities:

Designed and implemented secure login and registration interfaces using JSP and JSTL:

WEB-INF/Auth/register.jsp for user registration.

WEB-INF/Auth/login.jsp for user login.

Developed centralized routing logic using the Front Controller design pattern in FrontControllerServlet.java:

Handled all user actions via action parameters (e.g., action=login, action=register, etc.).

Managed role-based access control and secure page forwarding.

Implemented user authentication and registration through UserDAO and UserDAOImpl using JDBC.

Applied SHA-256 hashing for secure password storage.

Built public entry points for the app using index.html and JSP redirectors (e.g., register.jsp → controller).

Assisted in drafting functional requirement FR-01 (User Authentication).

Contributed to the UML and class diagrams for the User module, DAO, DTO, and servlet/controller layers.

Key Deliverables:

Frontend Views:

index.html, register.jsp (redirector), WEB-INF/Auth/register.jsp, WEB-INF/Auth/login.jsp

Controller & Backend:

FrontControllerServlet.java, UserDAO.java, UserDAOImpl.java, UserDTO.java, DataSource.java

Dashboards:

Dashboard/manager.jsp, Dashboard/operator.jsp

Design Diagrams:

UML and class diagrams showing User, DAO, DTO, and controller interactions



---

### **Purnima – Vehicle Management & GPS Logging**

**Responsibilities:**
- Implemented CRUD operations for vehicle registration.
- Developed functionality for GPS tracking and out-of-service logging.
- Contributed to feature requirements FR-02 (Vehicle Management) and FR-03 (GPS Tracking).
- Designed and implemented the DAO and Servlet layers for vehicle and GPS functionality.
- Contributed to use cases and class diagrams related to vehicle and GPS modules.

**Key Deliverables:**
- `VehicleServlet.java`, `GPSLogServlet.java`, `GPSTrackingservlet`
- `VehicleDAO.java`, `VehicleDAOImpl.java` ,`GPSLogDAO.java` , `GPSLogDAOImpl.java`
- `StationDAO.java` , `StationDAOImpl.java` ,`StationDTO.java`
- `GPSTrackingDTO.java`, `GPSTrackingDAO.java` , `GPSTrackingDAOImpl.java`

---

### **Prabhsimran Kaur – Maintenance and Fuel Monitoring**

**Responsibilities:**
- Implemented fuel/energy logging and monitoring features.
- Developed alert logic for predictive maintenance (based on FR-04 and FR-05).
- Designed and implemented DAO and Servlet components for maintenance and fuel tracking.
- Built logic for detecting excessive fuel use and overdue maintenance tasks.

**Key Deliverables:**
- `FuelServlet.java`, `MaintenanceServlet.java`
- `FuelDAO.java`, `MaintenanceDAO.java`
- Alert and monitoring logic

---

### **Zhiru Xie – Reporting, Testing, and Documentation**

**Responsibilities:**
- Developed reporting dashboards including:
  - Maintenance logs
  - Operator performance reports
  - Fuel cost summaries
- Wrote JUnit test cases for all DAO classes.
- Created the final SQL schema, database ERD, and configuration properties file.

**Key Deliverables:**
- `DashboardServlet.java`
- `OperatorPerformanceDAO.java`
- JUnit test suite
- Final ERD and SQL Schema

---

## High-Level Design Document Contributions

**Nabila Msiah**
- Application Architecture  
- Business Architecture – Detailed Design  
- MVC Implementation Explanation  
- Use Case Diagram  
- Final proofreading and structure verification

**Prabhsimran Kaur**
- Deployment Architecture  
- Sign-off Page Formatting

**Zhiru Xie**
- Document Structure and Setup  
- Scope and Stakeholders  
- Initial Design and Project View  
- Formatting of Tables and Table of Figures

**Purnima**
- Data Architecture (ERD, Logical/Physical Models)  
- Security Architecture  
- Manual and Automated (JUnit) Testing

---

## Final Presentation and Packaging
All members collaborated on preparing the final project presentation slides and exporting the documentation in PDF/Power Point format for submission.
