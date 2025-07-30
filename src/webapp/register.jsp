<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!--
    Author       : Nabila Msiah 041146732
    Course       : CST8288 - Group Project - Authentication Module (Nabila Msiah Part)
    Description  : JSP form for user registration. Captures user details such as name, email,
                   password, and role. Submits data to the AuthServlet for processing.
-->
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
</head>
<body>
    <h2>Register</h2>

    <!-- Registration form posts to /auth servlet with action="register" -->
    <form action="auth" method="post">
        <input type="hidden" name="action" value="register">

        <!-- Name input -->
        <label>Name:</label><br>
        <input type="text" name="name" required><br><br>

        <!-- Email input -->
        <label>Email:</label><br>
        <input type="email" name="email" required><br><br>

        <!-- Password input -->
        <label>Password:</label><br>
        <input type="password" name="password" required><br><br>

        <!-- Role selection dropdown -->
        <label>Role:</label><br>
        <select name="role" required>
            <option value="MANAGER">Manager</option>
            <option value="OPERATOR">Operator</option>
        </select><br><br>

        <!-- Submit button -->
        <button type="submit">Register</button>
    </form>

    <br>
    <a href="login.jsp">Already have an account? Login here</a>

    <!-- Display message from servlet (e.g., success/failure) -->
    <%
        String msg = (String) request.getAttribute("msg");
        if (msg != null) {
    %>
        <p style="color:red;"><%= msg %></p>
    <%
        }
    %>
</body>
</html>
