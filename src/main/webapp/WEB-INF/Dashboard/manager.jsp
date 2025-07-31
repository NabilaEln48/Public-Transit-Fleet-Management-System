<%-- 
    Author : Nabila Msiah 041146732
    Course        : CST8288 - Group Project - Authentication Module (Nabila Msiah Part)
    Description   : Operator dashboard view. Redirects to login.jsp if the logged-in user is neither an operator nor a manager.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Get the user's role from the session
    String role = (String) session.getAttribute("role");

    // Redirect to login if not operator or manager
    if (role == null || (!role.equals("operator") && !role.equals("manager"))) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Operator Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f6f6f6;
            padding: 20px;
        }
        h1 {
            color: #333;
        }
        ul {
            list-style-type: none;
            padding: 0;
        }
        li {
            margin: 10px 0;
        }
        a {
            text-decoration: none;
            color: #0077cc;
        }
    </style>
</head>
<body>
    <h1>Welcome, <%= role.substring(0, 1).toUpperCase() + role.substring(1) %>!</h1>

    <% if ("operator".equals(role)) { %>
        <p>You have access to operator functionalities:</p>
        <ul>
            <li><a href="logGPS.jsp">Log GPS Data</a></li>
            <li><a href="declareOutOfService.jsp">Declare Out-of-Service</a></li>
            <li><a href="logFuelConsumption.jsp">Log Fuel Consumption</a></li>
            <li><a href="viewReports.jsp">View Reports</a></li>
        </ul>
    <% } else if ("manager".equals(role)) { %>
        <p>You are logged in as a Manager. You may view or supervise operator actions:</p>
        <ul>
            <li><a href="viewReports.jsp">View Reports</a></li>
            <li><a href="viewGPSLogs.jsp">View GPS Logs</a></li>
            <li><a href="fuelConsumptionSummary.jsp">View Fuel Consumption</a></li>
            <li><a href="maintenanceOverview.jsp">View Out-of-Service Logs</a></li>
        </ul>
    <% } %>

    <br><a href="logout.jsp">Logout</a>
</body>
</html>