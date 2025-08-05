<%-- 
    Document   : edit_fuel_log
    Created on : Aug 5, 2025, 12:29:28 a.m.
    Author     : simra
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="Simran.transferobject.FuelEnergyLogDTO" %>
<%
    FuelEnergyLogDTO log = (FuelEnergyLogDTO) request.getAttribute("log");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Fuel/Energy Log</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 0;
        }
        h2 {
            text-align: center;
            padding: 20px;
            color: #343a40;
        }
        .form-container {
            width: 50%;
            margin: auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 0px 10px rgba(0,0,0,0.1);
        }
        label {
            font-weight: bold;
            display: block;
            margin-top: 15px;
        }
        input {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        .btn-container {
            text-align: center;
            margin-top: 20px;
        }
        .save-btn {
            background-color: #28a745;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        .save-btn:hover {
            background-color: #218838;
        }
        .cancel-btn {
            background-color: #6c757d;
            color: white;
            border: none;
            padding: 10px 20px;
            margin-left: 10px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        .cancel-btn:hover {
            background-color: #5a6268;
        }
    </style>
</head>
<body>

<h2>Edit Fuel/Energy Log</h2>
<div class="form-container">
    <form method="post" action="<%= request.getContextPath() %>/FuelEnergyLogServlet">
        <input type="hidden" name="operation" value="update"/>
        <input type="hidden" name="id" value="<%= log.getId() %>"/>

        <label>Vehicle Ref:</label>
        <input type="text" name="vehicle_ref" value="<%= log.getVehicleRef() %>" required/>

        <label>Energy Type:</label>
        <input type="text" name="energy_type" value="<%= log.getEnergyType() %>" required/>

        <label>Quantity Used:</label>
        <input type="number" step="0.01" name="quantity_used" value="<%= log.getQuantityUsed() %>" required/>

        <label>KM Covered:</label>
        <input type="number" step="0.01" name="km_covered" value="<%= log.getKmCovered() %>" required/>

        <label>Recorded At:</label>
        <input type="datetime-local" name="recorded_at" 
               value="<%= log.getRecordedAt().toString().replace(' ', 'T') %>" required/>

        <div class="btn-container">
            <button type="submit" class="save-btn">Save Changes</button>
            <a href="<%= request.getContextPath() %>/FuelEnergyLogServlet">
                <button type="button" class="cancel-btn">Cancel</button>
            </a>
        </div>
    </form>
</div>

</body>
</html>
