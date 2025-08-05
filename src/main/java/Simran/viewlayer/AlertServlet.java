/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Simran.viewlayer;

import Simran.dataaccesslayer.SystemAlertDAO;
import Simran.dataaccesslayer.SystemAlertDAOImpl;
import Simran.transferobject.SystemAlertDTO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class AlertServlet extends HttpServlet {
    private final SystemAlertDAO alertDAO = new SystemAlertDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<SystemAlertDTO> alerts = alertDAO.getAllAlerts();
        request.setAttribute("alerts", alerts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("alerts.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int alertId = Integer.parseInt(request.getParameter("id"));
        String newStatus = request.getParameter("resolution_state");
        alertDAO.updateAlertStatus(alertId, newStatus);
        response.sendRedirect(request.getContextPath() + "/AlertServlet");
    }
}