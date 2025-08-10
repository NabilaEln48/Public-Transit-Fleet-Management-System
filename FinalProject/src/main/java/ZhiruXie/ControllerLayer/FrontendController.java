/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Zhiru Xie
 * Student Id: 041143904
 * Professor Name: Teddy Yap
 */
package ZhiruXie.ControllerLayer;

import Purnima.BusinessLayer.GPSTrackingBusinessLogic;
import Purnima.DTO.ServiceLogDTO;
import ZhiruXie.BusinessLayer.VehicleBusinessLogic;
import ZhiruXie.DTO.CostAnalysisDTO;
import ZhiruXie.DTO.MaintenanceScheduleDTO;
import ZhiruXie.DTO.PerformanceDTO;
import ZhiruXie.DTO.VehicleDTO;
import ZhiruXie.Utility.SimpleBusinessLogicFactory;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** This class handles all Webpage requests and dispatch or redirect them to their destinations.
 * @author Zhiru Xie
 * @since JDK21
 * @version 1.0
 * @see ZhiruXie.ControllerLayer
 */
public class FrontendController extends HttpServlet{
    /** The vehicle business logic instance that are used for calling detailed logic from the DAO implementation class. */
    private final VehicleBusinessLogic vehicleBusinessLogic = new VehicleBusinessLogic();
    /** The gps business logic instance that are used for calling detailed logic from the DAO implementation class. */
    private final GPSTrackingBusinessLogic gpsBusinessLogic = new GPSTrackingBusinessLogic();

    /** Target url for new web page. */
    private String targetUrl;
    
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
        }
        
        if(!prepareResult){
            return;
        }
        
        dispatcher = request.getRequestDispatcher("/" + targetUrl);       
        dispatcher.forward(request, response);
    }
    
    // <editor-fold defaultstate="collapsed" desc="Url process logic">
    /** This method prepares the request based on the action type.
    * @param request servlet request
    * @param response servlet response
    * @param action The action type to perform.
    * @return Operation result. True if success and False if not.
    * @throws NullPointerException if the database connection is null.
    * @throws IOException if an I/O error occurs
    */
    private boolean prepareRequest(HttpServletRequest request, HttpServletResponse response, String action) throws IOException, NullPointerException{
        switch (action){
            case "ReportDashboard" -> {
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
                        request.setAttribute("operatorId", technicianId); // for redisplay
                    } catch (NumberFormatException e) {
                        technicianId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
                        request.setAttribute("errorMessage", "Invalid Technician Id. Please enter a number.");
                    }
                }
                List<MaintenanceScheduleDTO> schedules = SimpleBusinessLogicFactory.getBusinessLogic("maintenance").getAll(technicianId);
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
                        request.setAttribute("operatorId", operatorId); // for redisplay
                    } catch (NumberFormatException e) {
                        operatorId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
                        request.setAttribute("errorMessage", "Invalid Operator ID. Please enter a number.");
                    }
                }
                List<PerformanceDTO> performanceRecords = SimpleBusinessLogicFactory.getBusinessLogic("performance").getAll(operatorId);
                request.setAttribute("performanceRecords", performanceRecords);
            }
            case "CostReports" -> {
                List<CostAnalysisDTO> analysisRecords = SimpleBusinessLogicFactory.getBusinessLogic("analytics").getAll();
                request.setAttribute("AnalysisRecords", analysisRecords);
            }
            case "ScheduleMaintenance" -> {}
            case "GetVehicle" -> {
                String vehicleIdParam = request.getParameter("vehicleId");
                try {
                    if (vehicleIdParam != null && !vehicleIdParam.trim().isEmpty()) {
                        // Fetch single vehicle
                        VehicleDTO vehicle = (VehicleDTO)SimpleBusinessLogicFactory.getBusinessLogic("vehicle").getSingleById(vehicleIdParam);
                        if (vehicle != null) {
                            request.setAttribute("vehicles", List.of(vehicle)); // wrap in list for consistency
                        } else {
                            request.setAttribute("vehicles", List.of()); // empty list
                            request.setAttribute("errorMessage", "No vehicle found with ID: " + vehicleIdParam);
                        }
                    } else {
                        // Fetch all vehicles
                        request.setAttribute("vehicles", vehicleBusinessLogic.getAll());
                    }
                } catch (Exception e) {
                    request.setAttribute("vehicles", List.of());
                    request.setAttribute("errorMessage", "Error fetching vehicles: " + e.getMessage());
                }
            }
            case "AddVehicle" -> {}
            case "UpdateVehicle" -> {}
            case "DeleteVehicle" -> {}
            case "FuelLogs" -> {
               response.sendRedirect(request.getContextPath() + "/FuelEnergyLogServlet");
               return false; // Prevent default forwarding
           }
           case "ViewAlerts" -> {
               response.sendRedirect(request.getContextPath() + "/AlertServlet");
               return false; // Prevent default forwarding
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
            case "GPSLogServlet" -> {
                    targetUrl = "GPSLogServlet";
                    break;
            }
                case "GPSTrackingServlet" -> {
                    targetUrl = "gpslog";
                    break;
            }
        }
        return true;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
    // </editor-fold>
}