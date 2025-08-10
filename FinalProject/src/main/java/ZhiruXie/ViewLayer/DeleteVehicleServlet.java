/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Zhiru Xie
 * Student Id: 041143904
 * Professor Name: Teddy Yap
 */
package ZhiruXie.ViewLayer;

import ZhiruXie.DataAccessLayer.VehicleDAO;
import ZhiruXie.DataAccessLayer.VehicleDAOImp;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Delete vehicle page view servlet.
 * @author Zhiru Xie
 * @since JDK21
 * @version 1.0
 * @see ZhiruXie.ViewLayer
 */
public class DeleteVehicleServlet extends HttpServlet {
    /** Default constructor without parameters. */
    public DeleteVehicleServlet() {}

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
        processRequest(request, response, new HashMap<>(), new HashMap<>());
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

        String formSubmitted = request.getParameter("formSubmitted");

        Map<String, String> formValues = new HashMap<>();
        Map<String, String> formErrors = new HashMap<>();

        String vehicleId = safeString(request.getParameter("vehicleId"));
        formValues.put("vehicleId", vehicleId);

        if (formSubmitted != null) {
            if (isBlank(vehicleId)) {
                formErrors.put("vehicleId", "Vehicle ID is required");
            }

            if (formErrors.isEmpty()) {
                try {
                    VehicleDAO vehicleDAO = new VehicleDAOImp();
                    boolean success = vehicleDAO.delete(vehicleId);

                    if (success) {
                        response.sendRedirect("DeleteVehicle?success=1");
                        return;
                    } else {
                        formErrors.put("general", "Vehicle not found or could not be deleted.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    formErrors.put("general", "An unexpected error occurred: " + e.getMessage());
                }
            }
        }

        processRequest(request, response, formValues, formErrors);
    }

    /**Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    * @throws ServletException if a servlet-specific error occurs
    * @throws IOException if an I/O error occurs
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response,
                           Map<String, String> values, Map<String, String> errors)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head><title>Delete Vehicle</title>");
            out.println("<link rel='stylesheet' type='text/css' href='css/DeleteVehicle.css'>");
            out.println("<style>");
            out.println(".error-message { color: #ff4d4d; font-size: 13px; margin-top: 4px; text-align: center; }");
            out.println(".success-message { color: #80ff80; font-size: 14px; margin-top: 15px; text-align: center; font-weight: bold; }");
            out.println("</style>");
            out.println("</head><body>");

            out.println("<div class='form-container'>");
            out.println("<h1>Delete Vehicle</h1>");

            // Form start
            out.println("<form method='post' action='DeleteVehicle'>");
            out.println("<input type='hidden' name='formSubmitted' value='true'/>");

            // Vehicle ID input
            out.println("<div>");
            out.println("<label>Vehicle ID:</label>");
            out.printf("<input type='text' name='vehicleId' value='%s'/>",
                    values.getOrDefault("vehicleId", ""));
            if (errors.containsKey("vehicleId")) {
                out.printf("<div class='error-message' style='text-align:left;'>%s</div>", errors.get("vehicleId"));
            }
            out.println("</div>");

            // Delete button
            out.println("<div>");
            out.println("<button type='submit'>Delete Vehicle</button>");
            out.println("</div>");
            out.println("</form>");

            // Return button
            out.println("<form action='VehicleManagement' method='get'>");
            out.println("<button type='submit' class='btn-secondary'>Return</button>");
            out.println("</form>");

            // Messages section at the bottom
            if ("1".equals(request.getParameter("success"))) {
                out.println("<div class='success-message'>Vehicle Deleted Successfully!</div>");
            } else if (errors.containsKey("general")) {
                out.printf("<div class='failure-message'>%s</div>", errors.get("general"));
            }

            out.println("</div>"); // End of form-container
            out.println("</body></html>");
        }
    }
    
    /**
     * Removes extra spaces from given text content.
     * @params Raw text content
     * @return Processed text content without extra space
     */
    private String safeString(String value) {
        return value != null ? value.trim() : "";
    }

    /**
     * Validates of given text content is empty.
     * @params Raw text content
     * @return A Boolean value for validation result - true for empty and false for not
     */
    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
