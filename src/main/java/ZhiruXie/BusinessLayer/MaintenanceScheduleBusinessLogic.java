/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ZhiruXie.BusinessLayer;

import ZhiruXie.DataAccessLayer.MaintenanceScheduleDAO;
import ZhiruXie.DataAccessLayer.MaintenanceScheduleDAOImp;
import ZhiruXie.DTO.MaintenanceScheduleDTO;
import java.util.List;

/**
 *
 * @author 61963
 */
public class MaintenanceScheduleBusinessLogic {
    private MaintenanceScheduleDAO dao = null;
    
    public MaintenanceScheduleBusinessLogic() {dao = new MaintenanceScheduleDAOImp();}
    
    public List<MaintenanceScheduleDTO> getAllMaintenaceSchedules(int userId) {
        return dao.getAllMaintenaceSchedules(userId);
    }

    public MaintenanceScheduleDTO getMaintenaceScheduleById(int userId, int scheduleId) {
        return dao.getMaintenaceScheduleById(userId, scheduleId);
    }

    public boolean addMaintenaceSchedule(int userId, MaintenanceScheduleDTO schedule) {
        return dao.addMaintenaceSchedule(userId, schedule);
    }

    public boolean updateMaintenanceSchedule(int userId, MaintenanceScheduleDTO schedule) {
        return dao.updateMaintenanceSchedule(userId, schedule);
    }

    public boolean deleteMaintenanceSchedule(int userId, int scheduleId) {
        return dao.deleteMaintenanceSchedule(userId, scheduleId);
    }
}
