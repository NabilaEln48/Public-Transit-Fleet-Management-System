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

/**
 *
 * @author 61963
 */
public class ReportDashboardServlet extends HttpServlet{
    /** Default constructor without parameters. */
    public ReportDashboardServlet(){}
    
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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Report Dashboard</title>");   
            out.println("<link rel=\"stylesheet\" href=\"css/ReportDashboard.css\" />");
            out.println("</head>");
            out.println("<body>");
            // content
            out.println("<h1>Report Dashboard</h1>");
            out.println("<form method=\"post\" action=\"FrontendController\">");
            out.println("<div class=\"buttons\">");
            out.println("<button type=\"submit\" name=\"action\" value=\"MaintenanceDashboard\">Maintenance Dashboard</button>");
            out.println("<button type=\"submit\" name=\"action\" value=\"PerformanceDashboard\">Performance Dashboard</button>");
            if(role.equalsIgnoreCase("MANAGER")){
                out.println("<button type=\"submit\" name=\"action\" value=\"CostReports\">Cost Reports</button>");
            }
            out.println("</div>");
            out.println("</form>");
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

