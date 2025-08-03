/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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