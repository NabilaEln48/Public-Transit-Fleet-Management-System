/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ZhiruXie.ViewLayer;

import ZhiruXie.DTO.VehicleDTO;
import ZhiruXie.DataAccessLayer.VehicleDAO;
import ZhiruXie.DataAccessLayer.VehicleDAOImp;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class GetVehicleServlet extends HttpServlet {

    public GetVehicleServlet() {}
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String vehicleIdParam = request.getParameter("vehicleId");
        VehicleDAO dao = new VehicleDAOImp();
        List<VehicleDTO> vehicles = new ArrayList<>();

        try {
            if (vehicleIdParam != null && !vehicleIdParam.trim().isEmpty()) {
                VehicleDTO vehicle = dao.getSingleById(vehicleIdParam);
                if (vehicle != null) {
                    vehicles.add(vehicle);
                } else {
                    request.setAttribute("errorMessage", "No vehicle found with ID: " + vehicleIdParam);
                }
            } else {
                vehicles = dao.getAll();
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error fetching vehicle data: " + e.getMessage());
        }

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Vehicle Information</title>");
            out.println("<link rel='stylesheet' type='text/css' href='css/GetVehicle.css'>");
            out.println("</head>");
            out.println("<body>");

            out.println("<h1>Vehicle Information</h1>");

            // Search form
            out.println("<form method='get' action='GetVehicle'>");
            out.println("<label for='vehicleId'>Vehicle ID:</label>");
            out.println("<input type='text' id='vehicleId' name='vehicleId' value='" +
                    (vehicleIdParam != null ? vehicleIdParam : "") + "'>");
            out.println("<button type='submit'>Find Vehicle</button>");
            out.println("</form>");

            // Error message
            String errorMsg = (String) request.getAttribute("errorMessage");
            if (errorMsg != null) {
                out.println("<div class='error-message'>" + errorMsg + "</div>");
            }

            // Vehicle list
            if (vehicles == null || vehicles.isEmpty()) {
                out.println("<p>No vehicles found.</p>");
            } else {
                for (VehicleDTO vehicle : vehicles) {
                    out.println("<div class='card'>");
                    out.println("<h2>Vehicle #" + vehicle.getId() + "</h2>");
                    out.println("<p><span class='label'>Category:</span> " + vehicle.getCategory() + "</p>");
                    out.println("<p><span class='label'>Registration Number:</span> " + vehicle.getRegistrationNumber() + "</p>");
                    out.println("<p><span class='label'>Fuel Used:</span> " + vehicle.getFuelUsed() + "</p>");
                    out.println("<p><span class='label'>Efficiency Rate:</span> " + vehicle.getEfficiencyRate() + "</p>");
                    out.println("<p><span class='label'>Capacity:</span> " + vehicle.getCapacity() + "</p>");
                    out.println("<p><span class='label'>Assigned Route:</span> " + vehicle.getAssigned_route() + "</p>");
                    out.println("<p><span class='label'>Operational State:</span> " + vehicle.getOperationalState() + "</p>");
                    out.println("</div>");
                }
            }

            // Return button
            out.println("<div class='return-container'>");
            out.println("<a href='VehicleManagement' class='return-btn'>Return</a>");
            out.println("</div>");

            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}