/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Zhiru Xie
 * Student Id: 041143904
 * Professor Name: Teddy Yap
 */
package ZhiruXie.DataAccessLayer;

import ZhiruXie.DTO.PerformanceDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ZhiruXie.Utility.PerformanceDTOBuilder;

/** This implementation class for performance report data access object that contains detailed logic for CRUD operations.
 * @author Zhiru Xie
 * @since JDK21
 * @version 1.0
 * @see ZhiruXie.DataAccessLayer
 */
public class PerformanceDAOImp implements PerformanceDAO{
    /**
     * Get all performance report
     * @param userId
     * @return
     */
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
                    PerformanceDTO performanceRecord = new PerformanceDTOBuilder()
                        .id(rs.getInt("id"))
                        .operatorId(rs.getInt("operator_ref"))
                        .vehicleId(rs.getString("vehicle_ref"))
                        .routeName(rs.getString("route_name"))
                        .startTime(rs.getTimestamp("start_time").toLocalDateTime())
                        .endTime(rs.getTimestamp("end_time").toLocalDateTime())
                        .scheduledStartTime(rs.getTimestamp("scheduled_start").toLocalDateTime())
                        .onTime(rs.getBoolean("on_time_flag"))
                        .distance(rs.getDouble("distance_km"))
                        .usedTime(rs.getInt("trip_length"))
                        .idleTime(rs.getInt("idle_minutes"))
                        .fuelSpent(rs.getDouble("fuel_spent"))
                        .passengerNumber(rs.getInt("passengers"))
                        .buildComplete(); // Build with all fields
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

    /**
     * Get a single performance report
     * @param userId
     * @param scheduleId
     * @return
     */
    @Override
    public PerformanceDTO getSingleById(int userId, int scheduleId) {
        String sql = "SELECT * FROM ptfms_db.trip_records WHERE operator_ref = ? AND id = ?";
        PerformanceDTO performance = null;
        try (
            Connection con = DataSource.getConnection("cst8288", "cst8288");
            PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, scheduleId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    performance = mapResultSetToDTO(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return performance;
    }

    /**
     * Insert a new performance report into the database
     * @param userId Unique identifier for the user
     * @param performance Unique identifier for the performance report
     * @return
     */
    @Override
    public boolean add(int userId, PerformanceDTO performance) {
        String sql = """
            INSERT INTO ptfms_db.trip_records
            (operator_ref, vehicle_ref, route_name, start_time, end_time, scheduled_start, on_time_flag, distance_km,
             trip_length, idle_minutes, fuel_spent, passengers)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (
            Connection con = DataSource.getConnection("cst8288", "cst8288");
            PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, performance.getVehicleId());
            pstmt.setString(3, performance.getRouteName());
            pstmt.setTimestamp(4, java.sql.Timestamp.valueOf(performance.getStartTime()));
            pstmt.setTimestamp(5, java.sql.Timestamp.valueOf(performance.getEndTime()));
            pstmt.setTimestamp(6, java.sql.Timestamp.valueOf(performance.getScheduledStartTime()));
            pstmt.setBoolean(7, performance.getOnTime());
            pstmt.setDouble(8, performance.getDistance());
            pstmt.setInt(9, performance.getUsedTime());
            pstmt.setInt(10, performance.getIdleTime());
            pstmt.setDouble(11, performance.getFuelSpent());
            pstmt.setInt(12, performance.getPassengerNumber());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Update an existing performance report from the database
     * @param userId Unique identifier for the user
     * @param performance Unique identifier for the performance report
     * @return
     */
    @Override
    public boolean update(int userId, PerformanceDTO performance) {
        String sql = """
            UPDATE ptfms_db.trip_records
            SET vehicle_ref=?, route_name=?, start_time=?, end_time=?, scheduled_start=?, on_time_flag=?, distance_km=?,
                trip_length=?, idle_minutes=?, fuel_spent=?, passengers=?
            WHERE id=? AND operator_ref=?
        """;

        try (
            Connection con = DataSource.getConnection("cst8288", "cst8288");
            PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            pstmt.setString(1, performance.getVehicleId());
            pstmt.setString(2, performance.getRouteName());
            pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(performance.getStartTime()));
            pstmt.setTimestamp(4, java.sql.Timestamp.valueOf(performance.getEndTime()));
            pstmt.setTimestamp(5, java.sql.Timestamp.valueOf(performance.getScheduledStartTime()));
            pstmt.setBoolean(6, performance.getOnTime());
            pstmt.setDouble(7, performance.getDistance());
            pstmt.setInt(8, performance.getUsedTime());
            pstmt.setInt(9, performance.getIdleTime());
            pstmt.setDouble(10, performance.getFuelSpent());
            pstmt.setInt(11, performance.getPassengerNumber());
            pstmt.setInt(12, performance.getId());
            pstmt.setInt(13, userId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Delete an existing performance report from the database
     * @param userId Unique identifier for the user
     * @param performanceId Unique identifier for the performance report
     * @return
     */
    @Override
    public boolean delete(int userId, int performanceId) {
        String sql = "DELETE FROM ptfms_db.trip_records WHERE id=? AND operator_ref=?";

        try (
            Connection con = DataSource.getConnection("cst8288", "cst8288");
            PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            pstmt.setInt(1, performanceId);
            pstmt.setInt(2, userId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Helper method to map ResultSet to PerformanceDTO
     * @param rs Result set for the SQL execution
     * @throw SQLException Possible SQL execution anomaly
     * @return A performance report instance
     */
    private PerformanceDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new PerformanceDTO(
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
    }
}
