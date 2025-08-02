/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ZhiruXie.DataAccessLayer;

import ZhiruXie.DTO.MaintenanceScheduleDTO;
import ZhiruXie.enums.Enum_MaintenanceProgressStatus;
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
public class MaintenanceScheduleDAOImp implements MaintenanceScheduleDAO{

    @Override
    public List<MaintenanceScheduleDTO> getAll(int userId) {
        String sql = "select * from ptfms_db.maintenance_schedule where assigned_technician = ?";
        ArrayList<MaintenanceScheduleDTO> scheduleItems = null;
        try(
               Connection con = DataSource.getConnection("cst8288", "cst8288");
               PreparedStatement pstmt = con.prepareStatement(sql);
        ){
            pstmt.setInt(1, userId);
            scheduleItems = new ArrayList<>();
            try(ResultSet rs = pstmt.executeQuery()){
                while (rs.next()) {
                        MaintenanceScheduleDTO schedule = new MaintenanceScheduleDTO(
                        rs.getInt("id"),
                        rs.getInt("component_ref"),
                        rs.getString("vehicle_ref"),
                        rs.getString("task_description"),
                        rs.getDate("planned_date").toLocalDate(),
                        Enum_MaintenanceProgressStatus.valueOf(rs.getString("progress_status")),
                        rs.getInt("assigned_technician")
                    );
                    scheduleItems.add(schedule);
                }
            }
            catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }          
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return scheduleItems;
    }

    @Override
    public MaintenanceScheduleDTO getSingleById(int userId, int scheduleId) {
        return null;
    }

    @Override
    public boolean add(int userId, MaintenanceScheduleDTO schedule) {
        return true;
    }

    @Override
    public boolean update(int userId, MaintenanceScheduleDTO schedule) {
        return true;
    }

    @Override
    public boolean delete(int userId, int scheduleId) {
        return true;
    }
    
}
