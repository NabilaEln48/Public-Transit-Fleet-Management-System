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
public class AnalyticsReportBusinessLogic {
    private CostAnalysisDAO dao = null;
    
    public AnalyticsReportBusinessLogic() {dao = new CostAnalysisDAOImp();}
    
    public List<CostAnalysisDTO> getAll() {
        return dao.getAll();
    }

    public CostAnalysisDTO getSingleById(int recordId) {
        return dao.getSingleById(recordId);
    }

    public boolean add(CostAnalysisDTO record) {
        return dao.add(record);
    }

    public boolean update(CostAnalysisDTO updatedRecord) {
        return dao.update(updatedRecord);
    }

    public boolean delete(int recordId) {
        return dao.delete(recordId);
    }
}