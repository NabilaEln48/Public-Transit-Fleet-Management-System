/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ZhiruXie.ControllerLayer;

import ZhiruXie.BusinessLayer.AnalyticsReportBusinessLogic;
import ZhiruXie.BusinessLayer.MaintenanceScheduleBusinessLogic;
import ZhiruXie.BusinessLayer.PerformanceBusinessLogic;
import ZhiruXie.DTO.CostAnalysisDTO;
import ZhiruXie.DTO.MaintenanceScheduleDTO;
import ZhiruXie.DTO.PerformanceDTO;
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
                int userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
                List<MaintenanceScheduleDTO> schedules = scheduleBusinessLogic.getAll(userId);
                request.setAttribute("schedules", schedules);
            }
            case "PerformanceDashboard" -> {
                int operatorId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
                List<PerformanceDTO> performanceRecords = performanceBusinessLogic.getAll(operatorId);
                request.setAttribute("performanceRecords", performanceRecords);
            }
            case "CostReports" -> {
                List<CostAnalysisDTO> analysisRecords = analysisBusinessLogic.getAll();
                request.setAttribute("AnalysisRecords", analysisRecords);
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