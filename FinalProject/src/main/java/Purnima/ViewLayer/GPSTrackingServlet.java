/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Purnima Purnima
 * Student Id: 041161250
 * Professor Name: Teddy Yap
 */
package Purnima.ViewLayer;

import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ZhiruXie.DataAccessLayer.DataSource;
import Purnima.DTO.GPSTrackingDTO;
import Purnima.BusinessLayer.GPSTrackingService;
import Purnima.BusinessLayer.ServiceResult;

// IMPORTS FOR REPORTS
import Purnima.DataAccessLayer.LiveTrackingDAO;
import Purnima.DataAccessLayer.LiveTrackingDAOImpl;
import Purnima.DTO.LiveTrackingDTO;

import Purnima.DataAccessLayer.ServiceLogDAO;
import Purnima.DataAccessLayer.ServiceLogDAOImpl;
import Purnima.DTO.ServiceLogDTO;

import Purnima.DataAccessLayer.StationLogDAO;
import Purnima.DataAccessLayer.StationLogDAOImpl;
import Purnima.DTO.StationLogDTO;

// NEW IMPORTS FOR TRANSIT VEHICLES REPORT
import ZhiruXie.DataAccessLayer.VehicleDAO;
import ZhiruXie.DataAccessLayer.VehicleDAOImp;
import ZhiruXie.DTO.VehicleDTO;

/**
 * GPSTrackingServlet handles all GPS tracking related requests.
 * This includes logging location updates, station arrivals/departures, and
 * breaks, as well as serving the main dashboard and various reports.
 *
 * @author Purnima
 * @version 2.0
 */
public class GPSTrackingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(GPSTrackingServlet.class.getName());

    /**
     * Handles HTTP GET requests. This method routes the user to different pages
     * based on the "action" parameter, such as the dashboard, logging form, or
     * various reports.
     *
     * @param request  the HttpServletRequest object that contains the request the client has made of the servlet
     * @param response the HttpServletResponse object that contains the response the servlet sends to the client
     * @throws ServletException if the request for the GET could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("GPSTrackingServlet doGet() called.");

        HttpSession session = request.getSession(false);
        String userType = (String) session.getAttribute("userType");
        Object userIdObj = session.getAttribute("userId");

        if (userType == null || userIdObj == null) {
            LOGGER.info("Redirecting to login - missing session data.");
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        LOGGER.log(Level.INFO, "Action parameter: {0}", action);

        if (action == null || "dashboard".equals(action)) {
            showDashboard(request, response);
        } else if ("log".equals(action)) {
            showLogForm(request, response);
        } else if ("viewHistory".equals(action)) {
            showErrorPage(request, response, "View History is not yet implemented.");
        } else if ("viewStationLogs".equals(action)) {
            if ("MANAGER".equals(userType) || "OPERATOR".equals(userType)) {
                viewStationLogs(request, response);
            } else {
                showErrorPage(request, response, "Access denied. Only managers and operators can view this report.");
            }
        } else if ("viewLiveTrackingReport".equals(action)) {
            if ("MANAGER".equals(userType) || "OPERATOR".equals(userType)) {
                viewLiveTrackingReport(request, response);
            } else {
                showErrorPage(request, response, "Access denied. Only managers and operators can view this report.");
            }
        } else if ("viewServiceLogReport".equals(action)) {
            if ("MANAGER".equals(userType)) {
                viewServiceLogReport(request, response);
            } else {
                showErrorPage(request, response, "Access denied. Only managers can view this report.");
            }
        } else if ("viewTransitVehiclesReport".equals(action)) {
            if ("MANAGER".equals(userType) || "OPERATOR".equals(userType)) {
                viewTransitVehiclesReport(request, response);
            } else {
                showErrorPage(request, response, "Access denied. Only managers and operators can view this report.");
            }
        } else {
            LOGGER.warning("Invalid action: " + action);
            showErrorPage(request, response, "Invalid action specified.");
        }
    }

    /**
     * Handles HTTP POST requests. This method processes form submissions for
     * logging location updates, station events, and break events.
     *
     * @param request  the HttpServletRequest object that contains the request the client has made of the servlet
     * @param response the HttpServletResponse object that contains the response the servlet sends to the client
     * @throws ServletException if the request for the POST could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the POST request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("GPSTrackingServlet doPost() called.");
        HttpSession session = request.getSession();
        String userType = (String) session.getAttribute("userType");
        Object userIdObj = session.getAttribute("userId");

        if (userType == null || userIdObj == null) {
            LOGGER.info("Redirecting to login from doPost.");
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        LOGGER.log(Level.INFO, "Action parameter in doPost: {0}", action);

        if (action == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No action specified.");
            return;
        }

        try {
            switch (action) {
                case "logLocation":
                    if ("OPERATOR".equals(userType) || "MANAGER".equals(userType)) {
                        logLocationUpdate(request, response);
                    } else {
                        LOGGER.warning("Access denied for user type: " + userType);
                        request.setAttribute("errorMessage", "Access denied. Only operators can log location updates.");
                        request.getRequestDispatcher("error.jsp").forward(request, response);
                    }
                    break;
                case "logBreak":
                    if ("OPERATOR".equals(userType) || "MANAGER".equals(userType)) {
                        logBreakEvent(request, response);
                    } else {
                        LOGGER.warning("Access denied for break event.");
                        request.setAttribute("errorMessage", "Access denied. Only operators can log break events.");
                        request.getRequestDispatcher("error.jsp").forward(request, response);
                    }
                    break;
                case "logStation":
                    if ("OPERATOR".equals(userType) || "MANAGER".equals(userType)) {
                        logStationEvent(request, response);
                    } else {
                        request.setAttribute("errorMessage", "Access denied. Only operators can log station events.");
                        request.getRequestDispatcher("error.jsp").forward(request, response);
                    }
                    break;
                default:
                    LOGGER.warning("Invalid action in switch: " + action);
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action: " + action);
                    break;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in GPSTrackingServlet POST: " + e.getMessage(), e);
            request.setAttribute("errorMessage", "System error during action processing: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    /**
     * Processes the request to log a GPS location update.
     *
     * @param request  the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    private void logLocationUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Entering logLocationUpdate method.");
        HttpSession session = request.getSession();
        String vehicleIdStr = request.getParameter("vehicleId");
        String routeIdStr = request.getParameter("routeId");
        String notes = request.getParameter("notes");
        String gpsLatStr = request.getParameter("gpsLat");
        String gpsLngStr = request.getParameter("gpsLng");
        String linkedStation = request.getParameter("linkedStation");
        LOGGER.log(Level.FINE, "Received GPS coords: {0}, {1}", new Object[]{gpsLatStr, gpsLngStr});

        try {
            Object userIdObj = session.getAttribute("userId");
            int operatorId;
            if (userIdObj instanceof Integer) {
                operatorId = (Integer) userIdObj;
            } else if (userIdObj instanceof String) {
                operatorId = Integer.parseInt((String) userIdObj);
            } else {
                throw new ServletException("Invalid or missing userId in session.");
            }

            if (vehicleIdStr == null || vehicleIdStr.trim().isEmpty() ||
                gpsLatStr == null || gpsLatStr.trim().isEmpty() ||
                gpsLngStr == null || gpsLngStr.trim().isEmpty()) {
                String errorMessage = "Vehicle ID, Latitude, and Longitude are required.";
                LOGGER.warning(errorMessage);
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
                    LOGGER.warning(errorMessage);
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
                LOGGER.log(Level.INFO, "DTO prepared for logging with GPS coords: {0}, {1}", new Object[]{gpsLat, gpsLng});

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
            LOGGER.log(Level.WARNING, errorMessage, e);
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("gpsLogForm.jsp").forward(request, response);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error logging location update: " + e.getMessage(), e);
            request.setAttribute("errorMessage", "System error during location logging: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    /**
     * Processes the request to log a station event (arrival or departure).
     *
     * @param request  the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    private void logStationEvent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Entering logStationEvent method.");
        HttpSession session = request.getSession();
        String vehicleIdStr = request.getParameter("vehicleId");
        String stationIdStr = request.getParameter("stationId");
        String eventTypeFromForm = request.getParameter("eventType");

        LOGGER.log(Level.INFO, "Vehicle ID: {0}, Station ID: {1}, Event Type: {2}",
                new Object[]{vehicleIdStr, stationIdStr, eventTypeFromForm});

        if (vehicleIdStr == null || vehicleIdStr.trim().isEmpty() ||
            stationIdStr == null || stationIdStr.trim().isEmpty() ||
            eventTypeFromForm == null || eventTypeFromForm.trim().isEmpty()) {
            String errorMessage = "Vehicle ID, Station ID, and Event Type are required.";
            LOGGER.warning(errorMessage);
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("gpsLogForm.jsp").forward(request, response);
            return;
        }

        ServiceResult result = null;
        try (Connection conn = DataSource.getConnection("cst8288", "cst8288")) {
            GPSTrackingService gpsService = new GPSTrackingService(conn);

            if ("Station Arrival".equalsIgnoreCase(eventTypeFromForm)) {
                LOGGER.info("Calling logArrival() for vehicle " + vehicleIdStr);
                result = gpsService.logArrival(vehicleIdStr, stationIdStr);
            } else if ("Station Departure".equalsIgnoreCase(eventTypeFromForm)) {
                LOGGER.info("Calling logDeparture() for vehicle " + vehicleIdStr);
                result = gpsService.logDeparture(vehicleIdStr, stationIdStr);
            } else {
                String errorMessage = "Invalid station event type provided.";
                LOGGER.warning(errorMessage);
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
            LOGGER.log(Level.SEVERE, "Database error logging station event: " + e.getMessage(), e);
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    /**
     * Processes the request to log a break event (start or end).
     *
     * @param request  the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    private void logBreakEvent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Entering logBreakEvent method.");
        HttpSession session = request.getSession();
        String vehicleId = request.getParameter("vehicleId");
        String eventType = request.getParameter("eventType");
        String notes = request.getParameter("notes");

        Object userIdObj = session.getAttribute("userId");
        int operatorId;
        if (userIdObj instanceof Integer) {
            operatorId = (Integer) userIdObj;
        } else if (userIdObj instanceof String) {
            try {
                operatorId = Integer.parseInt((String) userIdObj);
            } catch (NumberFormatException e) {
                LOGGER.log(Level.SEVERE, "Invalid userId format in session: " + userIdObj, e);
                request.setAttribute("errorMessage", "Invalid user session data. Please log in again.");
                request.getRequestDispatcher("gpsLogForm.jsp").forward(request, response);
                return;
            }
        } else {
            LOGGER.severe("Operator ID not found in session or is wrong type.");
            request.setAttribute("errorMessage", "Error: Operator ID not found in session. Please log in again.");
            request.getRequestDispatcher("gpsLogForm.jsp").forward(request, response);
            return;
        }

        LOGGER.log(Level.INFO, "Received Vehicle ID: {0}, Event Type: {1}, Notes: {2}",
                new Object[]{vehicleId, eventType, notes});

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
                if (notes == null || notes.trim().isEmpty()) {
                    request.setAttribute("errorMessage", "Reason/Notes are required for a new break event.");
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
            } else if ("Out of Service".equals(eventType)) {
                logType = "OUT_OF_SERVICE";
                if (notes == null || notes.trim().isEmpty()) {
                    request.setAttribute("errorMessage", "Reason/Notes are required for a new out-of-service event.");
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
                logType = "BREAK";
                LOGGER.info("Calling logBreakEnd() for vehicle " + vehicleId);
                result = gpsService.logBreakEnd(vehicleId, operatorId, logType);
            } else {
                request.setAttribute("errorMessage", "Invalid break event type specified.");
                request.getRequestDispatcher("gpsLogForm.jsp").forward(request, response);
                return;
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
            LOGGER.log(Level.SEVERE, "Database error logging break event: " + e.getMessage(), e);
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    /**
     * Prepares and forwards to the dashboard page, including active vehicles
     * and recent tracking activity.
     *
     * @param request  the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    private void showDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Preparing dashboard data.");

        try (Connection conn = DataSource.getConnection("cst8288", "cst8288")) {
            List<VehicleTrackingInfo> activeVehicles = getActiveVehiclesWithDetails(conn);
            List<RecentTrackingActivity> recentTracking = getRecentTrackingActivities(conn);

            request.setAttribute("activeVehicles", activeVehicles);
            request.setAttribute("recentTracking", recentTracking);

            LOGGER.log(Level.INFO, "Active vehicles count: {0}, Recent tracking count: {1}",
                    new Object[]{activeVehicles.size(), recentTracking.size()});

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error preparing dashboard data: " + e.getMessage(), e);
            request.setAttribute("errorMessage", "Unable to load dashboard data: " + e.getMessage());
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("gpsDashboard.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Forwards to the GPS log form page.
     *
     * @param request  the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    private void showLogForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Forwarding to gpsLogForm.jsp");
        RequestDispatcher dispatcher = request.getRequestDispatcher("gpsLogForm.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Forwards to an error page with a specific error message.
     *
     * @param request  the HttpServletRequest
     * @param response the HttpServletResponse
     * @param message  the error message to display
     * @throws ServletException
     * @throws IOException
     */
    private void showErrorPage(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        request.setAttribute("errorMessage", message);
        RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Inner class to hold vehicle tracking information for dashboard display.
     */
    public static class VehicleTrackingInfo {
        private String vehicleId;
        private String registrationNumber;
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
        public String getRegistrationNumber() { return registrationNumber; }
        public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }
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
     * Helper method to get active vehicles with their details.
     *
     * @param conn the database connection
     * @return a list of VehicleTrackingInfo objects
     */
    private List<VehicleTrackingInfo> getActiveVehiclesWithDetails(Connection conn) {
        List<VehicleTrackingInfo> activeVehicles = new ArrayList<>();

        String sql = """
            SELECT DISTINCT
                v.id as vehicleId,
                v.registration_number as vehicleNumber,
                v.registration_number as registration_number,
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
                info.setRegistrationNumber(rs.getString("vehicleNumber"));
                info.setRouteName(rs.getString("routeName"));
                info.setStationName(rs.getString("stationName"));
                info.setEventType(rs.getString("eventType"));
                info.setTimestamp(rs.getTimestamp("recorded_at"));
                info.setGpsLat(rs.getDouble("gps_lat")); // Added missing GPS Lat/Lng retrieval
                info.setGpsLng(rs.getDouble("gps_lng")); // Added missing GPS Lat/Lng retrieval
                activeVehicles.add(info);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting active vehicles: " + e.getMessage(), e);
        }

        return activeVehicles;
    }

    /**
     * Inner class to hold recent tracking activity information for dashboard display.
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
     * Helper method to get recent tracking activities from all relevant logs.
     *
     * @param conn the database connection
     * @return a list of RecentTrackingActivity objects, sorted by timestamp
     */
    private List<RecentTrackingActivity> getRecentTrackingActivities(Connection conn) {
        List<RecentTrackingActivity> activities = new ArrayList<>();

        String gpsSQL = """
            SELECT
                v.registration_number as vehicleNumber,
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

        String stationSQL = """
            SELECT
                v.registration_number as vehicleNumber,
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

        String serviceSQL = """
            SELECT
                v.registration_number as vehicleNumber,
                CASE
                    WHEN srv.log_type = 'BREAK' AND srv.break_end IS NULL THEN 'Break Start'
                    WHEN srv.log_type = 'BREAK' AND srv.break_end IS NOT NULL THEN 'Break End'
                    WHEN srv.log_type = 'OUT_OF_SERVICE' THEN 'Out of Service'
                    ELSE srv.log_type
                END as eventType,
                COALESCE(srv.break_end, srv.break_start) as timestamp,
                NULL as stationName,
                srv.notes as notes
            FROM service_logs srv
            JOIN transit_vehicles v ON srv.vehicle_ref = v.id
            WHERE srv.break_start >= DATE_SUB(NOW(), INTERVAL 24 HOUR)
            ORDER BY srv.break_start DESC
            LIMIT 10
            """;

        try {
            executeAndAddActivities(conn, gpsSQL, activities);
            executeAndAddActivities(conn, stationSQL, activities);
            executeAndAddActivities(conn, serviceSQL, activities);

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
            LOGGER.log(Level.SEVERE, "Error getting recent activities: " + e.getMessage(), e);
        }

        return activities;
    }

    /**
     * Helper method to execute SQL and add activities to the list.
     *
     * @param conn       the database connection
     * @param sql        the SQL query
     * @param activities the list to add activities to
     * @throws SQLException
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

    /**
     * Handles the request to view the Live Tracking Report.
     * This method retrieves all live tracking data and forwards it to a JSP.
     *
     * @param request  the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    private void viewLiveTrackingReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Generating Live Tracking Report.");
        try (Connection conn = DataSource.getConnection("cst8288", "cst8288")) {
            LiveTrackingDAO liveTrackingDAO = new LiveTrackingDAOImpl(conn);
            List<LiveTrackingDTO> liveTracks = liveTrackingDAO.getAllLiveTracking();
            request.setAttribute("liveTrackingEntries", liveTracks);
            RequestDispatcher dispatcher = request.getRequestDispatcher("liveTrackingReport.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error retrieving live tracking data: " + e.getMessage(), e);
            showErrorPage(request, response, "Database error: " + e.getMessage());
        }
    }

    /**
     * Handles the request to view the Service Log Report.
     * This method retrieves all service log data and forwards it to a JSP.
     *
     * @param request  the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    private void viewServiceLogReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Generating Service Log Report.");
        try (Connection conn = DataSource.getConnection("cst8288", "cst8288")) {
            ServiceLogDAO serviceLogDAO = new ServiceLogDAOImpl(conn);
            List<ServiceLogDTO> serviceLogs = serviceLogDAO.getAllServiceLogs();
            request.setAttribute("serviceLogs", serviceLogs);
            RequestDispatcher dispatcher = request.getRequestDispatcher("serviceLogReport.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error retrieving service log data: " + e.getMessage(), e);
            showErrorPage(request, response, "Database error: " + e.getMessage());
        }
    }

    /**
     * Handles the request to view the Station Logs Report.
     * This method retrieves all station log data and forwards it to a JSP.
     *
     * @param request  the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    private void viewStationLogs(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Generating Station Logs Report.");
        try (Connection conn = DataSource.getConnection("cst8288", "cst8288")) {
            StationLogDAO stationLogDAO = new StationLogDAOImpl(conn);
            List<StationLogDTO> stationLogs = stationLogDAO.getAllStationLogs();
            request.setAttribute("stationLogs", stationLogs);
            RequestDispatcher dispatcher = request.getRequestDispatcher("stationLogsReport.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error retrieving station log data: " + e.getMessage(), e);
            showErrorPage(request, response, "Database error: " + e.getMessage());
        }
    }

    /**
     * Handles the request to view the Transit Vehicles Report.
     * This method retrieves all vehicle data and forwards it to a JSP.
     *
     * @param request  the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    private void viewTransitVehiclesReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Generating Transit Vehicles Report.");
        try (Connection conn = DataSource.getConnection("cst8288", "cst8288")) {
            VehicleDAO vehicleDAO = new VehicleDAOImp();
            List<VehicleDTO> vehicles = vehicleDAO.getAll();
            request.setAttribute("vehicles", vehicles);
            RequestDispatcher dispatcher = request.getRequestDispatcher("transitVehiclesReport.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error retrieving vehicle data: " + e.getMessage(), e);
            showErrorPage(request, response, "Database error: " + e.getMessage());
        }
    }
}
