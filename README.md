# Public Transit Fleet Management System (PTFMS)

## Team Member Roles and Task Distribution

---

### **Nabila Msiah – Frontend Development & Authentication Module**

**Responsibilities:**
- Designed and implemented login and registration interfaces using JSP (register.jsp, login.jsp).
- Developed servlet logic for:
  - User registration and authentication.
  - Role-based access control (e.g., operator vs. manager).
  - Secure password.
- Assisted in drafting the use case for user authentication (FR-01).
- Contributed to the UML and class diagram for the `User` model.

**Key Deliverables:**
- `register.jsp`, `login.jsp`
- `AuthServlet.java`, `UserDAO.java`
- Added also `ManagerDashboard.jsp`, `OperatorDashboard.jsp`
- User model representation in UML/Class Diagram

---

### **Purnima – Vehicle Management & GPS Logging**

**Responsibilities:**
- Implemented CRUD operations for vehicle registration.
- Developed functionality for GPS tracking and out-of-service logging.
- Contributed to feature requirements FR-02 (Vehicle Management) and FR-03 (GPS Tracking).
- Designed and implemented the DAO and Servlet layers for vehicle and GPS functionality.
- Contributed to use cases and class diagrams related to vehicle and GPS modules.

**Key Deliverables:**
- `VehicleServlet.java`, `GPSLogServlet.java`
- `VehicleDAO.java`, `GPSLogDAO.java`
- 'StationDAO.java' , 'StationDTO.java'

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
