/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Zhiru Xie
 * Student Id: 041143904
 * Professor Name: Teddy Yap
 */
package ZhiruXie.ViewLayer;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Vehicle management page view servlet.
 * @author Zhiru Xie
 * @since JDK21
 * @version 1.0
 * @see ZhiruXie.ViewLayer
 */
public class VehicleManagementServlet extends HttpServlet {
    /** Default constructor without parameters. */
    public VehicleManagementServlet() {}

    /**Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    * @throws ServletException if a servlet-specific error occurs
    * @throws IOException if an I/O error occurs
    */
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
     * Provides dashboard servlet information.
     * @return Dashboard servlet information
     */
    @Override
    public String getServletInfo() {
        return "Report Dashboard Servlet";
    }
}
