<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String role = (String) session.getAttribute("userType");
    if (role == null || (!role.equalsIgnoreCase("operator") && !role.equalsIgnoreCase("manager"))) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Operator Dashboard</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f2f4f7;
        }

        .container {
            max-width: 900px;
            margin: 50px auto;
            padding: 30px;
            background-color: #ffffff;
            border-radius: 12px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.1);
        }

        h1 {
            text-align: center;
            color: #2c3e50;
        }

        .section {
            margin-top: 40px;
        }

        .section h2 {
            font-size: 18px;
            color: #34495e;
            border-bottom: 1px solid #ddd;
            padding-bottom: 5px;
        }

        .card-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
            gap: 20px;
            margin-top: 20px;
        }

        .card {
            background-color: #f9fafb;
            border-radius: 8px;
            padding: 20px;
            text-align: center;
            transition: 0.3s;
            border: 1px solid #e1e4e8;
        }

        .card:hover {
            background-color: #eef1f5;
            box-shadow: 0 4px 10px rgba(0,0,0,0.05);
        }

        .card a {
            text-decoration: none;
            color: #007bff;
            font-weight: 500;
        }

        .logout-link {
            display: block;
            text-align: center;
            margin-top: 40px;
            font-size: 14px;
        }

        .logout-link a {
            color: #c0392b;
            text-decoration: none;
        }

        .logout-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Welcome, <%= role.substring(0, 1).toUpperCase() + role.substring(1).toLowerCase() %>!</h1>

        <% if ("operator".equalsIgnoreCase(role)) { %>
            <div class="section">
                <h2>Operator Actions</h2>
                <div class="card-grid">
                    <div class="card"><a href="${ctx}/logGPS.jsp">Log GPS Data</a></div>
                    <div class="card"><a href="${ctx}/declareOutOfService.jsp">Declare Out-of-Service</a></div>
                    <div class="card"><a href="${ctx}/logFuelConsumption.jsp">Log Fuel Consumption</a></div>
                    <div class="card"><a href="${ctx}/viewReports.jsp">View Reports</a></div>
                </div>
            </div>
        <% } else if ("manager".equalsIgnoreCase(role)) { %>
            <div class="section">
                <h2>Manager Access</h2>
                <div class="card-grid">
                    <div class="card"><a href="${ctx}/viewReports.jsp">View Reports</a></div>
                    <div class="card"><a href="${ctx}/viewGPSLogs.jsp">View GPS Logs</a></div>
                    <div class="card"><a href="${ctx}/fuelConsumptionSummary.jsp">Fuel Consumption Summary</a></div>
                    <div class="card"><a href="${ctx}/maintenanceOverview.jsp">Maintenance Overview</a></div>
                </div>
            </div>
        <% } %>

        <div class="logout-link">
            <a href="${ctx}/logout">Logout</a>
        </div>
    </div>
</body>
</html>
