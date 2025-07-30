<%-- 
    Author : Nabila Msiah 041146732
    Course        : CST8288 - Group Project - Authentication Module (Nabila Msiah Part)
    Description   : Manager dashboard view. Redirects to login.jsp if the logged-in user is not a manager.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Get the user's role from the session
    String role = (String) session.getAttribute("role");

    // Redirect to login if the user is not a manager
    if (role == null || !role.equals("manager")) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manager Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #eef3f7;
            padding: 20px;
        }
        h1 {
            color: #222;
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
    <h1>Welcome, Manager!</h1>
    <p>Here are your management options:</p>

    <ul>
        <li><a href="manageVehicles.jsp">Manage Vehicles</a></li>
        <li><a href="scheduleMaintenance.jsp">Schedule Maintenance</a></li>
        <li><a href="viewReports.jsp">View Reports</a></li>
        <li><a href="fuelAlerts.jsp">View Fuel Alerts</a></li>
        <li><a href="registration.jsp">Register New User</a></li>
    </ul>

    <br><a href="logout.jsp">Logout</a>
</body>
</html>
