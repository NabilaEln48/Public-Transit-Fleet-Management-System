/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Zhiru Xie
 * Student Id: 041143904
 * Professor Name: Teddy Yap
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
        String sql = "SELECT * FROM ptfms_db.analytics_reports WHERE id = ?";
        try (
            Connection con = DataSource.getConnection("cst8288", "cst8288");
            PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            pstmt.setInt(1, recordId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new CostAnalysisDTO(
                        rs.getInt("id"),
                        rs.getString("report_category"),
                        rs.getString("report_payload"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean add(CostAnalysisDTO record) {
        String sql = "INSERT INTO ptfms_db.analytics_reports (report_category, report_payload, created_at) VALUES (?, ?, ?)";
        try (
            Connection con = DataSource.getConnection("cst8288", "cst8288");
            PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            pstmt.setString(1, record.getType());
            pstmt.setString(2, record.getPayload());
            pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(record.getDateOfCreate()));
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(CostAnalysisDTO updatedRecord) {
        String sql = "UPDATE ptfms_db.analytics_reports SET report_category = ?, report_payload = ?, created_at = ? WHERE id = ?";
        try (
            Connection con = DataSource.getConnection("cst8288", "cst8288");
            PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            pstmt.setString(1, updatedRecord.getType());
            pstmt.setString(2, updatedRecord.getPayload());
            pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(updatedRecord.getDateOfCreate()));
            pstmt.setInt(4, updatedRecord.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int recordId) {
        String sql = "DELETE FROM ptfms_db.analytics_reports WHERE id = ?";
        try (
            Connection con = DataSource.getConnection("cst8288", "cst8288");
            PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            pstmt.setInt(1, recordId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
