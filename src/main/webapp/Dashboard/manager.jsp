<%-- 
    Author : Nabila Msiah 041146732
    Course : CST8288 - Group Project - Authentication Module (Nabila Msiah Part)
    Description : Manager dashboard view. Redirects to login.jsp if user is not a manager.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String role = (String) session.getAttribute("userType");

    if (role == null || !role.equalsIgnoreCase("manager")) {
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
    <h1>Welcome, Manager!</h1>

    <p>You may view or supervise operator actions:</p>
    <ul>
        <a href="${pageContext.request.contextPath}/FrontendController?action=ReportDashboard">View Reports</a>
        <li><a href="viewGPSLogs.jsp">View GPS Logs</a></li>
        <li><a href="fuelConsumptionSummary.jsp">View Fuel Consumption</a></li>
        <li><a href="maintenanceOverview.jsp">View Out-of-Service Logs</a></li>
    </ul>

    <br><a href="${pageContext.request.contextPath}/logout">Logout</a>
</body>
</html>
