/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ZhiruXie.ViewLayer;

import ZhiruXie.DTO.PerformanceDTO;
import ZhiruXie.Utility.TimestampFormatter;
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
public class PerformanceDashboardServlet extends HttpServlet{
    /** Default constructor without parameters. */
    public PerformanceDashboardServlet(){}
    
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
            List<PerformanceDTO> records = (List<PerformanceDTO>)request.getAttribute("performanceRecords");
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Performance Dashboard</title>");   
            out.println("<link rel='stylesheet' type='text/css' href='css/PerformanceDashboard.css'>");
            out.println("</head>");
            out.println("<body>");
            // content
            out.println("<h1>Performance Dashboard</h1>");
            String dashboardPage = "operator.jsp";
            if(role.equalsIgnoreCase("MANAGER")){
                dashboardPage = "manager.jsp";
                String operatorParam = request.getParameter("operatorId");
                String operatorStr = (operatorParam != null && !operatorParam.isEmpty())
                    ? operatorParam
                    : request.getSession().getAttribute("userId").toString();
                out.println("<div style='display:flex; justify-content:center; margin-bottom:30px;'>");
                out.println("<form method='get' action='FrontendController' style='display:flex; flex-wrap:wrap; align-items:center; background-color:#fff; padding:20px 30px; border-radius:10px; box-shadow:0 2px 10px rgba(0,0,0,0.08); gap:15px;'>");
                out.println("<input type='hidden' name='action' value='PerformanceDashboard' />");
                out.println("<label for='operatorId' style='font-weight:600; color:#34495e;'>Operator Id:</label>");
                out.println("<input type='text' id='operatorId' name='operatorId' value='" + operatorStr + "' style='padding:10px 14px; border:1px solid #ccc; border-radius:6px; font-size:15px; min-width:120px;' />");
                out.println("<button type='submit' style='background-color:#2980b9; color:white; padding:10px 18px; border:none; border-radius:6px; font-size:15px; cursor:pointer;'>Find Records</button>");
                out.println("</form>");
                String errorMsg = (String) request.getAttribute("errorMessage");
                out.println("</div>");
                if (errorMsg != null) {
                    out.println("<div style='color: red; font-weight: bold; text-align: center; margin-bottom: 20px;'>");
                    out.println(errorMsg);
                    out.println("</div>");
                }
            }
            if (records != null && !records.isEmpty()) {
                for (PerformanceDTO record : records) {
                    out.println("<div class='card'>");
                    out.println("<h2>Trip #" + record.getId() + "</h2>");
                    out.println("<p><span class='label'>Operator ID:</span> " + record.getOperatorId() + "</p>");
                    out.println("<p><span class='label'>Vehicle ID:</span> " + record.getVehicleId() + "</p>");
                    out.println("<p><span class='label'>Route Name:</span> " + record.getRouteName() + "</p>");
                    out.println("<p><span class='label'>Scheduled Start:</span> " + TimestampFormatter.format(record.getScheduledStartTime()) + "</p>");
                    out.println("<p><span class='label'>Start Time:</span> " + TimestampFormatter.format(record.getStartTime()) + "</p>");
                    out.println("<p><span class='label'>End Time:</span> " + TimestampFormatter.format(record.getEndTime()) + "</p>");
                    out.println("<p><span class='label'>On Time:</span> " + (record.getOnTime() ? "Yes" : "No") + "</p>");
                    out.println("<p><span class='label'>Distance:</span> " + record.getDistance() + " km</p>");
                    out.println("<p><span class='label'>Used Time:</span> " + record.getUsedTime() + " min</p>");
                    out.println("<p><span class='label'>Idle Time:</span> " + record.getIdleTime() + " min</p>");
                    out.println("<p><span class='label'>Fuel Spent:</span> " + record.getFuelSpent() + " L</p>");
                    out.println("<p><span class='label'>Passengers:</span> " + record.getPassengerNumber() + "</p>");
                    out.println("</div>");
                }
            } else {
                out.println("<p>No performance data available.</p>");
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