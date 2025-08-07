/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Zhiru Xie
 * Student Id: 041143904
 * Professor Name: Teddy Yap
 */
package ZhiruXie.Utility;

import ZhiruXie.BusinessLayer.AnalyticsReportBusinessLogic;
import ZhiruXie.BusinessLayer.BusinessLogic;
import ZhiruXie.BusinessLayer.MaintenanceScheduleBusinessLogic;
import ZhiruXie.BusinessLayer.PerformanceBusinessLogic;
import ZhiruXie.BusinessLayer.VehicleBusinessLogic;

/** This class is a simple factory for creating business logic instances.
 * @author Zhiru Xie
 * @since JDK21
 * @version 1.0
 * @see ZhiruXie.Utility
 */
public class SimpleBusinessLogicFactory {
    /** Create business logic instances based on input String
     * @param type The business logic type
     * @return An instance of the respective business logic
    */
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
