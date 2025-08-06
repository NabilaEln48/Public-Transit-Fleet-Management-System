/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Prabhsimran Kaur
 * Student Id: 041119310
 * Professor Name: Teddy Yap
 */

package Simran.viewlayer;

import Simran.dataaccesslayer.SystemAlertDAO;
import Simran.dataaccesslayer.SystemAlertDAOImpl;
import Simran.transferobject.SystemAlertDTO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * Servlet for handling system alert management.
 * 
 * Handles displaying all alerts and updating their resolution status.
 * 
 * Mapped to URL: /AlertServlet
 * 
 * - GET: Retrieves all alerts and forwards them to alerts.jsp
 * - POST: Updates alert resolution state and redirects to GET view
 * 
 * @author Prabhsimran Kaur
 * 
 */
public class AlertServlet extends HttpServlet {

    /** DAO for performing alert database operations. */
    private final SystemAlertDAO alertDAO = new SystemAlertDAOImpl();

    /**
     * Handles GET requests to display all system alerts.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @throws ServletException if servlet-specific error occurs
     * @throws IOException      if I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve all alerts from database
        List<SystemAlertDTO> alerts = alertDAO.getAllAlerts();

        // Store alerts in request scope for JSP rendering
        request.setAttribute("alerts", alerts);

        // Forward request to JSP view
        RequestDispatcher dispatcher = request.getRequestDispatcher("alerts.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Handles POST requests to update the resolution state of an alert.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @throws ServletException if servlet-specific error occurs
     * @throws IOException      if I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get alert ID and new resolution state from form submission
        int alertId = Integer.parseInt(request.getParameter("id"));
        String newStatus = request.getParameter("resolution_state");

        // Update alert resolution state in the database
        alertDAO.updateAlertStatus(alertId, newStatus);

        // Redirect back to alert list (GET method)
        response.sendRedirect(request.getContextPath() + "/AlertServlet");
    }
}