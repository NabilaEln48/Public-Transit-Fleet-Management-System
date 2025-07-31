<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<!--
    Author       : Nabila Msiah 041146732
    Course       : CST8288 - Group Project - Authentication Module (Nabila Msiah Part)
    Description  : Login page for users of the Public Transit Fleet Management System.
                   Submits login credentials to the AuthServlet with action=login.
-->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - Public Transit Fleet Management System</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }

        .login-container {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            padding: 40px;
            width: 400px;
        }

        h1 {
            color: #2c3e50;
            margin-top: 0;
            text-align: center;
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #7f8c8d;
        }

        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }

        .btn {
            display: inline-block;
            background-color: #3498db;
            color: white;
            padding: 12px 24px;
            text-decoration: none;
            border-radius: 4px;
            font-weight: bold;
            border: none;
            cursor: pointer;
            width: 100%;
        }

        .btn:hover {
            background-color: #2980b9;
        }

        .error {
            color: red;
            margin-bottom: 15px;
            text-align: center;
        }

        .success {
            color: green;
            margin-bottom: 15px;
            text-align: center;
        }

        .register-link {
            text-align: center;
            margin-top: 20px;
        }
    </style>
</head>
<body>
<div class="login-container">
    <h1>Login</h1>

    <!-- Error or general message from servlet -->
    <c:if test="${not empty msg}">
        <div class="error">${msg}</div>
    </c:if>

    <!-- Optional success message (e.g., after registration) -->
    <c:if test="${not empty success}">
        <div class="success">${success}</div>
    </c:if>

    <!-- Login form sends action=login to /auth servlet -->
    <form action="${pageContext.request.contextPath}/auth" method="post">
        <input type="hidden" name="action" value="login">

        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" id="email" name="email" required>
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required>
        </div>

        <button type="submit" class="btn">Login</button>
    </form>

    <div class="register-link">
        <p>Don't have an account? <a href="/src/main/webapp/WEB-INF/Auth/register.jsp">Register</a></p>
    </div>
</div>
</body>
</html>
