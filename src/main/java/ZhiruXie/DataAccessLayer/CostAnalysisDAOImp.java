/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ZhiruXie.DataAccessLayer;

import ZhiruXie.DTO.CostAnalysisDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 61963
 */
public class CostAnalysisDAOImp implements CostAnalysisDAO{

    @Override
    public List<CostAnalysisDTO> getAll() {
        String sql = "select * from ptfms_db.analytics_reports";
        ArrayList<CostAnalysisDTO> records = null;
        try(
               Connection con = DataSource.getConnection("cst8288", "cst8288");
               PreparedStatement pstmt = con.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ){
            records = new ArrayList<>();
            while (rs.next()){
                CostAnalysisDTO record = new CostAnalysisDTO(
                    rs.getInt("id"),
                    rs.getString("report_category"),
                    rs.getString("report_payload"),
                    rs.getTimestamp("created_at").toLocalDateTime()
                );
                records.add(record);
            }
        }          
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return records;
    }

    @Override
    public CostAnalysisDTO getSingleById(int recordId) {
        return null;
    }

    @Override
    public boolean add(CostAnalysisDTO record) {
        return true;
    }

    @Override
    public boolean update(CostAnalysisDTO updatedRecord) {
        return true;
    }

    @Override
    public boolean delete(int recordId) {
        return true;
    }
    
}
