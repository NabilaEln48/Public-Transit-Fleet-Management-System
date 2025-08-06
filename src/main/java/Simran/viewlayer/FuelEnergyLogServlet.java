/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Prabhsimran Kaur
 * Student Id: 041119310
 * Professor Name: Teddy Yap
 */

package Simran.viewlayer;

import Simran.BusinessLayer.FuelEnergyLogBusinessLogic;
import Simran.alerts.DatabaseAlertObserver;
import Simran.transferobject.FuelEnergyLogDTO;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Servlet for handling CRUD operations on Fuel/Energy Logs.
 * 
 * Only users with the role "Operator" can add, update, or delete records.
 * Managers and other roles can only view the logs.
 * 
 * Mapped to URL: /FuelEnergyLogServlet
 * 
 * GET  - Displays logs or edit form based on the "operation" parameter.
 * POST - Executes add, update, or delete operations via the Command pattern.
 * 
 * Observer pattern is used to trigger database alerts on specific conditions.
 * 
 * Author: Prabhsimran Kaur
 */
public class FuelEnergyLogServlet extends HttpServlet {

    /** Business logic layer for fuel/energy log operations. */
    private final FuelEnergyLogBusinessLogic businessLogic = new FuelEnergyLogBusinessLogic();

    /**
     * Initializes the servlet by registering an alert observer.
     *
     * @throws ServletException if initialization fails
     */
    @Override
    public void init() throws ServletException {
        // Register observer for database alerts
        businessLogic.registerObserver(new DatabaseAlertObserver());
    }

    /**
     * Handles GET requests for viewing logs or displaying edit form.
     *
     * @param request  HTTP request
     * @param response HTTP response
     * @throws ServletException if servlet-specific error occurs
     * @throws IOException      if I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String operation = request.getParameter("operation");

        if ("editForm".equalsIgnoreCase(operation)) {
            // Show edit form for a specific log
            int id = Integer.parseInt(request.getParameter("id"));
            FuelEnergyLogDTO log = businessLogic.getById(id);
            request.setAttribute("log", log);
            RequestDispatcher dispatcher = request.getRequestDispatcher("edit_fuel_log.jsp");
            dispatcher.forward(request, response);

        } else {
            // Default view: show all fuel/energy logs
            List<FuelEnergyLogDTO> logs = businessLogic.getAll();
            request.setAttribute("fuelLogs", logs);
            RequestDispatcher dispatcher = request.getRequestDispatcher("fuel_logs.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * Handles POST requests for adding, updating, or deleting logs.
     * 
     * Uses the Command pattern to execute the requested action.
     *
     * @param request  HTTP request
     * @param response HTTP response
     * @throws ServletException if servlet-specific error occurs
     * @throws IOException      if I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String role = (String) request.getSession().getAttribute("userType");
        String operation = request.getParameter("operation");

        Simran.commands.CommandInvoker invoker = new Simran.commands.CommandInvoker();

        if ("add".equalsIgnoreCase(operation)) {
            // Create a new log object from form parameters
            FuelEnergyLogDTO log = new FuelEnergyLogDTO();
            log.setVehicleRef(request.getParameter("vehicle_ref"));
            log.setEnergyType(request.getParameter("energy_type"));
            log.setQuantityUsed(Double.parseDouble(request.getParameter("quantity_used")));
            log.setKmCovered(Double.parseDouble(request.getParameter("km_covered")));
            log.setRecordedAt(LocalDateTime.parse(request.getParameter("recorded_at")));

            // Execute Add command
            invoker.executeCommand(new Simran.commands.AddFuelLogCommand(businessLogic, log, role));

        } else if ("update".equalsIgnoreCase(operation)) {
            // Create a log object with updated data
            FuelEnergyLogDTO log = new FuelEnergyLogDTO();
            log.setId(Integer.parseInt(request.getParameter("id")));
            log.setVehicleRef(request.getParameter("vehicle_ref"));
            log.setEnergyType(request.getParameter("energy_type"));
            log.setQuantityUsed(Double.parseDouble(request.getParameter("quantity_used")));
            log.setKmCovered(Double.parseDouble(request.getParameter("km_covered")));
            log.setRecordedAt(LocalDateTime.parse(request.getParameter("recorded_at")));

            // Execute Update command
            invoker.executeCommand(new Simran.commands.UpdateFuelLogCommand(businessLogic, log, role));

        } else if ("delete".equalsIgnoreCase(operation)) {
            // Execute Delete command
            int id = Integer.parseInt(request.getParameter("id"));
            invoker.executeCommand(new Simran.commands.DeleteFuelLogCommand(businessLogic, id, role));
        }

        // Redirect to the main servlet to refresh the list
        response.sendRedirect(request.getContextPath() + "/FuelEnergyLogServlet");
    }
}