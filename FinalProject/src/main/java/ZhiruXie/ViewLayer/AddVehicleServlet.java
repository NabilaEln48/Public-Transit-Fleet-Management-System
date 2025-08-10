/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Zhiru Xie
 * Student Id: 041143904
 * Professor Name: Teddy Yap
 */
package ZhiruXie.ViewLayer;

import ZhiruXie.DTO.VehicleDTO;
import ZhiruXie.DataAccessLayer.VehicleDAO;
import ZhiruXie.DataAccessLayer.VehicleDAOImp;
import ZhiruXie.enums.Enum_VehicleCategory;
import ZhiruXie.enums.Enum_VehicleOperationalState;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Add vehicle page view servlet.
 * @author Zhiru Xie
 * @since JDK21
 * @version 1.0
 * @see ZhiruXie.ViewLayer
 */
public class AddVehicleServlet extends HttpServlet {
    /** Default constructor without parameters. */
    public AddVehicleServlet() {};
    
    /**Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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

        // Get form inputs
        String vehicleId = safeString(request.getParameter("vehicleId"));
        String registrationNumber = safeString(request.getParameter("registrationNumber"));
        String fuelUsed = safeString(request.getParameter("fuelUsed"));
        String efficiencyRate = safeString(request.getParameter("efficiencyRate"));
        String capacity = safeString(request.getParameter("capacity"));
        String assignedRoute = safeString(request.getParameter("assignedRoute"));
        String category = safeString(request.getParameter("category"));
        String operationalState = safeString(request.getParameter("operationalState"));

        // Keep values for sticky form
        formValues.put("vehicleId", vehicleId);
        formValues.put("registrationNumber", registrationNumber);
        formValues.put("fuelUsed", fuelUsed);
        formValues.put("efficiencyRate", efficiencyRate);
        formValues.put("capacity", capacity);
        formValues.put("assignedRoute", assignedRoute);
        formValues.put("category", category);
        formValues.put("operationalState", operationalState);

        // Validation
        if (formSubmitted != null) {
            if (isBlank(vehicleId)) formErrors.put("vehicleId", "Vehicle ID is required");
            if (isBlank(registrationNumber)) formErrors.put("registrationNumber", "Registration Number is required");
            if (isBlank(fuelUsed)) formErrors.put("fuelUsed", "Fuel Used is required");
            if (isBlank(efficiencyRate)) formErrors.put("efficiencyRate", "Efficiency Rate is required");
            if (isBlank(capacity)) formErrors.put("capacity", "Capacity is required");
            if (isBlank(assignedRoute)) formErrors.put("assignedRoute", "Assigned Route is required");
            if (isBlank(category)) formErrors.put("category", "Vehicle Category is required");
            if (isBlank(operationalState)) formErrors.put("operationalState", "Operational State is required");

            // If no validation errors → save to DB
            if (formErrors.isEmpty()) {
                Map<String, String> categoryMap = new HashMap<>();
                categoryMap.put("Diesel Bus", "DieselBus");
                categoryMap.put("Electric Light Rail", "ElectricLightRail");
                categoryMap.put("Diesel-Electric Train", "DieselElectricTrain");
                String convertedOperationalState = categoryMap.get(category);
                try {
                    VehicleDTO vehicle = new VehicleDTO(
                        vehicleId,
                        Enum_VehicleCategory.valueOf(convertedOperationalState),
                        registrationNumber,
                        fuelUsed,
                        Double.parseDouble(efficiencyRate),
                        Integer.parseInt(capacity),
                        assignedRoute,
                        Enum_VehicleOperationalState.valueOf(operationalState)
                    );

                    VehicleDAO vehicleDAO = new VehicleDAOImp();
                    boolean success = vehicleDAO.add(vehicle);

                    if (success) {
                        // Redirect to list page
                        response.sendRedirect("AddVehicle?success=1");
                        return;
                    } else {
                        formErrors.put("general", "Failed! Vehicle Already Exist.");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    formErrors.put("general", "An unexpected error occurred: " + e.getMessage());
                }
            }
        }

        // If errors or first load, redisplay form
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

        String[] fuelTypes = {"Diesel", "Electric", "Hybrid"};
        String[] vehicleCategories = {"Diesel Bus", "Electric Light Rail", "Diesel-Electric Train"};
        String[] operationalStates = {"Active", "Inactive", "Maintenance"};

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head><title>Add Vehicle</title>");
            out.println("<link rel='stylesheet' type='text/css' href='css/AddVehicle.css'>");
            out.println("<style>");
            out.println(".error-message { color: #ff4d4d; font-size: 13px; margin-top: 4px; text-align:center; font-weight:bold; }");
            out.println(".success-message { color: green; font-size: 14px; margin-top: 10px; text-align:center; font-weight:bold; }");
            out.println("</style>");
            out.println("</head><body>");

            out.println("<div class='form-container'>");
            out.println("<h1>Add New Vehicle</h1>");

            out.println("<form method='post' action='AddVehicle'>");
            out.println("<input type='hidden' name='formSubmitted' value='true'/>");

            // Vehicle ID
            out.println("<div>");
            out.println("<label>Vehicle ID:</label>");
            out.printf("<input type='text' name='vehicleId' value='%s'/>", values.getOrDefault("vehicleId", ""));
            if (errors.containsKey("vehicleId")) 
                out.printf("<div class='error-message'>%s</div>", errors.get("vehicleId"));
            out.println("</div>");

            // Registration Number
            out.println("<div>");
            out.println("<label>Registration Number:</label>");
            out.printf("<input type='text' name='registrationNumber' value='%s'/>", values.getOrDefault("registrationNumber", ""));
            if (errors.containsKey("registrationNumber")) 
                out.printf("<div class='error-message'>%s</div>", errors.get("registrationNumber"));
            out.println("</div>");

            // Fuel Used dropdown
            out.println("<div>");
            out.println("<label>Fuel Used:</label>");
            out.println("<select name='fuelUsed'>");
            out.println("<option value=''>-- Select Fuel Type --</option>");
            for (String fuel : fuelTypes) {
                String selected = fuel.equals(values.get("fuelUsed")) ? "selected" : "";
                out.printf("<option value='%s' %s>%s</option>", fuel, selected, fuel);
            }
            out.println("</select>");
            if (errors.containsKey("fuelUsed")) 
                out.printf("<div class='error-message'>%s</div>", errors.get("fuelUsed"));
            out.println("</div>");

            // Vehicle Category dropdown
            out.println("<div>");
            out.println("<label>Vehicle Category:</label>");
            out.println("<select name='category'>");
            out.println("<option value=''>-- Select Category --</option>");
            for (String cat : vehicleCategories) {
                String selected = cat.equals(values.get("category")) ? "selected" : "";
                out.printf("<option value='%s' %s>%s</option>", cat, selected, cat);
            }
            out.println("</select>");
            if (errors.containsKey("category")) 
                out.printf("<div class='error-message'>%s</div>", errors.get("category"));
            out.println("</div>");

            // Efficiency Rate
            out.println("<div>");
            out.println("<label>Efficiency Rate:</label>");
            out.printf("<input type='text' name='efficiencyRate' value='%s'/>", values.getOrDefault("efficiencyRate", ""));
            if (errors.containsKey("efficiencyRate")) 
                out.printf("<div class='error-message'>%s</div>", errors.get("efficiencyRate"));
            out.println("</div>");

            // Capacity
            out.println("<div>");
            out.println("<label>Capacity:</label>");
            out.printf("<input type='text' name='capacity' value='%s'/>", values.getOrDefault("capacity", ""));
            if (errors.containsKey("capacity")) 
                out.printf("<div class='error-message'>%s</div>", errors.get("capacity"));
            out.println("</div>");

            // Assigned Route
            out.println("<div>");
            out.println("<label>Assigned Route:</label>");
            out.printf("<input type='text' name='assignedRoute' value='%s'/>", values.getOrDefault("assignedRoute", ""));
            if (errors.containsKey("assignedRoute")) 
                out.printf("<div class='error-message'>%s</div>", errors.get("assignedRoute"));
            out.println("</div>");

            // Operational State dropdown
            out.println("<div>");
            out.println("<label>Operational State:</label>");
            out.println("<select name='operationalState'>");
            out.println("<option value=''>-- Select Operational State --</option>");
            for (String state : operationalStates) {
                String selected = state.equals(values.get("operationalState")) ? "selected" : "";
                out.printf("<option value='%s' %s>%s</option>", state, selected, state);
            }
            out.println("</select>");
            if (errors.containsKey("operationalState")) 
                out.printf("<div class='error-message'>%s</div>", errors.get("operationalState"));
            out.println("</div>");

            // Submit button
            out.println("<div>");
            out.println("<button type='submit'>Add Vehicle</button>");
            out.println("</div>");
            out.println("</form>");

            // Return button
            out.println("<form action=\"VehicleManagement\" method=\"get\">");
            out.println("<button type=\"submit\" class=\"btn-secondary\">Return</button>");
            out.println("</form>");


            if (errors.containsKey("general")) {
                out.printf("<div class='failure-message'>%s</div>", errors.get("general"));
            }
            if ("1".equals(request.getParameter("success"))) {
                out.println("<div class='success-message'>Vehicle Added Successfully!</div>");
            }

            out.println("</div>"); // form-container
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
