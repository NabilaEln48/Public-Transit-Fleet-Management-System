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
    
    public List<MaintenanceScheduleDTO> getAll(int userId) {
        return dao.getAll(userId);
    }

    public MaintenanceScheduleDTO getSingleById(int userId, int scheduleId) {
        return dao.getSingleById(userId, scheduleId);
    }

    public boolean add(int userId, MaintenanceScheduleDTO schedule) {
        return dao.add(userId, schedule);
    }

    public boolean update(int userId, MaintenanceScheduleDTO schedule) {
        return dao.update(userId, schedule);
    }

    public boolean delete(int userId, int scheduleId) {
        return dao.delete(userId, scheduleId);
    }
}
