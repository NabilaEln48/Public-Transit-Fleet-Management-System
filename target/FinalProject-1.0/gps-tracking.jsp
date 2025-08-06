<%-- 
    Document   : gps-tracking
    Created on : Aug 5, 2025, 4:46:01?PM
    Author     :Purnima Purnima
--%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>GPS Tracking Dashboard - PTFMS</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .tracking-table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        .tracking-table th, .tracking-table td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
        }
        .tracking-table th {
            background-color: #f2f2f2;
            font-weight: bold;
        }
        .tracking-table tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        .tracking-table tr:hover {
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
        .btn-info { background-color: #17a2b8; color: white; }
        .btn:hover { opacity: 0.8; }
        .alert {
            padding: 12px;
            margin: 10px 0;
            border-radius: 4px;
        }
        .alert-success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .alert-error { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .dashboard-actions {
            margin: 20px 0;
            padding: 15px;
            background-color: #f8f9fa;
            border-radius: 5px;
        }
        .coordinates {
            font-family: monospace;
            font-size: 12px;
        }
        .timestamp {
            font-size: 12px;
            color: #666;
        }
        .vehicle-badge {
            padding: 4px 8px;
            border-radius: 12px;
            font-size: 12px;
            font-weight: bold;
        }
        .badge-bus { background-color: #007bff; color: white; }
        .badge-rail { background-color: #28a745; color: white; }
        .badge-train { background-color: #ffc107; color: black; }
    </style>
</head>
<body>
    <div class="container">
        <header>
            <h1>Public Transit Fleet Management System</h1>
            <nav>
                <a href="index.jsp">Dashboard</a>
                <a href="VehicleServlet?action=list">Vehicles</a>
                <a href="FrontendController?action=GPSTrackingDashboard" class="active">GPS Tracking</a>
                <a href="FrontendController?action=MaintenanceDashboard">Maintenance</a>
                <a href="logout.jsp">Logout</a>
            </nav>
        </header>

        <main>
            <div class="page-header">
                <h2>GPS Tracking Dashboard</h2>
                <div class="dashboard-actions">
                    <a href="FrontendController?action=GPSTrackingDashboard" class="btn btn-primary">Refresh Tracking</a>
                    <a href="FrontendController?action=StationReports" class="btn btn-info">Station Reports</a>
                    <c:if test="${sessionScope.userRole == 'OPERATOR'}">
                        <a href="FrontendController?action=OperatorServiceLogs" class="btn btn-success">My Service Logs</a>
                    </c:if>
                </div>
            </div>

            <!-- Display success/error messages -->
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success">${successMessage}</div>
            </c:if>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-error">${errorMessage}</div>
            </c:if>

            <!-- Live Tracking Data -->
            <div class="tracking-section">
                <h3>Real-Time Vehicle Locations</h3>
                <c:choose>
                    <c:when test="${empty trackingData}">
                        <div class="alert alert-info">
                            <p>No GPS tracking data available. Vehicles may not be transmitting location data.</p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <table class="tracking-table">
                            <thead>
                                <tr>
                                    <th>Vehicle ID</th>
                                    <th>Vehicle Type</th>
                                    <th>GPS Coordinates</th>
                                    <th>Linked Station</th>
                                    <th>Last Updated</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="tracking" items="${trackingData}">
                                    <tr>
                                        <td>
                                            <span class="vehicle-badge 
                                                <c:choose>
                                                    <c:when test="${tracking.vehicleRef.startsWith('BUS')}">badge-bus</c:when>
                                                    <c:when test="${tracking.vehicleRef.startsWith('RAIL')}">badge-rail</c:when>
                                                    <c:when test="${tracking.vehicleRef.startsWith('TRAIN')}">badge-train</c:when>
                                                    <c:otherwise>badge-bus</c:otherwise>
                                                </c:choose>">
                                                ${tracking.vehicleRef}
                                            </span>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${tracking.vehicleRef.startsWith('BUS')}">Bus</c:when>
                                                <c:when test="${tracking.vehicleRef.startsWith('RAIL')}">Light Rail</c:when>
                                                <c:when test="${tracking.vehicleRef.startsWith('TRAIN')}">Train</c:when>
                                                <c:otherwise>Unknown</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td class="coordinates">
                                            Lat: <fmt:formatNumber value="${tracking.gpsLat}" pattern="#0.000000"/><br>
                                            Lng: <fmt:formatNumber value="${tracking.gpsLng}" pattern="#0.000000"/>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty tracking.linkedStation}">
                                                    ${tracking.linkedStation}
                                                </c:when>
                                                <c:otherwise>
                                                    <span style="color: #999;">In Transit</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td class="timestamp">
                                            <fmt:formatDate value="${tracking.recordedAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                        </td>
                                        <td>
                                            <c:if test="${sessionScope.userRole == 'MANAGER'}">
                                                <a href="FrontendController?action=StationReports&vehicleId=${tracking.vehicleRef}" 
                                                   class="btn btn-info">View History</a>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>

            <!-- Quick Actions for Operators -->
            <c:if test="${sessionScope.userRole == 'OPERATOR'}">
                <div class="operator-actions">
                    <h3>Operator Actions</h3>
                    <div class="dashboard-actions">
                        <h4>Log Station Events</h4>
                        <form action="FrontendController" method="post" style="display: inline-block; margin-right: 20px;">
                            <input type="hidden" name="action" value="LogStationEvent">
                            <select name="vehicleId" required>
                                <option value="">Select Vehicle</option>
                                <c:forEach var="tracking" items="${trackingData}">
                                    <option value="${tracking.vehicleRef}">${tracking.vehicleRef}</option>
                                </c:forEach>
                            </select>
                            <select name="stationId" required>
                                <option value="">Select Station</option>
                                <option value="Station1">Downtown Station</option>
                                <option value="Station2">Central Station</option>
                                <option value="Station3">North Station</option>
                            </select>
                            <select name="actionType" required>
                                <option value="">Select Action</option>
                                <option value="ARRIVAL">Arrival</option>
                                <option value="DEPARTURE">Departure</option>
                            </select>
                            <button type="submit" class="btn btn-success">Log Event</button>
                        </form>

                        <h4>Start Break/Service Log</h4>
                        <form action="FrontendController" method="post" style="display: inline-block;">
                            <input type="hidden" name="action" value="StartServiceLog">
                            <select name="vehicleId" required>
                                <option value="">Select Vehicle</option>
                                <c:forEach var="tracking" items="${trackingData}">
                                    <option value="${tracking.vehicleRef}">${tracking.vehicleRef}</option>
                                </c:forEach>
                            </select>
                            <select name="logType" required>
                                <option value="">Select Type</option>
                                <option value="Break">Break</option>
                                <option value="OutOfService">Out of Service</option>
                            </select>
                            <button type="submit" class="btn btn-primary">Start Log</button>
                        </form>
                    </div>
                </div>
            </c:if>

            <!-- Auto-refresh notice -->
            <div class="info-notice">
                <p><strong>Note:</strong> This page shows the latest GPS tracking data. 
                   Click "Refresh Tracking" to get the most current information.</p>
                <p><strong>Current Time:</strong> <span id="currentTime"></span></p>
            </div>
        </main>

        <footer>
            <p>&copy; 2025 Public Transit Fleet Management System</p>
        </footer>
    </div>

    <script>
        // Auto-refresh every 30 seconds for real-time updates
        setTimeout(function() {
            if (confirm('Refresh GPS tracking data?')) {
                window.location.reload();
            }
        }, 30000);

        // Form validation for operator actions
        document.querySelectorAll('form').forEach(function(form) {
            form.addEventListener('submit', function(e) {
                const selects = form.querySelectorAll('select[required]');
                let isValid = true;
                
                selects.forEach(function(select) {
                    if (!select.value) {
                        select.style.borderColor = 'red';
                        isValid = false;
                    } else {
                        select.style.borderColor = '#ddd';
                    }
                });
                
                if (!isValid) {
                    e.preventDefault();
                    alert('Please fill in all required fields.');
                }
            });
        });

        // Real-time clock
        function updateClock() {
            const now = new Date();
            const timeString = now.toLocaleString();
            document.getElementById('currentTime').textContent = timeString;
        }
        
        setInterval(updateClock, 1000);
        updateClock(); // Initial call