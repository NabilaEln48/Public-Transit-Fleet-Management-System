<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c" %> <% String role = (String) session.getAttribute("userType"); if
(role == null || !role.equalsIgnoreCase("manager")) {
response.sendRedirect("login.jsp"); return; } %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Manager Dashboard</title>
    <!-- (header remains unchanged, only style section modified) -->
    <style>
      body {
        margin: 0;
        padding: 0;
        font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
        background-color: #121212; /* BLACK BACKGROUND */
        color: #f5f5f5; /* LIGHT TEXT */
      }

      .navbar {
        background-color: #1e1e1e;
        color: #ffffff;
        padding: 15px 30px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        font-size: 16px;
      }

      .navbar .app-title {
        font-weight: bold;
      }

      .navbar .user-role {
        font-style: italic;
      }

      .navbar .logout-btn {
        color: white;
        text-decoration: none;
        padding: 6px 12px;
        background-color: #c0392b;
        border-radius: 4px;
        font-weight: 500;
      }

      .navbar .logout-btn:hover {
        background-color: #e74c3c;
      }

      .container {
        max-width: 900px;
        margin: 40px auto;
        padding: 30px;
        background-color: #1e1e1e;
        border-radius: 12px;
        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.4);
      }

      h1 {
        text-align: center;
        color: #ffffff;
      }

      .section {
        margin-top: 40px;
      }

      .section h2 {
        font-size: 18px;
        color: #dddddd;
        border-bottom: 1px solid #444;
        padding-bottom: 5px;
      }

      .card-grid {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
        gap: 20px;
        margin-top: 20px;
      }

      .card {
        background-color: #2c2c2c;
        border-radius: 8px;
        padding: 20px;
        text-align: center;
        transition: 0.3s;
        border: 1px solid #3a3a3a;
      }

      .card:hover {
        background-color: #3a3a3a;
        box-shadow: 0 4px 10px rgba(255, 255, 255, 0.05);
      }

      .card a {
        text-decoration: none;
        color: #61dafb;
        font-weight: 500;
      }
    </style>
  </head>
  <body>
    <!-- Navigation Bar -->
    <nav class="navbar">
      <div class="app-title">PTFMS - Manager Dashboard</div>
      <div class="user-role">Logged in as: <%= role %></div>
      <a class="logout-btn" href="${ctx}/logout">Logout</a>
    </nav>

    <!-- Main Content -->
    <div class="container">
      <h1>Welcome, Manager!</h1>

      <div class="section">
        <h2>Administrative Tools</h2>
        <div class="card-grid">
          <div class="card">
            <a href="${ctx}/VehicleManagement">Manage Vehicles</a>
          </div>
          <div class="card">
            <a href="${ctx}/ReportDashboard">View Reports</a>
          </div>
          <div class="card">
            <a href="${ctx}/ScheduleMaintenance">Schedule Maintenance</a>
          </div>
          <div class="card">
            <a href="GPSTrackingServlet?action=viewServiceLogReport">View Service Log Report</a>
          </div>
          <div class="card">
            <a href="GPSTrackingServlet?action=viewLiveTrackingReport">View Live Tracking Report</a>
          </div>
          <div class="card">
            <a href="GPSTrackingServlet?action=viewTransitVehiclesReport">View Transit Vehicles Report</a>
          </div>
          <div class="card">
            <a href="${ctx}/FrontendController?action=ViewAlerts"
              >View Fuel Alerts</a
            >
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
