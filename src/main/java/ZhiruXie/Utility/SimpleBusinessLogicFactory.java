/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ZhiruXie.Utility;

import ZhiruXie.BusinessLayer.AnalyticsReportBusinessLogic;
import ZhiruXie.BusinessLayer.BusinessLogic;
import ZhiruXie.BusinessLayer.MaintenanceScheduleBusinessLogic;
import ZhiruXie.BusinessLayer.PerformanceBusinessLogic;
import ZhiruXie.BusinessLayer.VehicleBusinessLogic;

/**
 *
 * @author 61963
 */
public class SimpleBusinessLogicFactory {
    public static BusinessLogic getBusinessLogic(String type) {
        return switch (type.toLowerCase()) {
            case "vehicle"     -> new VehicleBusinessLogic();
            case "maintenance" -> new MaintenanceScheduleBusinessLogic();
            case "performance" -> new PerformanceBusinessLogic();
            case "analytics"   -> new AnalyticsReportBusinessLogic();
            default -> throw new IllegalArgumentException("Unknown business logic type: " + type);
        };
    }
}
