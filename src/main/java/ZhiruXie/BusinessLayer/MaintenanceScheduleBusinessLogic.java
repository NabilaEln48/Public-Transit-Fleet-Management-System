/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Zhiru Xie
 * Student Id: 041143904
 * Professor Name: Teddy Yap
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
public class MaintenanceScheduleBusinessLogic  implements BusinessLogic<MaintenanceScheduleDTO> {
    private MaintenanceScheduleDAO dao = null;
    
    public MaintenanceScheduleBusinessLogic() {dao = new MaintenanceScheduleDAOImp();}

    @Override
    public List<MaintenanceScheduleDTO> getAll(int... params) {
        return dao.getAll(params[0]);
    }

    @Override
    public MaintenanceScheduleDTO getSingleById(Object... params) {
        return dao.getSingleById((int)params[0], (int)params[1]);
    }

    @Override
    public boolean add(Object... params) {
        return dao.add((MaintenanceScheduleDTO)params[0]);
    }

    @Override
    public boolean update(Object... params) {
        return dao.update((MaintenanceScheduleDTO)params[0]);
    }

    @Override
    public boolean delete(Object... params) {
        return dao.delete((int)params[0]);
    }
}
