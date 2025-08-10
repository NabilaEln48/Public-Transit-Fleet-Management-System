/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Purnima Purnima
 * Student Id: 041161250
 * Professor Name: Teddy Yap
 */

package Purnima.DataAccessLayer;

import Purnima.DTO.StationLogDTO;
import java.util.List;

/**
 * An interface for a Data Access Object (DAO) that handles station log data.
 * <p>
 * This interface defines the contract for interacting with a data source
 * to manage station log information, such as arrivals and departures.
 * </p>
 */
public interface StationLogDAO {

    /**
     * Retrieves all station log records from the data source.
     *
     * @return A list of all {@link StationLogDTO} objects representing station logs.
     */
    List<StationLogDTO> getAllStationLogs();

    /**
     * Retrieves all station log records for a specific vehicle.
     *
     * @param vehicleId The unique identifier of the vehicle.
     * @return A list of {@link StationLogDTO} objects for the specified vehicle.
     */
    List<StationLogDTO> getLogsByVehicle(String vehicleId);

    /**
     * Inserts a new station log record into the data source.
     *
     * @param stationLog The {@link StationLogDTO} object containing the station log data to be inserted.
     * @return {@code true} if the insertion was successful, {@code false} otherwise.
     */
    boolean insertStationLog(StationLogDTO stationLog);
}
