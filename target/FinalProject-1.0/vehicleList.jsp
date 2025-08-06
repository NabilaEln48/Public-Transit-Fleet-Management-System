<%-- 
    Document   : vehicleList
    Created on : Aug 5, 2025, 12:09:49 AM
    Author     : Purnima Purnima
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Vehicle Management - PTFMS</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .vehicle-table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        .vehicle-table th, .vehicle-table td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }
        .vehicle-table th {
            background-color: #f2f2f2;
            font-weight: bold;
        }
        .vehicle-table tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        .vehicle-table tr:hover {
            background-color: #f5f5f5;
        }
        .btn {
            padding: 8px 16px;
            margin: 2px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
        }
        .btn-primary { background-color: #007bff; color: white; }
        .btn-success { background-color: #28a745; color: white; }
        .btn-warning { background-color: #ffc107; color: black; }
        .btn-danger { background-color: #dc3545; color: white; }
        .btn:hover { opacity: 0.8; }
        .status-active { color: #28a745; font-weight: bold; }
        .status-maintenance { color: #ffc107; font-weight: bold; }
        .status-inactive { color: #dc3545; font-weight: bold; }
        .alert {
            padding: 12px;
            margin: 10px 0;
            border-radius: 4px;
        }
        .alert-success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .alert-error { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
    </style>
</head>
<body>
    <div class="container">
        <header>
            <h1>Public Transit Fleet Management System</h1>
            <nav>
                <a href="index.jsp">Dashboard</a>
                <a href="VehicleServlet?action=list" class="active">Vehicles</a>
                <a href="FrontendController?action=GPSTrackingDashboard">GPS Tracking</a>
                <a href="FrontendController?action=MaintenanceDashboard">Maintenance</a>
                <a href="logout.jsp">Logout</a>
            </nav>
        </header>

        <main>
            <div class="page-header">
                <h2>Vehicle Management</h2>
                <a href="VehicleServlet?action=add" class="btn btn-success">Add New Vehicle</a>
            </div>

            <!-- Display success/error messages -->
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success">${successMessage}</div>
            </c:if>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-error">${errorMessage}</div>
            </c:if>

            <!-- Vehicle Statistics Summary -->
            <div class="stats-summary">
                <div class="stat-card">
                    <h3>Total Vehicles</h3>
                    <p class="stat-number">${vehicles.size()}</p>
                </div>
            </div>

            <!-- Vehicle List Table -->
            <div class="table-container">
                <c:choose>
                    <c:when test="${empty vehicles}">
                        <div class="alert alert-info">
                            <p>No vehicles found. <a href="VehicleServlet?action=add">Add the first vehicle</a></p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <table class="vehicle-table">
                            <thead>
                                <tr>
                                    <th>Vehicle ID</th>
                                    <th>Vehicle Number</th>
                                    <th>Category</th>
                                    <th>Registration</th>
                                    <th>Fuel Type</th>
                                    <th>Efficiency Rate</th>
                                    <th>Capacity</th>
                                    <th>Assigned Route</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="vehicle" items="${vehicles}">
                                    <tr>
                                        <td>${vehicle.id}</td>
                                        <td>${vehicle.vehicleNumber}</td>
                                        <td>${vehicle.category}</td>
                                        <td>${vehicle.registrationNumber}</td>
                                        <td>${vehicle.fuelUsed}</td>
                                        <td><fmt:formatNumber value="${vehicle.efficiencyRate}" pattern="#0.00"/></td>
                                        <td>${vehicle.capacity}</td>
                                        <td>${vehicle.assignedRoute}</td>
                                        <td>
                                            <span class="status-${vehicle.operationalState.toLowerCase()}">
                                                ${vehicle.operationalState}
                                            </span>
                                        </td>
                                        <td>
                                            <a href="VehicleServlet?action=view&id=${vehicle.id}" class="btn btn-primary">View</a>
                                            <a href="VehicleServlet?action=edit&id=${vehicle.id}" class="btn btn-warning">Edit</a>
                                            <a href="VehicleServlet?action=delete&id=${vehicle.id}" 
                                               class="btn btn-danger"
                                               onclick="return confirm('Are you sure you want to delete this vehicle?')">Delete</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>

            <!-- Filter Options -->
            <div class="filter-section">
                <h3>Filter Vehicles</h3>
                <div class="filter-buttons">
                    <a href="VehicleServlet?action=list" class="btn btn-primary">All Vehicles</a>
                    <a href="VehicleServlet?action=list&category=DieselBus" class="btn btn-primary">Diesel Buses</a>
                    <a href="VehicleServlet?action=list&category=ElectricLightRail" class="btn btn-primary">Electric Rail</a>
                    <a href="VehicleServlet?action=list&category=DieselElectricTrain" class="btn btn-primary">Diesel Trains</a>
                </div>
                <div class="filter-buttons">
                    <a href="VehicleServlet?action=list&state=Active" class="btn btn-success">Active Only</a>
                    <a href="VehicleServlet?action=list&state=Maintenance" class="btn btn-warning">In Maintenance</a>
                    <a href="VehicleServlet?action=list&state=Inactive" class="btn btn-danger">Inactive</a>
                </div>
            </div>
        </main>

        <footer>
            <p>&copy; 2025 Public Transit Fleet Management System</p>
        </footer>
    </div>
</body>
</html>