<%-- 
    Document   : service-logs
    Created on : Aug 4, 2025, 4:32:12 PM
    Author     : Purnima Purnima
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Service Logs</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h2>My Service Logs</h2>
        
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
        
        <!-- Start New Service Log -->
        <div class="card mb-3">
            <div class="card-header">
                <h5>Log Break or Out-of-Service</h5>
            </div>
            <div class="card-body">
                <form method="post" action="FrontendController">
                    <input type="hidden" name="action" value="StartServiceLog">
                    <div class="row">
                        <div class="col-md-4">
                            <label for="vehicleId" class="form-label">Vehicle ID:</label>
                            <input type="text" class="form-control" id="vehicleId" name="vehicleId" required>
                        </div>
                        <div class="col-md-4">
                            <label for="logType" class="form-label">Type:</label>
                            <select class="form-control" id="logType" name="logType" required>
                                <option value="">Select Type</option>
                                <option value="Break">Break</option>
                                <option value="OutOfService">Out of Service</option>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">&nbsp;</label>
                            <button type="submit" class="btn btn-primary d-block">Start Log</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        
        <!-- Service Logs History -->
        <div class="card">
            <div class="card-header">
                <h5>Service Log History</h5>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>Vehicle</th>
                                <th>Registration</th>
                                <th>Type</th>
                                <th>Start Time</th>
                                <th>End Time</th>
                                <th>Duration</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="log" items="${serviceLogs}">
                                <tr>
                                    <td>${log.vehicleRef}</td>
                                    <td>${log.vehicleRegistration}</td>
                                    <td>
                                        <span class="badge ${log.logType == 'Break' ? 'bg-info' : 'bg-warning'}">
                                            ${log.logType}
                                        </span>
                                    </td>
                                    <td>${log.breakStart}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${log.breakEnd != null}">
                                                ${log.breakEnd}
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted">In Progress</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${log.breakEnd != null}">
                                                <!-- Calculate duration would need JSP functions or server-side calculation -->
                                                Completed
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-primary">Active</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:if test="${log.breakEnd == null}">
                                            <form method="post" action="FrontendController" class="d-inline">
                                                <input type="hidden" name="action" value="EndServiceLog">
                                                <input type="hidden" name="logId" value="${log.id}">
                                                <button type="submit" class="btn btn-sm btn-success">End Log</button>
                                            </form>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        
        <div class="mt-3">
            <a href="operator.jsp" class="btn btn-primary">Back to Dashboard</a>
        </div>
    </div>
</body>
</html>