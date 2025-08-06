/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Zhiru Xie
 * Student Id: 041143904
 * Professor Name: Teddy Yap
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
public class PerformanceBusinessLogic implements BusinessLogic<PerformanceDTO> {
    private PerformanceDAO dao = null;
    
    public PerformanceBusinessLogic() {dao = new PerformanceDAOImp();}
    
    @Override    
    public List<PerformanceDTO> getAll(int... params) {
        return dao.getAll(params[0]);
    }

    @Override
    public PerformanceDTO getSingleById(Object... params) {
        return dao.getSingleById((int)params[0], (int)params[1]);
    }

    @Override
    public boolean add(Object... params) {
        return dao.add((int)params[0], (PerformanceDTO)params[1]);
    }

    @Override
    public boolean update(Object... params) {
        return dao.update((int)params[0], (PerformanceDTO)params[1]);
    }

    @Override
    public boolean delete(Object... params) {
        return dao.delete((int)params[0], (int)params[1]);
    }
}
