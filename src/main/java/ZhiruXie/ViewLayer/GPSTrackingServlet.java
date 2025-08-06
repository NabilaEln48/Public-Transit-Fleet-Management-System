package ZhiruXie.ViewLayer;

import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ZhiruXie.DataAccessLayer.DataSource;
import ZhiruXie.DTO.GPSTrackingDTO;
import ZhiruXie.BusinessLayer.GPSTrackingService;
import ZhiruXie.BusinessLayer.ServiceResult;

// IMPORTS FOR REPORTS
import ZhiruXie.DataAccessLayer.LiveTrackingDAO;
import ZhiruXie.DataAccessLayer.LiveTrackingDAOImpl;
import ZhiruXie.DTO.LiveTrackingDTO;

import ZhiruXie.DataAccessLayer.ServiceLogDAO;
import ZhiruXie.DataAccessLayer.ServiceLogDAOImpl;
import ZhiruXie.DTO.ServiceLogDTO;

import ZhiruXie.DataAccessLayer.StationLogDAO;
import ZhiruXie.DataAccessLayer.StationLogDAOImpl;
import ZhiruXie.DTO.StationLogDTO;

// NEW IMPORTS FOR TRANSIT VEHICLES REPORT
import ZhiruXie.DataAccessLayer.VehicleDAO; // Using your VehicleDAO
import ZhiruXie.DataAccessLayer.VehicleDAOImp; // Using your VehicleDAOImp
import ZhiruXie.DTO.VehicleDTO; // Using your VehicleDTO


public class GPSTrackingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(GPSTrackingServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("DEBUG: doGet() from new code version.");
        System.out.println("=== DEBUG: GPSTrackingServlet doGet() called ===");
        System.out.println("=== NEW LINE: This message should appear in server logs ===");
        
        HttpSession session = request.getSession(false); // don't create a new session if one doesn't exist
        String userType = (String) session.getAttribute("userType");
        Object userIdObj = session.getAttribute("userId");

        if (userType == null || userIdObj == null) {
            System.out.println("DEBUG: Redirecting to login - missing session data");
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        System.out.println("DEBUG: Action parameter: " + action);
        
        if (action == null || "dashboard".equals(action)) {
            System.out.println("DEBUG: Showing dashboard");
            showDashboard(request, response);
        } else if ("log".equals(action)) {
            System.out.println("DEBUG: Showing log form");
            showLogForm(request, response);
        } else if ("viewHistory".equals(action)) {
            System.out.println("DEBUG: View history requested - not implemented");
            showErrorPage(request, response, "View History is not yet implemented.");
        } else if ("viewStationLogs".equals(action)) {
            System.out.println("DEBUG: Showing station logs report");
            if ("MANAGER".equals(userType) || "OPERATOR".equals(userType)) {
                viewStationLogs(request, response);
            } else {
                showErrorPage(request, response, "Access denied. Only managers and operators can view this report.");
            }
        } else if ("viewLiveTrackingReport".equals(action)) {
            System.out.println("DEBUG: Showing Live Tracking Report");
            if ("MANAGER".equals(userType) || "OPERATOR".equals(userType)) {
                viewLiveTrackingReport(request, response);
            } else {
                showErrorPage(request, response, "Access denied. Only managers and operators can view this report.");
            }
        } else if ("viewServiceLogReport".equals(action)) {
            System.out.println("DEBUG: Showing Service Log Report");
            if ("MANAGER".equals(userType)) {
                viewServiceLogReport(request, response);
            } else {
                showErrorPage(request, response, "Access denied. Only managers can view this report.");
            }
        } else if ("viewTransitVehiclesReport".equals(action)) { 
            System.out.println("DEBUG: Showing Transit Vehicles Report");
            if ("MANAGER".equals(userType) || "OPERATOR".equals(userType)) { 
                viewTransitVehiclesReport(request, response); 
            } else {
                showErrorPage(request, response, "Access denied. Only managers and operators can view this report.");
            }
        }
        else { 
            System.out.println("DEBUG: Invalid action: " + action);
            showErrorPage(request, response, "Invalid action specified.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("=== DEBUG: GPSTrackingServlet doPost() called ===");
        HttpSession session = request.getSession();
        String userType = (String) session.getAttribute("userType");
        Object userIdObj = session.getAttribute("userId");

        if (userType == null || userIdObj == null) {
            System.out.println("DEBUG: Redirecting to login from doPost");
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        System.out.println("DEBUG in doPost: action parameter is '" + action + "'");

        if (action == null) {
            System.out.println("DEBUG: No action specified in POST");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No action specified");
            return;
        }

        try {
            switch (action) {
                case "logLocation":
                    System.out.println("DEBUG: Processing logLocation action");
                    if ("OPERATOR".equals(userType) || "MANAGER".equals(userType)) {
                        logLocationUpdate(request, response);
                    } else {
                        System.out.println("DEBUG: Access denied for user type: " + userType);
                        request.setAttribute("errorMessage", "Access denied. Only operators can log location updates.");
                        request.getRequestDispatcher("error.jsp").forward(request, response);
                    }
                    break;
                case "logBreak":
                    System.out.println("DEBUG: Processing logBreak action");
                    if ("OPERATOR".equals(userType) || "MANAGER".equals(userType)) {
                        logBreakEvent(request, response);
                    } else {
                        System.out.println("DEBUG: Access denied for break event");
                        request.setAttribute("errorMessage", "Access denied. Only operators can log break events.");
                        request.getRequestDispatcher("error.jsp").forward(request, response);
                    }
                    break;
                case "logStation":
                    System.out.println("DEBUG: Processing logStation action");
                    if ("OPERATOR".equals(userType) || "MANAGER".equals(userType)) {
                        logStationEvent(request, response);
                    } else {
                        request.setAttribute("errorMessage", "Access denied. Only operators can log station events.");
                        request.getRequestDispatcher("error.jsp").forward(request, response);
                    }
                    break;
                default:
                    System.out.println("DEBUG: Invalid action in switch: " + action);
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action: " + action);
                    break;
            }
        } catch (Exception e) {
            System.err.println("SEVERE Error in GPSTrackingServlet POST: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "System error during action processing: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private void logLocationUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("DEBUG: === Entering logLocationUpdate method ===");
        HttpSession session = request.getSession();
        String vehicleIdStr = request.getParameter("vehicleId");
        String routeIdStr = request.getParameter("routeId");
        String notes = request.getParameter("notes");
        String gpsLatStr = request.getParameter("gpsLat");
        String gpsLngStr = request.getParameter("gpsLng");
        String linkedStation = request.getParameter("linkedStation");
        System.out.println("DEBUG: gpsLatStr from form: '" + gpsLatStr + "'");
        System.out.println("DEBUG: gpsLngStr from form: '" + gpsLngStr + "'");

        try {
            Object userIdObj = session.getAttribute("userId");
            int operatorId;
            if (userIdObj instanceof Integer) {
                operatorId = (Integer) userIdObj;
            } else if (userIdObj instanceof String) {
                operatorId = Integer.parseInt((String) userIdObj);
            } else {
                throw new ServletException("Invalid or missing userId in session");
            }
            if (vehicleIdStr == null || vehicleIdStr.trim().isEmpty() ||
                gpsLatStr == null || gpsLatStr.trim().isEmpty() ||
                gpsLngStr == null || gpsLngStr.trim().isEmpty()) {
                String errorMessage = "Vehicle ID, Latitude, and Longitude are required.";
                System.out.println("DEBUG: " + errorMessage);
                request.setAttribute("errorMessage", errorMessage);
                request.getRequestDispatcher("gpsLogForm.jsp").forward(request, response);
                return;
            }

            Integer routeId = null;
            if (routeIdStr != null && !routeIdStr.trim().isEmpty()) {
                try {
                    routeId = Integer.parseInt(routeIdStr.trim());
                } catch (NumberFormatException e) {
                    String errorMessage = "Invalid Route ID. Please enter a valid number.";
                    System.out.println("DEBUG: " + errorMessage);
                    request.setAttribute("errorMessage", errorMessage);
                    request.getRequestDispatcher("gpsLogForm.jsp").forward(request, response);
                    return;
                }
            }

            double gpsLat = Double.parseDouble(gpsLatStr.trim());
            double gpsLng = Double.parseDouble(gpsLngStr.trim());
            try (Connection conn = DataSource.getConnection("cst8288", "cst8288")) {
                GPSTrackingService gpsService = new GPSTrackingService(conn);
                GPSTrackingDTO tracking = new GPSTrackingDTO();
                tracking.setVehicleId(vehicleIdStr.trim());
                tracking.setOperatorId(operatorId);
                tracking.setRouteId(routeId);
                tracking.setGpsLat(gpsLat);
                tracking.setGpsLng(gpsLng);
                tracking.setEventType("Location Update");
                tracking.setRecordedAt(new Timestamp(System.currentTimeMillis()));
                tracking.setNotes(notes != null ? notes.trim() : null);
                tracking.setLinkedStation(linkedStation != null ? linkedStation.trim() : null);
                System.out.println("DEBUG: DTO prepared with GPS coords: " + gpsLat + ", " + gpsLng);

                ServiceResult result = gpsService.logGPSEvent(tracking);
                if (result.isSuccess()) {
                    session.setAttribute("successMessage", "Location update logged successfully!");
                    response.sendRedirect("GPSTrackingServlet?action=dashboard");
                } else {
                    request.setAttribute("errorMessage", result.getMessage());
                    request.getRequestDispatcher("gpsLogForm.jsp").forward(request, response);
                }
            }
        } catch (NumberFormatException e) {
            String errorMessage = "Invalid number format for latitude or longitude.";
            System.err.println("DEBUG: " + errorMessage + " " + e.getMessage());
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("gpsLogForm.jsp").forward(request, response);
        } catch (Exception e) {
            System.err.println("SEVERE Error logging location update: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "System error during location logging: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private void logStationEvent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("DEBUG: === Entering logStationEvent method ===");
        HttpSession session = request.getSession();
        String vehicleIdStr = request.getParameter("vehicleId");
        String stationIdStr = request.getParameter("stationId");
        String eventTypeFromForm = request.getParameter("eventType");

        System.out.println("DEBUG: Vehicle ID: " + vehicleIdStr);
        System.out.println("DEBUG: Station ID: " + stationIdStr);
        System.out.println("DEBUG: Event Type from form: " + eventTypeFromForm);

        if (vehicleIdStr == null || vehicleIdStr.trim().isEmpty() ||
            stationIdStr == null || stationIdStr.trim().isEmpty() ||
            eventTypeFromForm == null || eventTypeFromForm.trim().isEmpty()) {
            String errorMessage = "Vehicle ID, Station ID, and Event Type are required.";
            System.out.println("DEBUG: " + errorMessage);
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("gpsLogForm.jsp").forward(request, response);
            return;
        }
        
        ServiceResult result = null;
        try (Connection conn = DataSource.getConnection("cst8288", "cst8288")) {
            GPSTrackingService gpsService = new GPSTrackingService(conn);

            if ("Station Arrival".equalsIgnoreCase(eventTypeFromForm)) {
                System.out.println("DEBUG: Calling logArrival() for vehicle " + vehicleIdStr);
                result = gpsService.logArrival(vehicleIdStr, stationIdStr);
            } else if ("Station Departure".equalsIgnoreCase(eventTypeFromForm)) {
                System.out.println("DEBUG: Calling logDeparture() for vehicle " + vehicleIdStr);
                result = gpsService.logDeparture(vehicleIdStr, stationIdStr);
            } else {
                String errorMessage = "Invalid station event type provided.";
                System.out.println("DEBUG: " + errorMessage);
                request.setAttribute("errorMessage", errorMessage);
                request.getRequestDispatcher("gpsLogForm.jsp").forward(request, response);
                return;
            }

            if (result != null && result.isSuccess()) {
                session.setAttribute("successMessage", "Station event logged successfully!");
                response.sendRedirect("GPSTrackingServlet?action=dashboard");
            } else if (result != null) {
                request.setAttribute("errorMessage", result.getMessage());
                request.getRequestDispatcher("gpsLogForm.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "An unknown error occurred.");
                request.getRequestDispatcher("gpsLogForm.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            System.err.println("SEVERE Database error logging station event: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private void logBreakEvent(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        System.out.println("DEBUG: === Entering logBreakEvent method ===");
        HttpSession session = request.getSession();

        Object userIdObj = session.getAttribute("userId");
        int operatorId;
        if (userIdObj instanceof Integer) {
            operatorId = (Integer) userIdObj;
        } else if (userIdObj instanceof String) {
            try {
                operatorId = Integer.parseInt((String) userIdObj);
            } catch (NumberFormatException e) {
                System.err.println("Invalid userId format in session: " + userIdObj);
                request.setAttribute("errorMessage", "Invalid user session data. Please log in again.");
                request.getRequestDispatcher("gpsLogForm.jsp").forward(request, response);
                return;
            }
        } else {
            System.err.println("Operator ID not found in session or is wrong type.");
            request.setAttribute("errorMessage", "Error: Operator ID not found in session. Please log in again.");
            request.getRequestDispatcher("gpsLogForm.jsp").forward(request, response);
            return;
        }

        String vehicleId = request.getParameter("vehicleId");
        String eventType = request.getParameter("eventType");
        String notes = request.getParameter("notes");

        System.out.println("DEBUG in logBreakEvent: Received Vehicle ID: " + vehicleId);
        System.out.println("DEBUG in logBreakEvent: Received Event Type: " + eventType);
        System.out.println("DEBUG in logBreakEvent: Received Notes: " + notes);

        if (vehicleId == null || vehicleId.trim().isEmpty() || eventType == null || eventType.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Vehicle ID and Event Type are required fields.");
            request.getRequestDispatcher("gpsLogForm.jsp").forward(request, response);
            return;
        }

        try (Connection conn = DataSource.getConnection("cst8288", "cst8288")) {
            GPSTrackingService gpsService = new GPSTrackingService(conn);
            ServiceResult result = null;
            String logType = null;

            if ("Break Start".equals(eventType)) {
                logType = "BREAK";
            } else if ("Out of Service".equals(eventType)) {
                logType = "OUT_OF_SERVICE";
            } else if ("Break End".equals(eventType)) {
                logType = "BREAK";
            } else {
                request.setAttribute("errorMessage", "Invalid break event type specified.");
                request.getRequestDispatcher("gpsLogForm.jsp").forward(request, response);
                return;
            }

            if ("Break Start".equals(eventType) || "Out of Service".equals(eventType)) {
                if (notes == null || notes.trim().isEmpty()) {
                    request.setAttribute("errorMessage", "Reason/Notes are required for a new break or out-of-service event.");
                    request.getRequestDispatcher("gpsLogForm.jsp").forward(request, response);
                    return;
                }
                ServiceLogDTO serviceLog = new ServiceLogDTO();
                serviceLog.setVehicleRef(vehicleId);
                serviceLog.setLogType(logType);
                serviceLog.setOperatorRef(operatorId);
                serviceLog.setNotes(notes);
                serviceLog.setBreakStart(new Timestamp(System.currentTimeMillis()));

                result = gpsService.logServiceEvent(serviceLog);

            } else if ("Break End".equals(eventType)) {
                System.out.println("DEBUG: Calling logBreakEnd() for vehicle " + vehicleId + " with logType: " + logType);
                result = gpsService.logBreakEnd(vehicleId, operatorId, logType);
            }

            if (result != null) {
                if (result.isSuccess()) {
                    session.setAttribute("successMessage", result.getMessage());
                } else {
                    session.setAttribute("errorMessage", result.getMessage());
                }
                response.sendRedirect("GPSTrackingServlet?action=dashboard");
            }

        } catch (SQLException e) {
            System.err.println("SEVERE Database error logging break event: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
        
    private void showDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("DEBUG: Preparing dashboard data");
        
        try (Connection conn = DataSource.getConnection("cst8288", "cst8288")) {
            GPSTrackingService gpsService = new GPSTrackingService(conn);
            
            // Get active vehicles with their latest GPS data
            List<VehicleTrackingInfo> activeVehicles = getActiveVehiclesWithDetails(conn);
            
            // Get recent tracking activities (last 20 events)
            List<RecentTrackingActivity> recentTracking = getRecentTrackingActivities(conn);
            
            // Set attributes for the JSP
            request.setAttribute("activeVehicles", activeVehicles);
            request.setAttribute("recentTracking", recentTracking);
            
            System.out.println("DEBUG: Active vehicles count: " + activeVehicles.size());
            System.out.println("DEBUG: Recent tracking count: " + recentTracking.size());
            
        } catch (SQLException e) {
            System.err.println("SEVERE Error preparing dashboard data: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to load dashboard data: " + e.getMessage());
        }
        
        System.out.println("DEBUG: Forwarding to gpsDashboard.jsp");
        RequestDispatcher dispatcher = request.getRequestDispatcher("gpsDashboard.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Inner class to hold vehicle tracking information for dashboard display
     */
    public static class VehicleTrackingInfo {
        private String vehicleId;
        private String vehicleNumber;
        private String routeName;
        private double gpsLat;
        private double gpsLng;
        private Timestamp timestamp;
        private String stationName;
        private String eventType;

        // Getters and setters for all fields
        public String getVehicleId() { return vehicleId; }
        public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }
        public String getVehicleNumber() { return vehicleNumber; }
        public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }
        public String getRouteName() { return routeName; }
        public void setRouteName(String routeName) { this.routeName = routeName; }
        public double getGpsLat() { return gpsLat; }
        public void setGpsLat(double gpsLat) { this.gpsLat = gpsLat; }
        public double getGpsLng() { return gpsLng; }
        public void setGpsLng(double gpsLng) { this.gpsLng = gpsLng; }
        public Timestamp getTimestamp() { return timestamp; }
        public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }
        public String getStationName() { return stationName; }
        public void setStationName(String stationName) { this.stationName = stationName; }
        public String getEventType() { return eventType; }
        public void setEventType(String eventType) { this.eventType = eventType; }
    }

    /**
     * Helper method to get active vehicles with their details
     */
    private List<VehicleTrackingInfo> getActiveVehiclesWithDetails(Connection conn) {
        List<VehicleTrackingInfo> activeVehicles = new ArrayList<>();
        
        String sql = """
            SELECT DISTINCT 
                v.id as vehicleId,
                v.vehicle_number as vehicleNumber,
                v.assigned_route as routeName,
                lt.gps_lat,
                lt.gps_lng,
                lt.recorded_at,
                lt.linked_station as stationName,
                'Location Update' as eventType
            FROM transit_vehicles v
            LEFT JOIN live_tracking lt ON v.id = lt.vehicle_ref
            WHERE v.operational_state = 'Active'
            AND lt.recorded_at >= DATE_SUB(NOW(), INTERVAL 2 HOUR)
            ORDER BY lt.recorded_at DESC
        """;
        
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                VehicleTrackingInfo info = new VehicleTrackingInfo();
                info.setVehicleId(rs.getString("vehicleId"));
                info.setVehicleNumber(rs.getString("vehicleNumber"));
                info.setRouteName(rs.getString("routeName"));
                info.setStationName(rs.getString("stationName"));
                info.setEventType(rs.getString("eventType"));
                info.setTimestamp(rs.getTimestamp("recorded_at"));
                activeVehicles.add(info);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting active vehicles: " + e.getMessage());
            e.printStackTrace();
        }
        
        return activeVehicles;
    }

    /**
     * Inner class to hold recent tracking activity information for dashboard display
     */
    public static class RecentTrackingActivity {
        private String vehicleNumber;
        private String eventType;
        private Timestamp timestamp;
        private String stationName;
        private String notes;

        // Getters and setters
        public String getVehicleNumber() { return vehicleNumber; }
        public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }
        public String getEventType() { return eventType; }
        public void setEventType(String eventType) { this.eventType = eventType; }
        public Timestamp getTimestamp() { return timestamp; }
        public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }
        public String getStationName() { return stationName; }
        public void setStationName(String stationName) { this.stationName = stationName; }
        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }
    }

    /**
     * Helper method to get recent tracking activities
     */
    private List<RecentTrackingActivity> getRecentTrackingActivities(Connection conn) {
        List<RecentTrackingActivity> activities = new ArrayList<>();
        
        // Get recent GPS tracking events
        String gpsSQL = """
            SELECT 
                v.vehicle_number as vehicleNumber,
                'Location Update' as eventType,
                lt.recorded_at as timestamp,
                lt.linked_station as stationName,
                CONCAT('GPS coordinates: ', ROUND(lt.gps_lat, 4), ', ', ROUND(lt.gps_lng, 4)) as notes
            FROM live_tracking lt
            JOIN transit_vehicles v ON lt.vehicle_ref = v.id
            WHERE lt.recorded_at >= DATE_SUB(NOW(), INTERVAL 24 HOUR)
            ORDER BY lt.recorded_at DESC
            LIMIT 10
        """;
        
        // Get recent station events
        String stationSQL = """
            SELECT 
                v.vehicle_number as vehicleNumber,
                CASE 
                    WHEN sl.action_type = 'ARRIVAL' THEN 'Station Arrival'
                    WHEN sl.action_type = 'DEPARTURE' THEN 'Station Departure'
                    ELSE sl.action_type
                END as eventType,
                sl.logged_time as timestamp,
                rs.station_name as stationName,
                CONCAT('Station: ', rs.station_name) as notes
            FROM station_logs sl
            JOIN transit_vehicles v ON sl.vehicle_ref = v.id
            JOIN route_stations rs ON sl.station_ref = rs.id
            WHERE sl.logged_time >= DATE_SUB(NOW(), INTERVAL 24 HOUR)
            ORDER BY sl.logged_time DESC
            LIMIT 10
        """;
        
        // Get recent service events (breaks)
        String serviceSQL = """
            SELECT 
                v.vehicle_number as vehicleNumber,
                CASE 
                    WHEN srv.log_type = 'BREAK' AND srv.break_end IS NULL THEN 'Break Start'
                    WHEN srv.log_type = 'BREAK' AND srv.break_end IS NOT NULL THEN 'Break End'
                    WHEN srv.log_type = 'OUT_OF_SERVICE' THEN 'Out of Service'
                    ELSE srv.log_type
                END as eventType,
                COALESCE(srv.break_end, srv.break_start) as timestamp,
                NULL as stationName,
                CASE 
                    WHEN srv.break_end IS NULL THEN 'Break started'
                    ELSE 'Break ended'
                END as notes
            FROM service_logs srv
            JOIN transit_vehicles v ON srv.vehicle_ref = v.id
            WHERE srv.break_start >= DATE_SUB(NOW(), INTERVAL 24 HOUR)
            ORDER BY srv.break_start DESC
            LIMIT 10
        """;
        
        try {
            // Execute all queries and combine results
            executeAndAddActivities(conn, gpsSQL, activities);
            executeAndAddActivities(conn, stationSQL, activities);
            executeAndAddActivities(conn, serviceSQL, activities);
            
            // Sort by timestamp descending and limit to 20
            activities.sort((a, b) -> {
                if (a.getTimestamp() == null && b.getTimestamp() == null) return 0;
                if (a.getTimestamp() == null) return 1;
                if (b.getTimestamp() == null) return -1;
                return b.getTimestamp().compareTo(a.getTimestamp());
            });
            
            if (activities.size() > 20) {
                activities = activities.subList(0, 20);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting recent activities: " + e.getMessage());
            e.printStackTrace();
        }
        
        return activities;
    }

    /**
     * Helper method to execute SQL and add activities to the list
     */
    private void executeAndAddActivities(Connection conn, String sql, List<RecentTrackingActivity> activities)
            throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                RecentTrackingActivity activity = new RecentTrackingActivity();
                activity.setVehicleNumber(rs.getString("vehicleNumber"));
                activity.setEventType(rs.getString("eventType"));
                activity.setTimestamp(rs.getTimestamp("timestamp"));
                activity.setStationName(rs.getString("stationName"));
                activity.setNotes(rs.getString("notes"));
                activities.add(activity);
            }
        }
    }

    private void showLogForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("DEBUG: Forwarding to gpsLogForm.jsp");
        RequestDispatcher dispatcher = request.getRequestDispatcher("gpsLogForm.jsp");
        dispatcher.forward(request, response);
    }

    private void showErrorPage(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        System.out.println("DEBUG: Showing error page with message: " + message);
        request.setAttribute("errorMessage", message);
        RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
        dispatcher.forward(request, response);
    }

    // --- METHOD FOR LIVE TRACKING REPORT ---
    private void viewLiveTrackingReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("DEBUG: Entering viewLiveTrackingReport method");

        List<LiveTrackingDTO> liveTrackingLogs = new ArrayList<>();
        try (Connection conn = DataSource.getConnection("cst8288", "cst8288")) {
            LiveTrackingDAO liveTrackingDAO = new LiveTrackingDAOImpl(conn);
            liveTrackingLogs = liveTrackingDAO.getAllLiveTracking();
            
        } catch (SQLException e) {
            System.err.println("Database error fetching live tracking logs: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
        }

        request.setAttribute("liveTrackingLogs", liveTrackingLogs);
        System.out.println("DEBUG: Found " + liveTrackingLogs.size() + " live tracking logs.");
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("liveTrackingReport.jsp");
        dispatcher.forward(request, response);
    }

    // --- METHOD FOR SERVICE LOG REPORT ---
    private void viewServiceLogReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("DEBUG: Entering viewServiceLogReport method");

        List<ServiceLogDTO> serviceLogs = new ArrayList<>();
        try (Connection conn = DataSource.getConnection("cst8288", "cst8288")) {
            ServiceLogDAO serviceLogDAO = new ServiceLogDAOImpl(conn);
            serviceLogs = serviceLogDAO.getAllServiceLogs();
        } catch (SQLException e) {
            System.err.println("Database error fetching service logs: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
        }

        request.setAttribute("serviceLogs", serviceLogs);
        System.out.println("DEBUG: Found " + serviceLogs.size() + " service logs.");

        RequestDispatcher dispatcher = request.getRequestDispatcher("serviceLogReport.jsp");
        dispatcher.forward(request, response);
    }

    // --- METHOD FOR STATION LOG REPORT ---
    private void viewStationLogs(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("DEBUG: Entering viewStationLogs method");

        List<StationLogDTO> stationLogs = new ArrayList<>();
        try (Connection conn = DataSource.getConnection("cst8288", "cst8288")) {
            StationLogDAO stationLogDAO = new StationLogDAOImpl(conn);
            stationLogs = stationLogDAO.getAllStationLogs();
            
        } catch (SQLException e) {
            // Corrected line: Ensure only one double quote before the plus sign
            System.err.println("Database error fetching station logs: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
        }

        request.setAttribute("stationLogs", stationLogs);
        System.out.println("DEBUG: Found " + stationLogs.size() + " station logs.");
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("stationLogReport.jsp");
        dispatcher.forward(request, response);
    }
    
    // --- NEW METHOD FOR TRANSIT VEHICLES REPORT ---
    private void viewTransitVehiclesReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("DEBUG: Entering viewTransitVehiclesReport method");

        List<VehicleDTO> transitVehicles = new ArrayList<>();
        try (Connection conn = DataSource.getConnection("cst8288", "cst8288")) {
            // Use your VehicleDAOImp here
            VehicleDAO vehicleDAO = new VehicleDAOImp(conn);
            transitVehicles = vehicleDAO.getAll(); // Call your getAll method
            
        } catch (SQLException e) {
            System.err.println("Database error fetching transit vehicles: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
        } catch (Exception e) { // Catch any other exceptions, e.g., from enum parsing
            System.err.println("Error fetching transit vehicles: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error loading transit vehicles data: " + e.getMessage());
        }

        request.setAttribute("transitVehicles", transitVehicles); // Set attribute name for JSP
        System.out.println("DEBUG: Found " + transitVehicles.size() + " transit vehicles.");
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("transitVehiclesReport.jsp");
        dispatcher.forward(request, response);
    }
}