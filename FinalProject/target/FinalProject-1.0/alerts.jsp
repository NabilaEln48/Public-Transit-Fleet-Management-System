<%-- 
    Document   : alerts
    Created on : Aug 5, 2025, 5:00:13 p.m.
    Author     : simra
--%>

<%@page import="ZhiruXie.Utility.TimestampFormatter"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="Simran.transferobject.SystemAlertDTO" %>

<%
    List<SystemAlertDTO> alerts = (List<SystemAlertDTO>) request.getAttribute("alerts");
%>

<!DOCTYPE html>
<html>
<head>
    <title>System Alerts</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, sans-serif;
            background-color: #121212;
            color: #f5f5f5;
            margin: 0;
            padding: 0;
        }
        h2 {
            text-align: center;
            padding: 20px;
            color: #ffffff;
        }
        .container {
            width: 90%;
            margin: auto;
            background-color: #1e1e1e;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0px 0px 10px rgba(255,255,255,0.1);
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
            background-color: #2c2c2c;
            border-radius: 8px;
            overflow: hidden;
        }
        th, td {
            padding: 12px;
            border-bottom: 1px solid #444;
            text-align: center;
        }
        th {
            background-color: #343a40;
            color: #ffffff;
        }
        tr:nth-child(even) {
            background-color: #242424;
        }
        select, button {
            padding: 5px 10px;
            border-radius: 4px;
            border: none;
            font-size: 14px;
        }
        select {
            background-color: #1e1e1e;
            color: #f5f5f5;
            border: 1px solid #555;
        }
        button {
            cursor: pointer;
            background-color: #007bff;
            color: white;
        }
        button:hover {
            background-color: #0056b3;
        }
        .back-btn {
            display: inline-block;
            margin-bottom: 15px;
            padding: 8px 16px;
            background-color: #6c757d;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
        .back-btn:hover {
            background-color: #5a6268;
        }
    </style>
</head>
<body>
<h2>System Alerts</h2>
<div class="container">
    <a href="<%= request.getContextPath() %>/manager.jsp" class="back-btn">← Go Back</a>
    <table>
        <tr>
            <th>ID</th><th>Category</th><th>Vehicle</th><th>Message</th><th>Time</th><th>Status</th><th>Action</th>
        </tr>
        <% for (SystemAlertDTO alert : alerts) { %>
        <tr>
            <td><%= alert.getId() %></td>
            <td><%= alert.getAlertCategory() %></td>
            <td><%= alert.getVehicleRef() %></td>
            <td><%= alert.getAlertMessage() %></td>
            <td><%= TimestampFormatter.format(alert.getAlertTime()) %></td>
            <td><%= alert.getResolutionState() %></td>
            <td>
                <form method="post" action="<%= request.getContextPath() %>/AlertServlet">
                    <input type="hidden" name="id" value="<%= alert.getId() %>"/>
                    <select name="resolution_state">
                        <option value="Pending" <%= alert.getResolutionState().equals("Pending") ? "selected" : "" %>>Pending</option>
                        <option value="Resolved" <%= alert.getResolutionState().equals("Resolved") ? "selected" : "" %>>Resolved</option>
                    </select>
                    <button type="submit">Update</button>
                </form>
            </td>
        </tr>
        <% } %>
    </table>
</div>
</body>
</html>