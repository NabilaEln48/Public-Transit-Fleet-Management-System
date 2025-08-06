/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Zhiru Xie
 * Student Id: 041143904
 * Professor Name: Teddy Yap
 */
package ZhiruXie.ControllerLayer;

import ZhiruXie.BusinessLayer.VehicleBusinessLogic;
import ZhiruXie.BusinessLayer.AnalyticsReportBusinessLogic;
import ZhiruXie.BusinessLayer.MaintenanceScheduleBusinessLogic;
import ZhiruXie.BusinessLayer.PerformanceBusinessLogic;
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

/**
 *
 * @author 61963
 */
public class FrontendController extends HttpServlet{
    /** The business logic instance that are used for calling detailed logic from the DAO implementation class. */
    private final MaintenanceScheduleBusinessLogic scheduleBusinessLogic = new MaintenanceScheduleBusinessLogic();
    private final PerformanceBusinessLogic performanceBusinessLogic = new PerformanceBusinessLogic();
    private final AnalyticsReportBusinessLogic analysisBusinessLogic = new AnalyticsReportBusinessLogic();
    private final VehicleBusinessLogic vehicleBusinessLogic = new VehicleBusinessLogic();
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