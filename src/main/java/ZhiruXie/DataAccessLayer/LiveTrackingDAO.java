

/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Purnima Purnima
 * Student Id: 041161250
 * Professor Name: Teddy Yap
 */

package ZhiruXie.DataAccessLayer;

import ZhiruXie.DTO.LiveTrackingDTO;
import ZhiruXie.DTO.LiveTrackingDTO;
import java.util.List;

public interface LiveTrackingDAO {
    List<LiveTrackingDTO> getAllLiveTracking();
    List<LiveTrackingDTO> getTrackingByVehicle(String vehicleId);
    boolean insertTrackingData(LiveTrackingDTO tracking);
    LiveTrackingDTO getLatestTrackingByVehicle(String vehicleId);
}