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

/** This implementation class for cost analysis data access object that contains detailed logic for CRUD operations.
 * @author Zhiru Xie
 * @since JDK21
 * @version 1.0
 * @see ZhiruXie.DataAccessLayer
 */
public class MaintenanceScheduleDAOImp implements MaintenanceScheduleDAO{

    /**
     * Get all schedule records
     * @param userId The unique identifier for the user
     * @return A list of MaintenanceScheduleDTO records from the database
     */
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

    /**
     * Get single schedule
     * @param userId The unique identifier for the user
     * @param scheduleId The unique identifier for the maintenance schedule report
     * @return A single MaintenanceScheduleDTO record from the database
     */
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

    /**
     * Insert a new maintenance schedule record
     * @param schedule A new schedule DTO
     * @return A Boolean result. true for success and false for failure
     */
    @Override
    public boolean add(MaintenanceScheduleDTO schedule) {
        String sql = "INSERT INTO ptfms_db.maintenance_schedule "
                + "(component_ref, vehicle_ref, task_description, planned_date, progress_status, assigned_technician) "
                + "VALUES (?, ?, ?, ?, 'Scheduled', ?)";
        try (
            Connection con = DataSource.getConnection("cst8288", "cst8288");
            PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
            pstmt.setInt(1, schedule.getComponentId());
            pstmt.setString(2, schedule.getVehicleId());
            pstmt.setString(3, schedule.getTaskDescription());
            pstmt.setDate(4, java.sql.Date.valueOf(schedule.getPlannedDate()));
            pstmt.setInt(5, schedule.getTechnicianId()); // assigned technician is the logged-in user
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Update an existing maintenance schedule record
     * @param schedule Updated schedule DTO
     * @return A Boolean result. true for success and false for failure
     */
    @Override
    public boolean update(MaintenanceScheduleDTO schedule) {
        String sql = "UPDATE ptfms_db.maintenance_schedule "
                + "SET component_ref = ?, vehicle_ref = ?, task_description = ?, "
                + "planned_date = ?, progress_status = ?, assigned_technician = ? "
                + "WHERE id = ?";
        try (
            Connection con = DataSource.getConnection("cst8288", "cst8288");
            PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
            pstmt.setInt(1, schedule.getComponentId());
            pstmt.setString(2, schedule.getVehicleId());
            pstmt.setString(3, schedule.getTaskDescription());
            pstmt.setDate(4, java.sql.Date.valueOf(schedule.getPlannedDate()));
            pstmt.setString(5, schedule.getProgressStatus().name());
            pstmt.setInt(6, schedule.getTechnicianId());
            pstmt.setInt(7, schedule.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Delete an existing maintenance schedule record
     * @param scheduleId Target record id
     * @return A Boolean result. true for success and false for failure
     */
    @Override
    public boolean delete(int scheduleId) {
        String sql = "DELETE FROM ptfms_db.maintenance_schedule WHERE id = ?";
        try (
            Connection con = DataSource.getConnection("cst8288", "cst8288");
            PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
            pstmt.setInt(1, scheduleId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
