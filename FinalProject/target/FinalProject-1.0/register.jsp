<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register - Public Transit Fleet Management System</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #121212;
            color: #ffffff;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }

        .form-container {
            background-color: #1e1e1e;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.5);
            width: 400px;
        }

        h2 {
            text-align: center;
            color: #61dafb;
            margin-bottom: 25px;
        }

        label {
            display: block;
            margin-top: 12px;
            margin-bottom: 4px;
            font-size: 14px;
            color: #dddddd;
        }

        input,
        select {
            width: 100%;
            padding: 10px;
            border: 1px solid #444;
            background-color: #2c2c2c;
            color: #ffffff;
            border-radius: 4px;
            box-sizing: border-box;
        }

        input:focus,
        select:focus {
            outline: none;
            border-color: #61dafb;
        }

        button {
            width: 100%;
            margin-top: 20px;
            padding: 12px;
            background-color: #3498db;
            color: white;
            border: none;
            border-radius: 4px;
            font-weight: bold;
            cursor: pointer;
        }

        button:hover {
            background-color: #2980b9;
        }

        .message {
            margin-top: 15px;
            text-align: center;
            font-size: 14px;
        }

        .error {
            color: #e74c3c;
        }

        .success {
            color: #2ecc71;
        }

        .link {
            text-align: center;
            margin-top: 20px;
        }

        .link a {
            color: #61dafb;
            text-decoration: none;
        }

        .link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="form-container">
    <h2>Register</h2>

    <form action="${pageContext.request.contextPath}/auth" method="post">
        <input type="hidden" name="action" value="register">

        <label for="name">Name:</label>
        <input type="text" name="name" id="name" required>

        <label for="email">Email:</label>
        <input type="email" name="email" id="email" required>

        <label for="password">Password:</label>
        <input type="password" name="password" id="password" required>

        <label for="role">Role:</label>
        <select name="role" id="role" required>
            <option value="MANAGER">Manager</option>
            <option value="OPERATOR">Operator</option>
        </select>

        <button type="submit">Register</button>
    </form>

    <div class="link">
        <p><a href="${pageContext.request.contextPath}/controller?action=showLogin">Already have an account? Login here</a></p>
    </div>

    <div class="message">
        <c:if test="${not empty msg}">
            <p class="error">${msg}</p>
        </c:if>
        <c:if test="${not empty success}">
            <p class="success">${success}</p>
        </c:if>
    </div>
</div>
</body>
</html>
