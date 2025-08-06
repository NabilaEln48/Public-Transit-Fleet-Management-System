package ZhiruXie.ViewLayer;

import ZhiruXie.BusinessLayer.ServiceResult;
import ZhiruXie.BusinessLayer.GPSLogService;
import ZhiruXie.DTO.GPSLogDTO;
import ZhiruXie.DataAccessLayer.DataSource;
import ZhiruXie.DataAccessLayer.GPSTrackingDAO;
import ZhiruXie.DataAccessLayer.GPSTrackingDAOImpl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * GPS Log Servlet for handling GPS log operations
 * Handles logging GPS events and retrieving GPS log history
 * @author Your Name
 */

@WebServlet(name = "GPSLogServlet", urlPatterns = {"/GPSLogServlet"})
public class GPSLogServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(GPSLogServlet.class.getName());
    
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String userType = (String) session.getAttribute("userType");
        String userId = (String) session.getAttribute("userId");
        
        if (userType == null || userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "list":
                    listGPSLogs(request, response);
                    break;
                case "view":
                    viewGPSLog(request, response);
                    break;
                case "vehicle":
                    getVehicleGPSLogs(request, response);
                    break;
                case "export":
                    if ("Manager".equals(userType)) {
                        exportGPSLogs(request, response);
                    } else {
                        request.setAttribute("errorMessage", "Access denied. Only managers can export GPS data.");
                        request.getRequestDispatcher("error.jsp").forward(request, response);
                    }
                    break;
                default:
                    listGPSLogs(request, response);
                    break;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in GPSLogServlet", e);
            request.setAttribute("errorMessage", "System error: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String userType = (String) session.getAttribute("userType");
        String userId = (String) session.getAttribute("userId");
        
        if (userType == null || userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No action specified");
            return;
        }

        try {
            switch (action) {
                case "log":
                    if ("Operator".equals(userType) || "Manager".equals(userType)) {
                        logGPSEvent(request, response);
                    } else {
                        request.setAttribute("errorMessage", "Access denied. Only operators can log GPS events.");
                        request.getRequestDispatcher("error.jsp").forward(request, response);
                    }
                    break;
                case "bulk":
                    if ("Manager".equals(userType)) {
                        bulkLogGPSEvents(request, response);
                    } else {
                        request.setAttribute("errorMessage", "Access denied. Only managers can perform bulk operations.");
                        request.getRequestDispatcher("error.jsp").forward(request, response);
                    }
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                    break;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in GPSLogServlet POST", e);
            request.setAttribute("errorMessage", "System error: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private void listGPSLogs(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String dateFrom = request.getParameter("dateFrom");
        String dateTo = request.getParameter("dateTo");
        String vehicleIdStr = request.getParameter("vehicleId"); // This is a String
        String limitStr = request.getParameter("limit");
    // --- START DEBUG LINES ---
    System.out.println("--- GPSLogServlet Debugging ---");
    System.out.println("Action: listGPSLogs");
    System.out.println("vehicleIdStr (from request): '" + vehicleIdStr + "'");
    System.out.println("dateFrom (from request): '" + dateFrom + "'");
    System.out.println("dateTo (from request): '" + dateTo + "'");
    System.out.println("limitStr (from request): '" + limitStr + "'");
    
    boolean isVehicleIdPresent = (vehicleIdStr != null && !vehicleIdStr.trim().isEmpty());
    System.out.println("isVehicleIdPresent: " + isVehicleIdPresent);
    // --- END DEBUG LINES ---

        try (Connection con = DataSource.getConnection("cst8288", "cst8288")) {
            GPSTrackingDAO gpsDAO = new GPSTrackingDAOImpl(con);
            GPSLogService gpsLogService = new GPSLogService(gpsDAO);
            List<GPSLogDTO> gpsLogs;
            
            int limit = 50;
            if (limitStr != null && !limitStr.trim().isEmpty()) {
                try {
                    limit = Integer.parseInt(limitStr);
                } catch (NumberFormatException e) {
                    limit = 50; // Use a default value on error
                }
            }

            if (vehicleIdStr != null && !vehicleIdStr.trim().isEmpty()) {
                // *** CRITICAL CHANGE: Pass vehicleIdStr directly as a String ***
                gpsLogs = gpsLogService.getGPSLogsByVehicle(vehicleIdStr, dateFrom, dateTo, limit);
            } else {
                gpsLogs = gpsLogService.getAllGPSLogs(dateFrom, dateTo, limit);
            }

            request.setAttribute("gpsLogs", gpsLogs);
            request.setAttribute("dateFrom", dateFrom);
            request.setAttribute("dateTo", dateTo);
            request.setAttribute("vehicleId", vehicleIdStr); // Keep as String for JSP
            request.setAttribute("limit", limit);
            
            request.getRequestDispatcher("gpsLogList.jsp").forward(request, response);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error listing GPS logs", e);
            throw new ServletException("Error retrieving GPS logs", e);
        }
    }

    private void viewGPSLog(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String logIdStr = request.getParameter("id");
        if (logIdStr == null || logIdStr.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "GPS Log ID is required");
            return;
        }

        try {
            int logId = Integer.parseInt(logIdStr); // This is still an int, assuming log ID is numeric
            
            try (Connection conn = DataSource.getConnection("cst8288", "cst8288")) {
                GPSTrackingDAO gpsDAO = new GPSTrackingDAOImpl(conn);
                GPSLogService gpsLogService = new GPSLogService(gpsDAO);
                
                // Note: The getGPSLogById method must exist in your GPSLogService and DAO.
                GPSLogDTO gpsLog = gpsLogService.getGPSLogById(logId);
                
                if (gpsLog != null) {
                    request.setAttribute("gpsLog", gpsLog);
                    request.getRequestDispatcher("gpsLogDetails.jsp").forward(request, response);
                } else {
                    request.setAttribute("errorMessage", "GPS log not found");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                }
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid GPS log ID format. Must be a number.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error viewing GPS log", e);
            throw new ServletException("Error retrieving GPS log details", e);
        }
    }


    private void getVehicleGPSLogs(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String vehicleIdStr = request.getParameter("vehicleId"); // This is a String
        if (vehicleIdStr == null || vehicleIdStr.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Vehicle ID is required");
            return;
        }

        try {
            // *** CRITICAL CHANGE: Remove Integer.parseInt(vehicleIdStr) here ***
            // String vehicleId = vehicleIdStr; // No parsing needed, it's already a String
            String dateFrom = request.getParameter("dateFrom");
            String dateTo = request.getParameter("dateTo");
            
            try (Connection con = DataSource.getConnection("cst8288", "cst8288")) {
                GPSTrackingDAO gpsDAO = new GPSTrackingDAOImpl(con);
                GPSLogService gpsLogService = new GPSLogService(gpsDAO);
                // *** CRITICAL CHANGE: Pass vehicleIdStr directly as a String ***
                List<GPSLogDTO> vehicleGPSLogs = gpsLogService.getGPSLogsByVehicle(vehicleIdStr, dateFrom, dateTo, 100);
                
                request.setAttribute("vehicleGPSLogs", vehicleGPSLogs);
                request.setAttribute("vehicleId", vehicleIdStr); // Keep as String for JSP
                request.setAttribute("dateFrom", dateFrom);
                request.setAttribute("dateTo", dateTo);
                
                request.getRequestDispatcher("vehicleGPSLogs.jsp").forward(request, response);
            }
        } catch (Exception e) { // Catching generic Exception for simplicity, consider more specific catches
            LOGGER.log(Level.SEVERE, "Error getting vehicle GPS logs", e);
            request.setAttribute("errorMessage", "Error retrieving vehicle GPS logs: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }


    private void logGPSEvent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String vehicleIdStr = request.getParameter("vehicleId");
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");
        String eventType = request.getParameter("eventType");
        String notes = request.getParameter("notes");
        
        HttpSession session = request.getSession();
        String operatorIdStr = (String) session.getAttribute("userId");

        if (vehicleIdStr == null || vehicleIdStr.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Vehicle ID is required");
            request.getRequestDispatcher("gpsLogForm.jsp").forward(request, response);
            return;
        }

        try {
            // vehicleId is already a String, no change needed here
            String vehicleId = vehicleIdStr; 
            int operatorId = Integer.parseInt(operatorIdStr); // Assuming operatorId is always an int
            
            Double lat = null, lng = null;
            if (latitude != null && !latitude.trim().isEmpty()) {
                lat = Double.parseDouble(latitude);
            }
            if (longitude != null && !longitude.trim().isEmpty()) {
                lng = Double.parseDouble(longitude);
            }

            try (Connection con = DataSource.getConnection("cst8288", "cst8288")) {
                GPSTrackingDAO gpsDAO = new GPSTrackingDAOImpl(con);
                GPSLogService gpsLogService = new GPSLogService(gpsDAO);
                
                GPSLogDTO gpsLog = new GPSLogDTO();
                gpsLog.setVehicleId(vehicleId);
                gpsLog.setOperatorId(operatorId);
                gpsLog.setLatitude(lat);
                gpsLog.setLongitude(lng);
                gpsLog.setEventType(eventType);
                gpsLog.setTimestamp(new Timestamp(System.currentTimeMillis()));
                gpsLog.setNotes(notes);

                ServiceResult result = gpsLogService.logGPSEvent(gpsLog);

                if (result.isSuccess()) {
                    request.setAttribute("successMessage", "GPS event logged successfully!");
                    response.sendRedirect("GPSLogServlet?action=list");
                } else {
                    request.setAttribute("errorMessage", result.getMessage());
                    request.getRequestDispatcher("gpsLogForm.jsp").forward(request, response);
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Database error logging GPS event", e);
                throw new ServletException("Database error: " + e.getMessage(), e);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid number format in form data (e.g., latitude, longitude, operator ID).");
            request.getRequestDispatcher("gpsLogForm.jsp").forward(request, response);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error logging GPS event", e);
            request.setAttribute("errorMessage", "System error during GPS logging: " + e.getMessage());
            request.getRequestDispatcher("gpsLogForm.jsp").forward(request, response);
        }
    }

    private void bulkLogGPSEvents(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            String bulkData = request.getParameter("bulkData");
            
            if (bulkData == null || bulkData.trim().isEmpty()) {
                request.setAttribute("errorMessage", "Bulk data is required");
                request.getRequestDispatcher("bulkGPSLogForm.jsp").forward(request, response);
                return;
            }
                
            try (Connection con = DataSource.getConnection("cst8288", "cst8288")) {
                GPSTrackingDAO gpsDAO = new GPSTrackingDAOImpl(con);
                GPSLogService gpsLogService = new GPSLogService(gpsDAO);
                ServiceResult result = gpsLogService.bulkLogGPSEvents(bulkData);

                if (result.isSuccess()) {
                    request.setAttribute("successMessage", "Bulk GPS events logged successfully!");
                    response.sendRedirect("GPSLogServlet?action=list");
                } else {
                    request.setAttribute("errorMessage", result.getMessage());
                    request.getRequestDispatcher("bulkGPSLogForm.jsp").forward(request, response);
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Database error during bulk GPS logging", e);
                throw new ServletException("Database error: " + e.getMessage(), e);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in bulk GPS logging", e);
            request.setAttribute("errorMessage", "System error during bulk GPS logging: " + e.getMessage());
            request.getRequestDispatcher("bulkGPSLogForm.jsp").forward(request, response);
        }
    }

    private void exportGPSLogs(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dateFrom = request.getParameter("dateFrom");
        String dateTo = request.getParameter("dateTo");
        String vehicleIdStr = request.getParameter("vehicleId"); // This is a String
        String format = request.getParameter("format");
        
        if (format == null) {
            format = "csv";
        }

        try (Connection con = DataSource.getConnection("cst8288", "cst8288")) {
            GPSTrackingDAO gpsDAO = new GPSTrackingDAOImpl(con);
            GPSLogService gpsLogService = new GPSLogService(gpsDAO);
            List<GPSLogDTO> gpsLogs;

            if (vehicleIdStr != null && !vehicleIdStr.trim().isEmpty()) {
                // *** CRITICAL CHANGE: Pass vehicleIdStr directly as a String ***
                // The service method should also accept a String for vehicleId
                gpsLogs = gpsLogService.getGPSLogsByVehicle(vehicleIdStr, dateFrom, dateTo, Integer.MAX_VALUE);
            } else {
                gpsLogs = gpsLogService.getAllGPSLogs(dateFrom, dateTo, Integer.MAX_VALUE);
            }
            
            String fileName = "gps_logs_" + System.currentTimeMillis() + "." + format;
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

            if ("csv".equalsIgnoreCase(format)) {
                exportAsCSV(gpsLogs, response);
            } else if ("json".equalsIgnoreCase(format)) {
                exportAsJSON(gpsLogs, response);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unsupported export format");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error exporting GPS logs", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error exporting GPS logs");
        }
    }

    private void exportAsCSV(List<GPSLogDTO> gpsLogs, HttpServletResponse response) throws IOException {
        StringBuilder csv = new StringBuilder();
        csv.append("ID,Vehicle ID,Operator ID,Latitude,Longitude,Event Type,Timestamp,Notes,Assigned Route,Linked Station\n"); // Added new columns
        
        for (GPSLogDTO log : gpsLogs) {
            csv.append(log.getId()).append(",")
               .append(log.getVehicleId()).append(",")
               .append(log.getOperatorId()).append(",")
               .append(log.getLatitude() != null ? log.getLatitude() : "").append(",")
               .append(log.getLongitude() != null ? log.getLongitude() : "").append(",")
               .append(log.getEventType()).append(",")
               .append(log.getTimestamp()).append(",")
               .append(log.getNotes() != null ? escapeCsv(log.getNotes()) : "").append(",") // Escaping notes
               .append(log.getAssignedRoute() != null ? escapeCsv(log.getAssignedRoute()) : "").append(",") // Added
               .append(log.getLinkedStation() != null ? escapeCsv(log.getLinkedStation()) : "").append("\n"); // Added
        }
        
        response.getWriter().write(csv.toString());
    }

    private void exportAsJSON(List<GPSLogDTO> gpsLogs, HttpServletResponse response) throws IOException {
        StringBuilder json = new StringBuilder();
        json.append("[\n");
        
        for (int i = 0; i < gpsLogs.size(); i++) {
            GPSLogDTO log = gpsLogs.get(i);
            json.append("  {\n")
                .append("    \"id\": ").append(log.getId()).append(",\n")
                .append("    \"vehicleId\": \"").append(escapeJson(log.getVehicleId())).append("\",\n")
                .append("    \"operatorId\": ").append(log.getOperatorId()).append(",\n")
                .append("    \"latitude\": ").append(log.getLatitude() != null ? log.getLatitude() : "null").append(",\n")
                .append("    \"longitude\": ").append(log.getLongitude() != null ? log.getLongitude() : "null").append(",\n")
                .append("    \"eventType\": \"").append(escapeJson(log.getEventType())).append("\",\n")
                .append("    \"timestamp\": \"").append(log.getTimestamp() != null ? log.getTimestamp() : "").append("\",\n")
                .append("    \"notes\": \"").append(escapeJson(log.getNotes())).append("\",\n") // Added escapeJson
                .append("    \"assignedRoute\": \"").append(escapeJson(log.getAssignedRoute())).append("\",\n") // Added
                .append("    \"linkedStation\": \"").append(escapeJson(log.getLinkedStation())).append("\"\n") // Added
                .append("  }");
            
            if (i < gpsLogs.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }
        
        json.append("]");
        response.getWriter().write(json.toString());
    }

    // Helper method for CSV escaping
    private String escapeCsv(String value) {
        if (value == null) return "";
        String escaped = value.replace("\"", "\"\""); // Escape double quotes
        if (escaped.contains(",") || escaped.contains("\"") || escaped.contains("\n") || escaped.contains("\r")) {
            return "\"" + escaped + "\""; // Enclose in double quotes if it contains special characters
        }
        return escaped;
    }

    // Helper method for JSON escaping
    private String escapeJson(String value) {
        if (value == null) return "";
        return value.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t");
    }
}