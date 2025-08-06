/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Zhiru Xie
 * Student Id: 041143904
 * Professor Name: Teddy Yap
 */
package ZhiruXie.BusinessLayer;

import ZhiruXie.DTO.CostAnalysisDTO;
import ZhiruXie.DataAccessLayer.CostAnalysisDAO;
import ZhiruXie.DataAccessLayer.CostAnalysisDAOImp;
import java.util.List;

/**
 *
 * @author 61963
 */
public class AnalyticsReportBusinessLogic implements BusinessLogic<CostAnalysisDTO> {
    private CostAnalysisDAO dao = null;
    
    public AnalyticsReportBusinessLogic() {dao = new CostAnalysisDAOImp();}
    
    @Override
    public List<CostAnalysisDTO> getAll(int... params) {
        return dao.getAll();
    }

    @Override
    public CostAnalysisDTO getSingleById(Object... params) {
        return dao.getSingleById((int)params[0]);
    }

    @Override
    public boolean add(Object... params) {
        return dao.add((CostAnalysisDTO)params[0]);
    }

    @Override
    public boolean update(Object... params) {
        return dao.update((CostAnalysisDTO)params[0]);
    }

    @Override
    public boolean delete(Object... params) {
        return dao.delete((int)params[0]);
    }
}