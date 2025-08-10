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

/** This class contains the encapsulated CRUD operations for analytic reports.Including get single and get all.
 * @author Zhiru Xie
 * @since JDK21
 * @version 1.0
 * @see ZhiruXie.BusinessLayer
 */
public class AnalyticsReportBusinessLogic implements BusinessLogic<CostAnalysisDTO> {
    /** A DAO variable used for storing its respective DAO Implementation class instance. */
    private CostAnalysisDAO dao = null;
    
    /**
     * Default constructor with no parameter.Initializes the CostAnalysisDAO implementation class instance.
     */
    public AnalyticsReportBusinessLogic() {dao = new CostAnalysisDAOImp();}
    
    /**
     * Encapsulated method that gets all CostAnalysisDTO records from the database.
     * @param params Optional parameters
     * @return A list of CostAnalysisDTO records
     */
    @Override
    public List<CostAnalysisDTO> getAll(int... params) {
        return dao.getAll();
    }

    /**
     * Encapsulated method that gets one CostAnalysisDTO records from the database based on its id.
     * @param params Mandatory parameters
     * @return A single CostAnalysisDTO records
     */
    @Override
    public CostAnalysisDTO getSingleById(Object... params) {
        return dao.getSingleById((int)params[0]);
    }

    /**
     * Encapsulated method that inserts one CostAnalysisDTO records from the database based on its id.
     * @param params Mandatory parameters
     * @return A Boolean result. true for success and false for failure.
     */
    @Override
    public boolean add(Object... params) {
        return dao.add((CostAnalysisDTO)params[0]);
    }

    /**
     * Encapsulated method that updates information for one CostAnalysisDTO records from the database based on new attribute values.
     * @param params Mandatory parameters
     * @return A Boolean result. true for success and false for failure.
     */
    @Override
    public boolean update(Object... params) {
        return dao.update((CostAnalysisDTO)params[0]);
    }

    /**
     * Encapsulated method that deletes a single CostAnalysisDTO record from the database based on its id.
     * @param params  Mandatory parameters
     * @return A Boolean result. true for success and false for failure.
     */
    @Override
    public boolean delete(Object... params) {
        return dao.delete((int)params[0]);
    }
}