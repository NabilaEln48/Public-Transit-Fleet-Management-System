<%-- 
    Document   : liveTrackingReport
    Created on : Aug 6, 2025, 2:32:06 AM
    Author     : DELL
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Live Tracking Report</title>
    <!-- Use a CDN for Bootstrap for a quick and professional look -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body { padding-top: 20px; }
        .container { max-width: 1200px; }
        h2 { margin-bottom: 20px; }
        table { font-size: 0.9rem; }
        .table-striped tbody tr:nth-of-type(odd) {
            background-color: rgba(0,0,0,.05);
        }
        .report-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="report-header">
        <h2>Live Tracking Report</h2>
        <a href="manager.jsp" class="btn btn-secondary">Back to Manager Page</a>
    </div>
    <hr>
    
    <div class="alert alert-info">
        This report displays all live tracking data logged in the system, sorted by the most recent events.
    </div>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger" role="alert">
            <strong>Error:</strong> <c:out value="${errorMessage}"/>
        </div>
    </c:if>

    <c:choose>
        <c:when test="${not empty liveTrackingLogs}">
            <div class="table-responsive">
                <table class="table table-striped table-hover table-bordered">
                    <thead class="thead-dark">
                        <tr>
                            <th>Log ID</th>
                            <th>Vehicle Reg.</th>
                            <th>Vehicle ID</th>
                            <th>Category</th>
                            <th>Latitude</th>
                            <th>Longitude</th>
                            <th>Linked Station</th>
                            <th>Recorded At</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="log" items="${liveTrackingLogs}">
                            <tr>
                                <td><c:out value="${log.id}"/></td>
                                <td><c:out value="${log.vehicleRegistration}"/></td>
                                <td><c:out value="${log.vehicleRef}"/></td>
                                <td><c:out value="${log.vehicleCategory}"/></td>
                                <td><fmt:formatNumber value="${log.gpsLat}" pattern="#.####"/></td>
                                <td><fmt:formatNumber value="${log.gpsLng}" pattern="#.####"/></td>
                                <td><c:out value="${log.linkedStation}"/></td>
                                <td><fmt:formatDate value="${log.recordedAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:when>
        <c:otherwise>
            <div class="alert alert-warning">
                No live tracking data found in the database.
            </div>
        </c:otherwise>
    </c:choose>
</div>

<!-- Optional JavaScript for Bootstrap -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
