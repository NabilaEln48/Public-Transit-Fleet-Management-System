<%-- 
    Document   : fuel_logs
    Created on : Aug 4, 2025, 4:46:30 p.m.
    Author     : simra
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, java.util.Collections" %>
<%@ page import="Simran.transferobject.FuelEnergyLogDTO" %>

<%
    String role = (String) session.getAttribute("userType");
    List<FuelEnergyLogDTO> logs = (List<FuelEnergyLogDTO>) request.getAttribute("fuelLogs");
    if (logs == null) logs = Collections.emptyList();
%>

<!DOCTYPE html>
<html>
<head>
    <title>Fuel/Energy Logs</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f8f9fa; margin: 0; padding: 0; }
        h2 { text-align: center; padding: 20px; }
        table { width: 90%; margin: auto; border-collapse: collapse; background: white; }
        th, td { padding: 10px; border: 1px solid #ccc; text-align: center; }
        th { background-color: #343a40; color: white; }
        tr:nth-child(even) { background-color: #f2f2f2; }
        .action-btn { padding: 6px 12px; border: none; cursor: pointer; border-radius: 4px; }
        .delete-btn { background-color: #dc3545; color: white; }
        .edit-btn { background-color: #ffc107; color: black; }
        .add-section { width: 90%; margin: 20px auto; padding: 20px; background: white; border-radius: 8px; }
        .add-section input { margin: 5px; padding: 8px; width: calc(20% - 12px); }
        .add-btn { background-color: #28a745; color: white; }
    </style>
</head>
<body>

<h2>Fuel/Energy Logs</h2>

<table>
<tr>
    <th>ID</th><th>Vehicle Ref</th><th>Energy Type</th>
    <th>Quantity Used</th><th>KM Covered</th><th>Recorded At</th>
    <% if ("OPERATOR".equalsIgnoreCase(role)) { %><th>Actions</th><% } %>
</tr>

<% if (logs.isEmpty()) { %>
<tr>
    <td colspan="<%= "OPERATOR".equalsIgnoreCase(role) ? 7 : 6 %>">No logs found</td>
</tr>
<% } else { 
   for (FuelEnergyLogDTO log : logs) { %>
<tr>
    <td><%= log.getId() %></td>
    <td><%= log.getVehicleRef() %></td>
    <td><%= log.getEnergyType() %></td>
    <td><%= log.getQuantityUsed() %></td>
    <td><%= log.getKmCovered() %></td>
    <td><%= log.getRecordedAt() %></td>

    <% if ("OPERATOR".equalsIgnoreCase(role)) { %>
    <td>
        <!-- DELETE -->
        <form method="post" action="<%= request.getContextPath() %>/FuelEnergyLogServlet" style="display:inline;">
            <input type="hidden" name="operation" value="delete"/>
            <input type="hidden" name="id" value="<%= log.getId() %>"/>
            <button type="submit" class="action-btn delete-btn">Delete</button>
        </form>

        <!-- EDIT (GET request) -->
        <form method="get" action="<%= request.getContextPath() %>/FuelEnergyLogServlet" style="display:inline;">
            <input type="hidden" name="operation" value="editForm"/>
            <input type="hidden" name="id" value="<%= log.getId() %>"/>
            <button type="submit" class="action-btn edit-btn">Edit</button>
        </form>
    </td>
    <% } %>
</tr>
<% }} %>
</table>

<% if ("OPERATOR".equalsIgnoreCase(role)) { %>
<div class="add-section">
    <h3>Add New Log</h3>
    <form method="post" action="<%= request.getContextPath() %>/FuelEnergyLogServlet">
        <input type="hidden" name="operation" value="add"/>
        <input type="text" name="vehicle_ref" placeholder="Vehicle Ref" required/>
        <input type="text" name="energy_type" placeholder="Energy Type" required/>
        <input type="number" step="0.01" name="quantity_used" placeholder="Quantity Used" required/>
        <input type="number" step="0.01" name="km_covered" placeholder="KM Covered" required/>
        <input type="datetime-local" name="recorded_at" required/>
        <button type="submit" class="action-btn add-btn">Add Log</button>
    </form>
</div>
<% } %>

</body>
</html>