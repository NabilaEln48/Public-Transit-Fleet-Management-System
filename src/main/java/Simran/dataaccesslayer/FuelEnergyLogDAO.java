/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Prabhsimran Kaur
 * Student Id: 041119310
 * Professor Name: Teddy Yap
 */

package Simran.dataaccesslayer;

import Simran.transferobject.FuelEnergyLogDTO;
import java.util.List;

/**
 * Data Access Object (DAO) interface for fuel/energy logs.
 * 
 * Defines CRUD operations for interacting with the fuel/energy
 * logs in the database.
 * 
 * @author Prabhsimran Kaur
 */
public interface FuelEnergyLogDAO {

    /**
     * Retrieves all fuel/energy logs.
     *
     * @return list of fuel/energy logs
     */
    List<FuelEnergyLogDTO> getAll();

    /**
     * Retrieves a fuel/energy log by its ID.
     *
     * @param id the log ID
     * @return the matching fuel/energy log, or null if not found
     */
    FuelEnergyLogDTO getById(int id);

    /**
     * Adds a new fuel/energy log.
     *
     * @param log the log to add
     * @return true if added successfully, false otherwise
     */
    boolean add(FuelEnergyLogDTO log);

    /**
     * Updates an existing fuel/energy log.
     *
     * @param log the log to update
     * @return true if updated successfully, false otherwise
     */
    boolean update(FuelEnergyLogDTO log);

    /**
     * Deletes a fuel/energy log by its ID.
     *
     * @param id the log ID
     * @return true if deleted successfully, false otherwise
     */
    boolean delete(int id);
}