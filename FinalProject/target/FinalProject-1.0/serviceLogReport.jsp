<%-- 
    Document   : ServiceLogReport
    Created on : Aug 6, 2025, 1:54:56 AM
    Author     : Purnima Purnima 
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Service Log Report</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body { padding-top: 20px; }
        .container { max-width: 1200px; }
        h2 { margin-bottom: 20px; }
        table { font-size: 0.9rem; }
    </style>
</head>
<body>

<div class="container">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Service Log Report</h2>
        <a href="manager.jsp" class="btn btn-secondary">Back to Manager Page</a>
    </div>
    <hr>
    
    <div class="alert alert-info">
        This report displays all vehicle service logs, including breaks and out-of-service events.
    </div>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger" role="alert">
            <strong>Error:</strong> <c:out value="${errorMessage}"/>
        </div>
    </c:if>

    <c:choose>
        <c:when test="${not empty serviceLogs}">
            <div class="table-responsive">
                <table class="table table-striped table-hover table-bordered">
                    <thead class="thead-dark">
                        <tr>
                            <th>Log ID</th>
                            <th>Vehicle Ref</th>
                            <th>Operator Ref</th>
                            <th>Log Type</th>
                            <th>Break Start</th>
                            <th>Break End</th>
                            <th>Notes</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="log" items="${serviceLogs}">
                            <tr>
                                <td><c:out value="${log.id}"/></td>
                                <td><c:out value="${log.vehicleRef}"/></td>
                                <td><c:out value="${log.operatorRef}"/></td>
                                <td><c:out value="${log.logType}"/></td>
                                <td><fmt:formatDate value="${log.breakStart}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty log.breakEnd}">
                                            <fmt:formatDate value="${log.breakEnd}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="text-danger">Ongoing</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td><c:out value="${log.notes}"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:when>
        <c:otherwise>
            <div class="alert alert-warning">
                No service log data found.
            </div>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>