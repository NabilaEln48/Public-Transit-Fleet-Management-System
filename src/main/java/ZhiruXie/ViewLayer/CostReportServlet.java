/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ZhiruXie.ViewLayer;

import ZhiruXie.DTO.CostAnalysisDTO;
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
public class CostReportServlet extends HttpServlet{
    /** Default constructor without parameters. */
    public CostReportServlet(){}
    
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
            List<CostAnalysisDTO> records = (List<CostAnalysisDTO>)request.getAttribute("AnalysisRecords");
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Cost Report Page</title>");  
            out.println("<link rel='stylesheet' type='text/css' href='css/CostAnalysisDashboard.css'>");
            out.println("</head>");
            out.println("<body>");
            // content
            out.println("<h1>Fuel & Maintenance Cost Reports</h1>");
            if (records != null && !records.isEmpty()) {
                for (CostAnalysisDTO record : records) {
                    String recordType = record.getType();
                    out.println("<div class='card'>");
                    out.println("<h2>Report #" + record.getId() + "</h2>");
                    out.println("<p><span class='label'>Report Type:</span> " + recordType + "</p>");
                    String payload = record.getPayload();
                    String vehicleId = payload.split(":")[0].strip();
                    String info = payload.split(":")[1].strip();
                    if(recordType.endsWith("Fuel Usage")){
                        out.println("<p><span class='label'>Vehicle ID:</span> " + vehicleId + "</p>");
                        out.println("<p><span class='label'>Fuel Usage:</span> " + info + "</p>");
                    }
                    else if(recordType.endsWith("Maintenance Cost")){
                        out.println("<p><span class='label'>Vehicle ID:</span> " + vehicleId + "</p>");
                        out.println("<p><span class='label'>Estimated Maintenance Cost:</span> " + info + "</p>");
                    }
                    out.println("<p><span class='label'>Created At:</span> " + TimestampFormatter.format(record.getDateOfCreate()) + "</p>");
                    out.println("</div>");
                }
            } else {
                out.println("<p>No cost reports available.</p>");
            }
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
