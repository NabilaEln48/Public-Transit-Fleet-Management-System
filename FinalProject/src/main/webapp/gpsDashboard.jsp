<%-- 
    Document   : gpsDashboard
    Created on : Aug 5, 2025, 4:46:01?PM
    Author     :Purnima Purnima
--%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GPS Tracking Dashboard - PTFMS</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f0f2f5;
        }

        .navbar-brand {
            font-weight: 700;
        }

        .status-card {
            border: none;
            border-radius: 12px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            transition: all 0.3s ease;
        }

        .status-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
        }

        .status-icon {
            font-size: 2.5rem;
            color: rgba(255, 255, 255, 0.7);
        }

        .card-header {
            background-color: #fff;
            border-bottom: 1px solid #e0e2e5;
            padding: 1.25rem 1.5rem;
            border-top-left-radius: 12px;
            border-top-right-radius: 12px;
        }

        .card {
            border: none;
            border-radius: 12px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
        }

        .timeline-item {
            position: relative;
            padding-left: 25px;
            margin-bottom: 15px;
        }

        .timeline-item::before {
            content: '';
            position: absolute;
            left: 0;
            top: 5px;
            height: 12px;
            width: 12px;
            background-color: #0d6efd;
            border-radius: 50%;
        }

        .timeline-item:not(:last-child)::after {
            content: '';
            position: absolute;
            left: 5px;
            top: 17px;
            bottom: 0;
            width: 2px;
            background-color: #dee2e6;
        }

        .live-indicator {
            animation: pulse 2s infinite;
        }

        @keyframes pulse {
            0% { transform: scale(1); opacity: 1; }
            50% { transform: scale(1.1); opacity: 0.7; }
            100% { transform: scale(1); opacity: 1; }
        }

        .table-hover tbody tr:hover {
            background-color: #f8f9fa;
        }

        .btn-outline-primary {
            color: #0d6efd;
            border-color: #0d6efd;
        }

        .btn-outline-primary:hover {
            color: #fff;
            background-color: #0d6efd;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary shadow-sm">
        <div class="container">
            <a class="navbar-brand" href="index.jsp">
                <i class="fas fa-bus-simple me-2"></i>PTFMS
            </a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="gpsLogForm.jsp">Vehicles</a>
                <a class="nav-link active" href="GPSTrackingServlet?action=dashboard">GPS Tracking</a>
                <c:if test="${sessionScope.userType == 'Manager' || sessionScope.userType == 'Operator'}">
                    <a class="nav-link" href="GPSTrackingServlet?action=log">Log Event</a>
                </c:if>
                <a class="nav-link" href="operator.jsp">Home</a>
                 
            </div>
        </div>
    </nav>

    <div class="container-fluid mt-5">
        <div class="row mb-4">
            <div class="col-12">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h2 class="fw-bold text-dark"><i class="fas fa-satellite-dish me-2"></i>GPS Tracking Dashboard</h2>
                        <p class="text-muted mb-0">Real-time vehicle monitoring and location tracking for your fleet.</p>
                    </div>
                    <div class="d-flex gap-2">
                        <c:if test="${sessionScope.userType == 'Manager' || sessionScope.userType == 'Operator'}">
                            <div class="dropdown">
                                <button class="btn btn-success dropdown-toggle" type="button" data-bs-toggle="dropdown">
                                    <i class="fas fa-plus"></i> Log Event
                                </button>
                                <ul class="dropdown-menu dropdown-menu-end">
                                    <li><a class="dropdown-item" href="GPSTrackingServlet?action=log&type=location"><i class="fas fa-map-marker-alt"></i> Location Update</a></li>
                                    <li><a class="dropdown-item" href="GPSTrackingServlet?action=log&type=station"><i class="fas fa-train"></i> Station Event</a></li>
                                    <li><a class="dropdown-item" href="GPSTrackingServlet?action=log&type=break"><i class="fas fa-coffee"></i> Break Event</a></li>
                                </ul>
                            </div>
                        </c:if>
                        <button class="btn btn-outline-primary" onclick="refreshDashboard()">
                            <i class="fas fa-sync-alt"></i> Refresh
                        </button>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="row g-4 mb-4">
            <div class="col-lg-3 col-md-6">
                <div class="card status-card bg-primary text-white">
                    <div class="card-body d-flex justify-content-between align-items-center">
                        <div>
                            <h4 class="card-title fw-bold">${activeVehicles.size()}</h4>
                            <p class="card-text mb-0">Active Vehicles</p>
                        </div>
                        <i class="fas fa-truck-fast status-icon live-indicator"></i>
                    </div>
                </div>
            </div>
            
            <div class="col-lg-3 col-md-6">
                <div class="card status-card bg-info text-white">
                    <div class="card-body d-flex justify-content-between align-items-center">
                        <div>
                            <h4 class="card-title fw-bold">${recentTracking.size()}</h4>
                            <p class="card-text mb-0">Recent Updates</p>
                        </div>
                        <i class="fas fa-satellite-dish status-icon"></i>
                    </div>
                </div>
            </div>

            <div class="col-lg-3 col-md-6">
                <div class="card status-card bg-warning text-white">
                    <div class="card-body d-flex justify-content-between align-items-center">
                        <div>
                            <c:set var="onBreakCount" value="0" />
                            <c:forEach var="tracking" items="${recentTracking}">
                                <c:if test="${tracking.eventType == 'Break Start'}">
                                    <c:set var="onBreakCount" value="${onBreakCount + 1}" />
                                </c:if>
                            </c:forEach>
                            <h4 class="card-title fw-bold">${onBreakCount}</h4>
                            <p class="card-text mb-0">On Break</p>
                        </div>
                        <i class="fas fa-mug-saucer status-icon"></i>
                    </div>
                </div>
            </div>

            <div class="col-lg-3 col-md-6">
                <div class="card status-card bg-danger text-white">
                    <div class="card-body d-flex justify-content-between align-items-center">
                        <div>
                            <c:set var="outOfServiceCount" value="0" />
                            <c:forEach var="tracking" items="${recentTracking}">
                                <c:if test="${tracking.eventType == 'Out of Service'}">
                                    <c:set var="outOfServiceCount" value="${outOfServiceCount + 1}" />
                                </c:if>
                            </c:forEach>
                            <h4 class="card-title fw-bold">${outOfServiceCount}</h4>
                            <p class="card-text mb-0">Out of Service</p>
                        </div>
                        <i class="fas fa-wrench status-icon"></i>
                    </div>
                </div>
            </div>
        </div>

        <div class="row g-4">
            <div class="col-lg-8">
                <div class="card">
                    <div class="card-header d-flex justify-content-between align-items-center">
                        <h5 class="card-title mb-0 fw-bold">
                            <i class="fas fa-map-location-dot me-2"></i>Live Fleet Status
                        </h5>
                        <small class="text-muted">Last updated: <span id="lastUpdate"><fmt:formatDate value="<%=new java.util.Date()%>" pattern="HH:mm:ss"/></span></small>
                    </div>
                    <div class="card-body p-0">
                        <c:choose>
                            <c:when test="${empty activeVehicles}">
                                <div class="text-center py-5">
                                    <i class="fas fa-location-crosshairs fa-4x text-muted mb-3"></i>
                                    <h6 class="text-muted">No active vehicles currently transmitting data.</h6>
                                    <p class="text-muted small">Please check back later or refresh the dashboard.</p>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="table-responsive">
                                    <table class="table table-hover align-middle mb-0">
                                        <thead class="table-light">
                                            <tr>
                                                <th class="p-3">Vehicle</th>
                                                <th class="p-3">Current Location</th>
                                                <th class="p-3">Status</th>
                                                <th class="p-3">Last Update</th>
                                                <th class="p-3">Actions</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="vehicle" items="${activeVehicles}">
                                                <tr>
                                                    <td class="p-3">
                                                        <div class="d-flex align-items-center">
                                                            <div class="flex-shrink-0 me-3">
                                                                <i class="fas fa-bus text-secondary fa-lg"></i>
                                                            </div>
                                                            <div>
                                                                <strong class="d-block">${vehicle.registrationNumber}</strong>
                                                                <small class="text-muted">ID: ${vehicle.vehicleId}</small>
                                                            </div>
                                                        </div>
                                                    </td>
                                                    <td class="p-3">
                                                        <c:choose>
                                                            <c:when test="${not empty vehicle.routeName}">
                                                                <div class="d-flex align-items-center">
                                                                    <i class="fas fa-route text-primary me-2"></i>
                                                                    <span>${vehicle.routeName}</span>
                                                                </div>
                                                                <c:if test="${not empty vehicle.stationName}">
                                                                    <small class="text-muted d-block ms-4">
                                                                        <i class="fas fa-map-marker-alt me-1"></i> ${vehicle.stationName}
                                                                    </small>
                                                                </c:if>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="text-muted fst-italic">Location unknown</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td class="p-3">
                                                        <c:choose>
                                                            <c:when test="${vehicle.eventType == 'Location Update'}">
                                                                <span class="badge rounded-pill bg-success-subtle text-success">
                                                                    <i class="fas fa-circle live-indicator me-1"></i> Active
                                                                </span>
                                                            </c:when>
                                                            <c:when test="${vehicle.eventType == 'Break Start'}">
                                                                <span class="badge rounded-pill bg-warning-subtle text-warning">
                                                                    <i class="fas fa-coffee me-1"></i> On Break
                                                                </span>
                                                            </c:when>
                                                            <c:when test="${vehicle.eventType == 'Out of Service'}">
                                                                <span class="badge rounded-pill bg-danger-subtle text-danger">
                                                                    <i class="fas fa-tools me-1"></i> Out of Service
                                                                </span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="badge rounded-pill bg-secondary-subtle text-secondary">${vehicle.eventType}</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td class="p-3">
                                                        <small class="text-muted">
                                                            <fmt:formatDate value="${vehicle.timestamp}" pattern="MM/dd HH:mm"/>
                                                        </small>
                                                    </td>
                                                    <td class="p-3">
                                                        <div class="btn-group btn-group-sm">
                                                            <a href="GPSTrackingServlet?action=track&vehicleId=${vehicle.vehicleId}" class="btn btn-outline-primary" title="Track Vehicle">
                                                                <i class="fas fa-search"></i>
                                                            </a>
                                                            <a href="VehicleServlet?action=view&id=${vehicle.vehicleId}" class="btn btn-outline-secondary" title="Vehicle Details">
                                                                <i class="fas fa-info"></i>
                                                            </a>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>

            <div class="col-lg-4">
                <div class="card">
                    <div class="card-header">
                        <h5 class="card-title mb-0 fw-bold">
                            <i class="fas fa-clock-rotate-left me-2"></i>Recent Activity
                        </h5>
                    </div>
                    <div class="card-body p-4" style="max-height: 600px; overflow-y: auto;">
                        <c:choose>
                            <c:when test="${empty recentTracking}">
                                <div class="text-center py-5">
                                    <i class="fas fa-history fa-4x text-muted mb-3"></i>
                                    <p class="text-muted">No recent activity to display.</p>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="timeline">
                                    <c:forEach var="tracking" items="${recentTracking}" varStatus="status">
                                        <div class="timeline-item d-flex align-items-start mb-3">
                                            <div class="flex-shrink-0 me-3 mt-1">
                                                <c:choose>
                                                    <c:when test="${tracking.eventType == 'Location Update'}">
                                                        <div class="badge bg-primary rounded-pill p-2"><i class="fas fa-map-marker-alt"></i></div>
                                                    </c:when>
                                                    <c:when test="${tracking.eventType == 'Station Arrival'}">
                                                        <div class="badge bg-success rounded-pill p-2"><i class="fas fa-sign-in-alt"></i></div>
                                                    </c:when>
                                                    <c:when test="${tracking.eventType == 'Station Departure'}">
                                                        <div class="badge bg-info rounded-pill p-2"><i class="fas fa-sign-out-alt"></i></div>
                                                    </c:when>
                                                    <c:when test="${tracking.eventType == 'Break Start'}">
                                                        <div class="badge bg-warning rounded-pill p-2"><i class="fas fa-coffee"></i></div>
                                                    </c:when>
                                                    <c:when test="${tracking.eventType == 'Break End'}">
                                                        <div class="badge bg-success rounded-pill p-2"><i class="fas fa-play"></i></div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="badge bg-secondary rounded-pill p-2"><i class="fas fa-circle"></i></div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                            <div class="flex-grow-1">
                                                <div class="d-flex justify-content-between align-items-center">
                                                    <strong class="text-primary">${tracking.vehicleNumber}</strong>
                                                    <small class="text-muted"><fmt:formatDate value="${tracking.timestamp}" pattern="HH:mm"/></small>
                                                </div>
                                                <div class="small text-dark fw-light">${tracking.eventType}</div>
                                                <c:if test="${not empty tracking.stationName}">
                                                    <div class="small text-muted">
                                                        <i class="fas fa-map-marker-alt me-1"></i>${tracking.stationName}
                                                    </div>
                                                </c:if>
                                                <c:if test="${not empty tracking.notes}">
                                                    <div class="small text-secondary fst-italic mt-1">${tracking.notes}</div>
                                                </c:if>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="card-footer text-center bg-light">
                        <a href="GPSTrackingServlet?action=history" class="btn btn-sm btn-outline-primary">
                            <i class="fas fa-history me-1"></i> View Full History
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        let autoRefreshInterval;
        
        function startAutoRefresh() {
            autoRefreshInterval = setInterval(function() {
                refreshDashboard();
            }, 30000); // 30 seconds
        }

        function refreshDashboard() {
            document.getElementById('lastUpdate').textContent = new Date().toLocaleTimeString();
            location.reload();
        }

        function exportData() {
            alert('Export functionality would be implemented here.');
        }

        document.addEventListener('DOMContentLoaded', function() {
            startAutoRefresh();
        });

        document.addEventListener('visibilitychange', function() {
            if (document.hidden) {
                clearInterval(autoRefreshInterval);
            } else {
                startAutoRefresh();
            }
        });
    </script>
</body>
</html>