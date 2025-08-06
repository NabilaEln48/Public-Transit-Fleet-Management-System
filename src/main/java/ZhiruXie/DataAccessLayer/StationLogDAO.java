
/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Purnima Purnima
 * Student Id: 041161250
 * Professor Name: Teddy Yap
 */

package ZhiruXie.DataAccessLayer;

import ZhiruXie.DTO.StationLogDTO;
import java.util.List;

public interface StationLogDAO {
    List<StationLogDTO> getAllStationLogs();
    List<StationLogDTO> getLogsByVehicle(String vehicleId);
    boolean insertStationLog(StationLogDTO stationLog);
}

