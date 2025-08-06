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

    @Override
    public boolean delete(int userId, int scheduleId) {
        String sql = "DELETE FROM ptfms_db.trip_records WHERE id=? AND operator_ref=?";

        try (
            Connection con = DataSource.getConnection("cst8288", "cst8288");
            PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            pstmt.setInt(1, scheduleId);
            pstmt.setInt(2, userId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Helper method to map ResultSet to PerformanceDTO
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
