<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<!--
    Author       : Nabila Msiah 041146732
    Course       : CST8288 - Group Project - Authentication Module (Nabila Msiah Part)
    Description  : Secure JSP form for user registration, using Front Controller for routing.
-->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
        }
        .form-container {
            background-color: #fff;
            padding: 24px 36px;
            border-radius: 8px;
            box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
            width: 350px;
        }
        h2 {
            margin-top: 0;
            text-align: center;
        }
        label {
            font-weight: bold;
            display: block;
            margin-top: 12px;
        }
        input, select {
            width: 100%;
            padding: 8px;
            margin-top: 4px;
            margin-bottom: 12px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        button {
            width: 100%;
            padding: 10px;
            background-color: #0066cc;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
        }
        button:hover {
            background-color: #004a99;
        }
        .message {
            margin-top: 12px;
            text-align: center;
            font-weight: bold;
        }
        .error {
            color: red;
        }
        .success {
            color: green;
        }
        .link {
            margin-top: 16px;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="form-container">
    <h2>Register</h2>

    <form action="${pageContext.request.contextPath}/auth" method="post">
        <input type="hidden" name="action" value="register">

        <label>Name:</label>
        <input type="text" name="name" required>

        <label>Email:</label>
        <input type="email" name="email" required>

        <label>Password:</label>
        <input type="password" name="password" required>

        <label>Role:</label>
        <select name="role" required>
            <option value="MANAGER">Manager</option>
            <option value="OPERATOR">Operator</option>
        </select>

        <button type="submit">Register</button>
    </form>

    <div class="link">
        <a href="${pageContext.request.contextPath}/controller?action=showLogin">Already have an account? Login here</a>
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
