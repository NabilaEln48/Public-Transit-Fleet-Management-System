package Simran.viewlayer;

import ZhiruXie.DataAccessLayer.MaintenanceScheduleDAO;
import ZhiruXie.DataAccessLayer.MaintenanceScheduleDAOImp;
import ZhiruXie.DTO.MaintenanceScheduleDTO;
import ZhiruXie.enums.Enum_MaintenanceProgressStatus;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ScheduleMaintenanceServlet extends HttpServlet {
    /** Default constructor without parameters. */
    public ScheduleMaintenanceServlet() {}
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);
        Map<String, String> errors = getSessionMap(session, "formErrors");
        Map<String, String> values = getSessionMap(session, "formValues");
        String successMessage = session != null ? (String) session.getAttribute("successMessage") : null;

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Schedule Maintenance</title>");
            out.println("<link rel='stylesheet' type='text/css' href='css/ScheduleMaintenance.css'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Schedule Vehicle Maintenance</h1>");

            out.println("<div class='form-container'>");
            out.println("<form method='post' action='ScheduleMaintenance'>");

            printTextField(out, "Component ID:", "componentId", values, errors);
            printTextField(out, "Vehicle ID:", "vehicleId", values, errors);
            printTextField(out, "Task Description:", "taskDescription", values, errors);
            printDateField(out, "Planned Date:", "plannedDate", values, errors);
            printTextField(out, "Technician ID:", "technicianId", values, errors);

            out.println("<button type='submit'>Add Schedule</button>");
            out.println("</form>");

            out.println("<a href='manager.jsp' class='return-btn'>Return</a>");

            if (errors.containsKey("generalError")) {
                out.println("<div class='error-message'>" + errors.get("generalError") + "</div>");
            }
            if (successMessage != null) {
                out.println("<div class='success-message'>" + successMessage + "</div>");
            }

            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }

        // Clear messages after displaying
        if (session != null) {
            session.removeAttribute("formErrors");
            session.removeAttribute("formValues");
            session.removeAttribute("successMessage");
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

        // Retrieve form fields
        String componentId = request.getParameter("componentId");
        String vehicleId = request.getParameter("vehicleId");
        String taskDescription = request.getParameter("taskDescription");
        String plannedDate = request.getParameter("plannedDate");
        String technicianId = request.getParameter("technicianId");

        // Prepare storage for errors and values
        Map<String, String> errors = new HashMap<>();
        Map<String, String> values = new HashMap<>();

        // Store submitted values to keep them in the form
        values.put("componentId", safeString(componentId));
        values.put("vehicleId", safeString(vehicleId));
        values.put("taskDescription", safeString(taskDescription));
        values.put("plannedDate", safeString(plannedDate));
        values.put("technicianId", safeString(technicianId));

        // Validate fields
        if (isBlank(componentId)) errors.put("componentIdError", "Component ID is required");
        if (isBlank(vehicleId)) errors.put("vehicleIdError", "Vehicle ID is required");
        if (isBlank(taskDescription)) errors.put("taskDescriptionError", "Task Description is required");
        if (isBlank(plannedDate)) errors.put("plannedDateError", "Planned Date is required");
        if (isBlank(technicianId)) errors.put("technicianIdError", "Technician ID is required");

        HttpSession session = request.getSession();
        session.setAttribute("formValues", values);

        if (!errors.isEmpty()) {
            session.setAttribute("formErrors", errors);
            response.sendRedirect("ScheduleMaintenance");
            return;
        }

        // Attempt database insert
        try {
            MaintenanceScheduleDTO schedule = new MaintenanceScheduleDTO(
                    Integer.parseInt(componentId),
                    vehicleId,
                    taskDescription,
                    LocalDate.parse(plannedDate),
                    Enum_MaintenanceProgressStatus.Scheduled,
                    Integer.parseInt(technicianId)
            );

            MaintenanceScheduleDAO dao = new MaintenanceScheduleDAOImp();
            if (dao.add(schedule)) {
                session.setAttribute("successMessage", "Maintenance schedule added successfully!");
            } else {
                session.setAttribute("formErrors", Map.of("generalError", "Failed to add schedule. Please try again."));
            }
        } catch (Exception e) {
            session.setAttribute("formErrors", Map.of("generalError", "Error: " + e.getMessage()));
        }

        response.sendRedirect("ScheduleMaintenance");
    }

    // ---------- Utility Methods ----------
    private String safeString(String value) {
        return value != null ? value.trim() : "";
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> getSessionMap(HttpSession session, String key) {
        if (session != null && session.getAttribute(key) instanceof Map) {
            return (Map<String, String>) session.getAttribute(key);
        }
        return new HashMap<>();
    }

    private void printTextField(PrintWriter out, String label, String name,
                                Map<String, String> values, Map<String, String> errors) {
        out.println("<div class='form-group'>");
        out.println("<label for='" + name + "'>" + label + "</label>");
        out.println("<input type='text' id='" + name + "' name='" + name + "' value='" +
                values.getOrDefault(name, "") + "'>");
        if (errors.containsKey(name + "Error")) {
            out.println("<div class='error-message'>" + errors.get(name + "Error") + "</div>");
        }
        out.println("</div>");
    }

    private void printDateField(PrintWriter out, String label, String name,
                                Map<String, String> values, Map<String, String> errors) {
        out.println("<div class='form-group'>");
        out.println("<label for='" + name + "'>" + label + "</label>");
        out.println("<input type='date' id='" + name + "' name='" + name + "' value='" +
                values.getOrDefault(name, "") + "'>");
        if (errors.containsKey(name + "Error")) {
            out.println("<div class='error-message'>" + errors.get(name + "Error") + "</div>");
        }
        out.println("</div>");
    }
}
