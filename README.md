# Public Transit Fleet Management System (PTFMS)

## Team Member Roles and Task Distribution

---

### **Nabila Msiah – Frontend Development & Authentication Module**

**Responsibilities:**

The following tasks were assigned and implemented as part of my contribution to the project:

Design and implement login/register HTML forms

Implement servlet logic for:

User registration

Authentication & role-based access

Secure password hashing (SHA-256)

Help write Use Case for FR-01 (User Auth)

**Key Deliverables:**
- `AuthServlet.java`, `LoginServlet.java`, `FrontControllerservlet.java`
- `index.html`, `register.jsp` ,`login.jsp` , `managerdashboard.jsp` , `Operatordashboard.jsp`
- `UserBusinessLogic.java` , `UserDAO.java` ,`UserDAOImpl.java` , `UserDTO.java`
- `DataSource.java`, `Web.xml`



---

### **Purnima – Vehicle Management & GPS Logging**

**Responsibilities:**
- Implemented CRUD operations for vehicle registration.
- Developed functionality for GPS tracking and out-of-service logging.
- Contributed to feature requirements FR-02 (Vehicle Management) and FR-03 (GPS Tracking).
- Designed and implemented the DAO and Servlet layers for vehicle and GPS functionality.
- Contributed to use cases and class diagrams related to vehicle and GPS modules.

**Key Deliverables:**
- `GPSLogServlet.java`, `GPSTrackingservlet`
- `GPSLogDAO.java` , `GPSLogDAOImpl.java`
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
- database.sql 
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
- `VehicleServlet.java`, `VehicleDAO.java`, `VehicleDAOImpl.java`
- databse.sql
- Helped in Maintenance
  
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
