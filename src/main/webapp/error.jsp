<%-- 
    Document   : error
    Created on : Aug 5, 2025, 4:46:01?PM
    Author     : DELL
--%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error</title>
</head>
<body>
    <h1>An Error Occurred</h1>
    <p>We're sorry, an unexpected error has prevented your request from being fulfilled.</p>
    
    <%-- This line retrieves and displays the error message set by your servlet --%>
    <% 
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) { 
    %>
        <p><strong>Details:</strong> <%= errorMessage %></p>
    <%
        }
    %>
    
    <p><a href="GPSTrackingServlet?action=dashboard">Return to Dashboard</a></p>
</body>
</html>