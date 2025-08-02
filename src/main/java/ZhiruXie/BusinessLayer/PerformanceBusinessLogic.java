/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ZhiruXie.BusinessLayer;

import ZhiruXie.DTO.PerformanceDTO;
import ZhiruXie.DataAccessLayer.PerformanceDAO;
import ZhiruXie.DataAccessLayer.PerformanceDAOImp;
import java.util.List;

/**
 *
 * @author 61963
 */
public class PerformanceBusinessLogic {
        private PerformanceDAO dao = null;
    
    public PerformanceBusinessLogic() {dao = new PerformanceDAOImp();}
    
    public List<PerformanceDTO> getAll(int operatorId) {
        return dao.getAll(operatorId);
    }

    public PerformanceDTO getSingleById(int operatorId, int recordId) {
        return dao.getSingleById(operatorId, recordId);
    }

    public boolean add(int operatorId, PerformanceDTO record) {
        return dao.add(operatorId, record);
    }

    public boolean update(int operatorId, PerformanceDTO record) {
        return dao.update(operatorId, record);
    }

    public boolean delete(int operatorId, int scheduleId) {
        return dao.delete(operatorId, scheduleId);
    }
}
