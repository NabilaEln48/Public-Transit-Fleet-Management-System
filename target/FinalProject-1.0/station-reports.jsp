<%-- 
    Document   : station-reports
    Created on : Aug 4, 2025, 4:31:20 PM
    Author     : Purnima
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Station Arrival/Departure Reports</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h2>Station Arrival/Departure Reports</h2>
        
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success" role="alert">
                ${successMessage}
            </div>
        </c:if>
        
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger" role="alert">
                ${errorMessage}
            </div>
        </c:if>
        
        <!-- Filter Form -->
        <div class="card mb-3">
            <div class="card-body">
                <form method="get" action="FrontendController">
                    <input type="hidden" name="action" value="StationReports">
                    <div class="row">
                        <div class="col-md-4">
                            <label for="vehicleId" class="form-label">Filter by Vehicle ID:</label>
                            <input type="text" class="form-control" id="vehicleId" name="vehicleId" 
                                   value="${selectedVehicle}" placeholder="Enter Vehicle ID">
                        </div>
                        <div class="col-md-2">
                            <label class="form-label">&nbsp;</label>
                            <button type="submit" class="btn btn-primary d-block">Filter</button>
                        </div>
                        <div class="col-md-2">
                            <label class="form-label">&nbsp;</label>
                            <a href="FrontendController?action=StationReports" class="btn btn-secondary d-block">Clear</a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        
        <!-- Station Logs Table -->
        <div class="card">
            <div class="card-header">
                <h5>Station Events Log</h5>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>Vehicle</th>
                                <th>Registration</th>
                                <th>Station</th>
                                <th>Action</th>
                                <th>Time</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="log" items="${stationLogs}">
                                <tr>
                                    <td>${log.vehicleRef}</td>
                                    <td>${log.vehicleRegistration}</td>
                                    <td>${log.stationName}</td>
                                    <td>
                                        <span class="badge ${log.actionType == 'ARRIVAL' ? 'bg-success' : 'bg-warning'}">
                                            ${log.actionType}
                                        </span>
                                    </td>
                                    <td>${log.loggedTime}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        
        <div class="mt-3">
            <a href="FrontendController?action=GPSTrackingDashboard" class="btn btn-secondary">GPS Tracking</a>
            <a href="manager.jsp" class="btn btn-primary">Back to Dashboard</a>
        </div>
    </div>
</body>
</html>
