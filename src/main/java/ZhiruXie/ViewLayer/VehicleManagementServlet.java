/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Purnima.ViewLayer;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class VehicleManagementServlet extends HttpServlet {
    /** Default constructor without parameters. */
    public VehicleManagementServlet() {}

    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods. */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Vehicle Management</title>");
            out.println("<link rel='stylesheet' type='text/css' href='css/ReportDashboard.css'>");
            out.println("</head>");
            out.println("<body>");

            // Content
            out.println("<div class=\"container\">");
            out.println("<h1>Vehicle Management Options</h1>");
            out.println("<form method=\"post\" action=\"FrontendController\">");
            out.println("<div class=\"buttons\">");
            out.println("<button type=\"submit\" name=\"action\" value=\"GetVehicle\">Find Vehicle</button>");
            out.println("<button type=\"submit\" name=\"action\" value=\"AddVehicle\">Add Vehicle</button>");
            out.println("<button type=\"submit\" name=\"action\" value=\"UpdateVehicle\">Update Vehicle</button>");
            out.println("<button type=\"submit\" name=\"action\" value=\"DeleteVehicle\">Delete Vehicle</button>");
            out.println("</div>");
            out.println("</form>");
            out.println("<button class=\"return-button\" onclick=\"location.href='manager.jsp'\">Return</button>");
            out.println("</div>");
            // Content

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

    @Override
    public String getServletInfo() {
        return "Report Dashboard Servlet";
    }
}
