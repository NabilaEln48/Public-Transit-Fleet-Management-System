/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Simran.viewlayer;

import Simran.BusinessLayer.FuelEnergyLogBusinessLogic;
import Simran.transferobject.FuelEnergyLogDTO;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Servlet for handling CRUD operations on Fuel/Energy Logs.
 * Only Operators can Add, Update, Delete. Managers can view.
 * 
 * URL: /FuelEnergyLogServlet
 * 
 * @author
 */
public class FuelEnergyLogServlet extends HttpServlet {

    private final FuelEnergyLogBusinessLogic businessLogic = new FuelEnergyLogBusinessLogic();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<FuelEnergyLogDTO> logs = businessLogic.getAll();
        request.setAttribute("fuelLogs", logs);
        RequestDispatcher dispatcher = request.getRequestDispatcher("fuel_logs.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String role = (String) request.getSession().getAttribute("role");
        String operation = request.getParameter("operation");

        if ("add".equalsIgnoreCase(operation)) {
            FuelEnergyLogDTO log = new FuelEnergyLogDTO();
            log.setVehicleRef(request.getParameter("vehicle_ref"));
            log.setEnergyType(request.getParameter("energy_type"));
            log.setQuantityUsed(Double.parseDouble(request.getParameter("quantity_used")));
            log.setKmCovered(Double.parseDouble(request.getParameter("km_covered")));
            log.setRecordedAt(LocalDateTime.parse(request.getParameter("recorded_at")));
            businessLogic.add(log, role);

        } else if ("update".equalsIgnoreCase(operation)) {
            FuelEnergyLogDTO log = new FuelEnergyLogDTO();
            log.setId(Integer.parseInt(request.getParameter("id")));
            log.setVehicleRef(request.getParameter("vehicle_ref"));
            log.setEnergyType(request.getParameter("energy_type"));
            log.setQuantityUsed(Double.parseDouble(request.getParameter("quantity_used")));
            log.setKmCovered(Double.parseDouble(request.getParameter("km_covered")));
            log.setRecordedAt(LocalDateTime.parse(request.getParameter("recorded_at")));
            businessLogic.update(log, role);

        } else if ("delete".equalsIgnoreCase(operation)) {
            int id = Integer.parseInt(request.getParameter("id"));
            businessLogic.delete(id, role);
        }

        response.sendRedirect(request.getContextPath() + "/FuelEnergyLogServlet");
    }
}
