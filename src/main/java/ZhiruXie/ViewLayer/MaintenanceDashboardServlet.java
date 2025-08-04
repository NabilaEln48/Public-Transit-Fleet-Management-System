/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ZhiruXie.ViewLayer;

import ZhiruXie.DTO.MaintenanceScheduleDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 61963
 */
public class MaintenanceDashboardServlet extends HttpServlet{
    /** Default constructor without parameters. */
    public MaintenanceDashboardServlet(){}
    
    /**Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    * @throws ServletException if a servlet-specific error occurs
    * @throws IOException if an I/O error occurs
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            String role = request.getSession().getAttribute("userType").toString();
            List<MaintenanceScheduleDTO> schedules = (List<MaintenanceScheduleDTO>)request.getAttribute("schedules");
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Maintenance Dashboard</title>");
            out.println("<link rel='stylesheet' type='text/css' href='css/MaintenanceDashboard.css'>");
            out.println("</head>");
            out.println("<body>");
            // content
            out.println("<h1>Maintenance Dashboard</h1>");
            String dashboardPage = "operator.jsp";
            if(role.equalsIgnoreCase("MANAGER")){
                dashboardPage = "manager.jsp";
                String technicianParam = request.getParameter("technicianId");
                String technicianValue = (technicianParam != null && !technicianParam.isEmpty())
                    ? technicianParam
                    : request.getSession().getAttribute("userId").toString();
                out.println("<div style='display:flex; justify-content:center; margin-bottom:30px;'>");
                out.println("<form method='get' action='FrontendController' style='display:flex; flex-wrap:wrap; align-items:center; background-color:#fff; padding:20px 30px; border-radius:10px; box-shadow:0 2px 10px rgba(0,0,0,0.08); gap:15px;'>");
                out.println("<input type='hidden' name='action' value='MaintenanceDashboard' />");
                out.println("<label for='technicianId' style='font-weight:600; color:#34495e;'>Technician Id:</label>");
                out.println("<input type='text' id='technicianId' name='technicianId' value='" + technicianValue + "' style='padding:10px 14px; border:1px solid #ccc; border-radius:6px; font-size:15px; min-width:120px;' />");
                out.println("<button type='submit' style='background-color:#2980b9; color:white; padding:10px 18px; border:none; border-radius:6px; font-size:15px; cursor:pointer;'>Find Records</button>");
                out.println("</form>");
                out.println("</div>");
                String errorMsg = (String) request.getAttribute("errorMessage");
                out.println("</div>");
                if (errorMsg != null) {
                    out.println("<div style='color: red; font-weight: bold; text-align: center; margin-bottom: 20px;'>");
                    out.println(errorMsg);
                    out.println("</div>");
                }
            }
            if (schedules == null || schedules.isEmpty()) {
                out.println("<p>No maintenance records found for the specified technician.</p>");
            } else {
                for (MaintenanceScheduleDTO schedule : schedules) {
                    out.println("<div class='card'>");
                    out.println("<h2>Schedule #" + schedule.getId() + "</h2>");
                    out.println("<p><span class='label'>Component ID:</span> " + schedule.getComponentId() + "</p>");
                    out.println("<p><span class='label'>Vehicle ID:</span> " + schedule.getVehicleId() + "</p>");
                    out.println("<p><span class='label'>Task Description:</span> " + schedule.getTaskDescription() + "</p>");
                    out.println("<p><span class='label'>Planned Date:</span> " + schedule.getPlannedDate() + "</p>");
                    out.println("<p><span class='label'>Status:</span> " + schedule.getProgressStatus() + "</p>");
                    out.println("</div>");
                }
            }
            out.println("<div class='return-container'>");
            out.println("<a href='" + request.getContextPath() + "/" + dashboardPage + "' class='return-btn'>Return to Menu</a>");
            out.println("</div>");
            //content
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="doGet() doPost()">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}