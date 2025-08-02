/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ZhiruXie.DataAccessLayer;

import ZhiruXie.DTO.PerformanceDTO;
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
public class PerformanceDAOImp implements PerformanceDAO{
    @Override
    public List<PerformanceDTO> getAll(int userId) {
        String sql = "select * from ptfms_db.trip_records where operator_ref = ?";
        ArrayList<PerformanceDTO> performanceRecords = null;
        try(
               Connection con = DataSource.getConnection("cst8288", "cst8288");
               PreparedStatement pstmt = con.prepareStatement(sql);
        ){
            pstmt.setInt(1, userId);
            performanceRecords = new ArrayList<>();
            try(ResultSet rs = pstmt.executeQuery()){
                while (rs.next()) {
                        PerformanceDTO performanceRecord = new PerformanceDTO(
                        rs.getInt("id"),
                        rs.getInt("operator_ref"),
                        rs.getString("vehicle_ref"),
                        rs.getString("route_name"),
                        rs.getTimestamp("start_time").toLocalDateTime(),
                        rs.getTimestamp("end_time").toLocalDateTime(),
                        rs.getTimestamp("scheduled_start").toLocalDateTime(),
                        rs.getBoolean("on_time_flag"),
                        rs.getDouble("distance_km"),
                        rs.getInt("trip_length"),
                        rs.getInt("idle_minutes"),
                        rs.getDouble("fuel_spent"),
                        rs.getInt("passengers")
                    );
                    performanceRecords.add(performanceRecord);
                }
            }
            catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }          
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return performanceRecords;
    }

    @Override
    public PerformanceDTO getSingleById(int userId, int scheduleId) {
        return null;
    }

    @Override
    public boolean add(int userId, PerformanceDTO performance) {
        return true;
    }

    @Override
    public boolean update(int userId, PerformanceDTO performance) {
        return true;
    }

    @Override
    public boolean delete(int userId, int scheduleId) {
        return true;
    }
}
