/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Purnima Purnima
 * Student Id: 041161250
 * Professor Name: Teddy Yap
 */

package Purnima.DataAccessLayer;

import Purnima.DTO.LiveTrackingDTO;
import java.util.List;

/**
 * An interface for a Data Access Object (DAO) that handles live vehicle tracking data.
 * <p>
 * This interface defines the contract for interacting with a data source
 * to perform CRUD-like operations on live tracking information.
 * </p>
 */
public interface LiveTrackingDAO {

    /**
     * Retrieves all live tracking records from the data source.
     *
     * @return A list of all {@link LiveTrackingDTO} objects representing live tracking data.
     */
    List<LiveTrackingDTO> getAllLiveTracking();

    /**
     * Retrieves all live tracking records for a specific vehicle.
     *
     * @param vehicleId The unique identifier of the vehicle.
     * @return A list of {@link LiveTrackingDTO} objects for the specified vehicle.
     */
    List<LiveTrackingDTO> getTrackingByVehicle(String vehicleId);

    /**
     * Inserts a new live tracking record into the data source.
     *
     * @param tracking The {@link LiveTrackingDTO} object containing the tracking data to be inserted.
     * @return {@code true} if the insertion was successful, {@code false} otherwise.
     */
    boolean insertTrackingData(LiveTrackingDTO tracking);

    /**
     * Retrieves the most recent live tracking record for a specific vehicle.
     *
     * @param vehicleId The unique identifier of the vehicle.
     * @return The latest {@link LiveTrackingDTO} object for the specified vehicle, or {@code null} if none is found.
     */
    LiveTrackingDTO getLatestTrackingByVehicle(String vehicleId);
}
