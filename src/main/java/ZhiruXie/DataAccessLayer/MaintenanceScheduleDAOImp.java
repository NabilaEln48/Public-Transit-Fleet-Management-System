/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Zhiru Xie
 * Student Id: 041143904
 * Professor Name: Teddy Yap
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
        String sql = "SELECT * FROM ptfms_db.maintenance_schedule WHERE assigned_technician = ? AND id = ?";
        try (
            Connection con = DataSource.getConnection("cst8288", "cst8288");
            PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, scheduleId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new MaintenanceScheduleDTO(
                        rs.getInt("id"),
                        rs.getInt("component_ref"),
                        rs.getString("vehicle_ref"),
                        rs.getString("task_description"),
                        rs.getDate("planned_date").toLocalDate(),
                        Enum_MaintenanceProgressStatus.valueOf(rs.getString("progress_status")),
                        rs.getInt("assigned_technician")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null; // not found
    }

    @Override
    public boolean add(int userId, MaintenanceScheduleDTO schedule) {
        String sql = "INSERT INTO ptfms_db.maintenance_schedule "
                + "(component_ref, vehicle_ref, task_description, planned_date, progress_status, assigned_technician) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (
            Connection con = DataSource.getConnection("cst8288", "cst8288");
            PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
            pstmt.setInt(1, schedule.getComponentId());
            pstmt.setString(2, schedule.getVehicleId());
            pstmt.setString(3, schedule.getTaskDescription());
            pstmt.setDate(4, java.sql.Date.valueOf(schedule.getPlannedDate()));
            pstmt.setString(5, schedule.getProgressStatus().name());
            pstmt.setInt(6, userId); // assigned technician is the logged-in user
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(int userId, MaintenanceScheduleDTO schedule) {
        String sql = "UPDATE ptfms_db.maintenance_schedule "
                + "SET component_ref = ?, vehicle_ref = ?, task_description = ?, "
                + "planned_date = ?, progress_status = ? "
                + "WHERE id = ? AND assigned_technician = ?";
        try (
            Connection con = DataSource.getConnection("cst8288", "cst8288");
            PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
            pstmt.setInt(1, schedule.getComponentId());
            pstmt.setString(2, schedule.getVehicleId());
            pstmt.setString(3, schedule.getTaskDescription());
            pstmt.setDate(4, java.sql.Date.valueOf(schedule.getPlannedDate()));
            pstmt.setString(5, schedule.getProgressStatus().name());
            pstmt.setInt(6, schedule.getId());
            pstmt.setInt(7, userId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int userId, int scheduleId) {
        String sql = "DELETE FROM ptfms_db.maintenance_schedule WHERE id = ? AND assigned_technician = ?";
        try (
            Connection con = DataSource.getConnection("cst8288", "cst8288");
            PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
            pstmt.setInt(1, scheduleId);
            pstmt.setInt(2, userId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
