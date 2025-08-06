package ZhiruXie.ControllerLayer;

import ZhiruXie.BusinessLayer.AnalyticsReportBusinessLogic;
import ZhiruXie.BusinessLayer.MaintenanceScheduleBusinessLogic;
import ZhiruXie.BusinessLayer.PerformanceBusinessLogic;
import ZhiruXie.BusinessLayer.GPSTrackingBusinessLogic; // New import
import ZhiruXie.BusinessLayer.VehicleBusinessLogic;
import ZhiruXie.DTO.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Updated Frontend Controller with GPS Tracking functionality
 * @author Zhiru Xie
 */
public class FrontendController extends HttpServlet{
    
    // CORRECTED: The business logic instances are no longer initialized here.
    // They are no longer final because they will be assigned in the init() method.
    private MaintenanceScheduleBusinessLogic scheduleBusinessLogic;
    private PerformanceBusinessLogic performanceBusinessLogic;
    private AnalyticsReportBusinessLogic analysisBusinessLogic;
    private GPSTrackingBusinessLogic gpsBusinessLogic;
    
    /** Target url for new web page. */
    private String targetUrl;
    private VehicleBusinessLogic VehicleBusinessLogic;

    /**
     * Initializes the servlet and its business logic components.
     * This method is called once when the servlet is first loaded.
     * @throws ServletException if an exception occurs that prevents the servlet from initializing
     */
    @Override
    public void init() throws ServletException {
        super.init();
        try {
            // Instantiate all business logic classes inside a try-catch block
            this.scheduleBusinessLogic = new MaintenanceScheduleBusinessLogic();
            this.performanceBusinessLogic = new PerformanceBusinessLogic();
            this.analysisBusinessLogic = new AnalyticsReportBusinessLogic();
            this.gpsBusinessLogic = new GPSTrackingBusinessLogic();
            this.VehicleBusinessLogic= new VehicleBusinessLogic();
        } catch (SQLException e) {
            // If any business logic component fails to initialize due to a DB issue,
            // log the error and wrap it in a ServletException to halt deployment.
            System.err.println("Error initializing business logic components: " + e.getMessage());
            throw new ServletException("Failed to initialize FrontendController", e);
        }
    }
    
    /**
     * Destroys the servlet and closes any open resources.
     * This method is called once when the servlet is unloaded.
     */
    @Override
    public void destroy() {
        super.destroy();
        // It's a good practice to clean up resources.
        // Assuming your business logic classes have a method to close their connection.
        if (gpsBusinessLogic != null) {
            gpsBusinessLogic.closeConnection();
        }
        System.out.println("FrontendController destroyed and resources closed.");
    }
    
    /**Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        RequestDispatcher dispatcher;
        
        targetUrl = action;
        
        boolean prepareResult = false;
        try{
            prepareResult = prepareRequest(request, response, action);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        if(!prepareResult){
            return;
        }
        
        dispatcher = request.getRequestDispatcher("/" + targetUrl);        
        dispatcher.forward(request, response);
    }
    
    /**
     * Enhanced prepareRequest method with GPS tracking functionality
     */
    private boolean prepareRequest(HttpServletRequest request, HttpServletResponse response, String action) throws IOException, NullPointerException{
        switch (action){
            case "ReportDashboard" -> {
                // Existing code remains the same
            }
            case "MaintenanceDashboard" -> {
                String techIdParam = request.getParameter("technicianId");
                int technicianId;
                if (techIdParam == null || techIdParam.isBlank()) {
                    technicianId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
                }
                else{
                    try {
                        technicianId = Integer.parseInt(techIdParam);
                        request.setAttribute("operatorId", technicianId);
                    } catch (NumberFormatException e) {
                        technicianId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
                        request.setAttribute("errorMessage", "Invalid Technician Id. Please enter a number.");
                    }
                }
                List<MaintenanceScheduleDTO> schedules = scheduleBusinessLogic.getAll(technicianId);
                request.setAttribute("schedules", schedules);
            }
            case "PerformanceDashboard" -> {
                String operatorStr = request.getParameter("operatorId");
                int operatorId;
                if (operatorStr == null || operatorStr.isBlank()) {
                    operatorId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
                }
                else{
                    try {
                        operatorId = Integer.parseInt(operatorStr);
                        request.setAttribute("operatorId", operatorId);
                    } catch (NumberFormatException e) {
                        operatorId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
                        request.setAttribute("errorMessage", "Invalid Operator ID. Please enter a number.");
                    }
                }
                List<PerformanceDTO> performanceRecords = performanceBusinessLogic.getAll(operatorId);
                request.setAttribute("performanceRecords", performanceRecords);
            }
            case "CostReports" -> {
                List<CostAnalysisDTO> analysisRecords = analysisBusinessLogic.getAll();
                request.setAttribute("AnalysisRecords", analysisRecords);
            }
            case "GPSTrackingDashboard" -> {
                // Manager can view all vehicle tracking
                List<LiveTrackingDTO> allTracking = gpsBusinessLogic.getAllLiveTracking();
                request.setAttribute("trackingData", allTracking);
                targetUrl = "gps-tracking.jsp";
            }
            case "StationReports" -> {
                // View arrival/departure reports for all vehicles
                List<StationLogDTO> stationLogs = gpsBusinessLogic.getAllStationLogs();
                request.setAttribute("stationLogs", stationLogs);
                
                // Optional: Filter by vehicle if requested
                String vehicleFilter = request.getParameter("vehicleId");
                if (vehicleFilter != null && !vehicleFilter.isEmpty()) {
                    List<StationLogDTO> filteredLogs = gpsBusinessLogic.getVehicleStationLogs(vehicleFilter);
                    request.setAttribute("stationLogs", filteredLogs);
                    request.setAttribute("selectedVehicle", vehicleFilter);
                }
                targetUrl = "station-reports.jsp";
            }
            case "OperatorServiceLogs" -> {
                // Operator views their break/service logs
                int operatorId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
                List<ServiceLogDTO> serviceLogs = gpsBusinessLogic.getOperatorServiceLogs(operatorId);
                request.setAttribute("serviceLogs", serviceLogs);
                targetUrl = "service-logs.jsp";
            }
            case "RecordGPSLocation" -> {
                // Record new GPS location (typically called via AJAX)
                String vehicleId = request.getParameter("vehicleId");
                String latStr = request.getParameter("latitude");
                String lngStr = request.getParameter("longitude");
                String station = request.getParameter("station");
                
                try {
                    double latitude = Double.parseDouble(latStr);
                    double longitude = Double.parseDouble(lngStr);
                    
                    boolean success = gpsBusinessLogic.recordGPSLocation(vehicleId, latitude, longitude, station);
                    
                    response.setContentType("application/json");
                    response.getWriter().write("{\"success\": " + success + "}");
                    return false; // Don't forward to JSP
                } catch (NumberFormatException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"error\": \"Invalid coordinates\"}");
                    return false;
                }
            }
            case "LogStationEvent" -> {
                // Log arrival or departure at station
                String vehicleId = request.getParameter("vehicleId");
                String stationId = request.getParameter("stationId");
                String actionType = request.getParameter("actionType"); // ARRIVAL or DEPARTURE
                
                boolean success = gpsBusinessLogic.logStationEvent(vehicleId, stationId, actionType);
                
                if (success) {
                    request.setAttribute("successMessage", "Station event logged successfully");
                } else {
                    request.setAttribute("errorMessage", "Failed to log station event");
                }
                
                // Redirect back to appropriate dashboard
                String userRole = request.getSession().getAttribute("userRole").toString();
                if ("MANAGER".equals(userRole)) {
                    targetUrl = "StationReports";
                    return prepareRequest(request, response, targetUrl);
                } else {
                    targetUrl = "OperatorServiceLogs";
                    return prepareRequest(request, response, targetUrl);
                }
            }
            case "StartServiceLog" -> {
                // Start break or out-of-service period
                String vehicleId = request.getParameter("vehicleId");
                String logType = request.getParameter("logType"); // Break or OutOfService
                int operatorId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
                
                boolean success = gpsBusinessLogic.startServiceLog(vehicleId, operatorId, logType);
                
                if (success) {
                    request.setAttribute("successMessage", logType + " started successfully");
                } else {
                    request.setAttribute("errorMessage", "Failed to start " + logType);
                }
                
                targetUrl = "OperatorServiceLogs";
                return prepareRequest(request, response, targetUrl);
            }
            case "EndServiceLog" -> {
                // End break or out-of-service period
                String logIdStr = request.getParameter("logId");
                
                try {
                    int logId = Integer.parseInt(logIdStr);
                    boolean success = gpsBusinessLogic.endServiceLog(logId);
                    
                    if (success) {
                        request.setAttribute("successMessage", "Service period ended successfully");
                    } else {
                        request.setAttribute("errorMessage", "Failed to end service period");
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("errorMessage", "Invalid log ID");
                }
                
                targetUrl = "OperatorServiceLogs";
                return prepareRequest(request, response, targetUrl);
            }
  // --- NEW VEHICLE MANAGEMENT CASES ---
               case "AddVehicleServlet" -> { // This case handles displaying the "Add Vehicle" form.
               
                targetUrl = "AddVehicleServlet";
              
                break;
            
            }
               case "GPSLogServlet" -> {
                    targetUrl = "GPSLogServlet";
                    break;
               }
                case "GPSTrackingServlet" -> {
                    targetUrl = "gpslog";
                    break;
               }
                  
         
        }
        // NEW GPS TRACKING CASES
        return true;
        
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
}