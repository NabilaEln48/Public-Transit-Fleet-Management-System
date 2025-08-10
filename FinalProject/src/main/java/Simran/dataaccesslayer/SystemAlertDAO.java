/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Prabhsimran Kaur
 * Student Id: 041119310
 * Professor Name: Teddy Yap
 */

package Simran.dataaccesslayer;

import Simran.transferobject.SystemAlertDTO;
import java.util.List;

/**
 * Data Access Object (DAO) interface for system alerts.
 * 
 * Defines operations for adding, retrieving, and updating
 * system alerts in the database.
 * 
 * @author Prabhsimran Kaur
 */
public interface SystemAlertDAO {

    /**
     * Adds a new system alert to the database.
     *
     * @param alert the alert to add
     * @return true if added successfully, false otherwise
     */
    boolean addAlert(SystemAlertDTO alert);

    /**
     * Retrieves all system alerts from the database.
     *
     * @return list of all alerts
     */
    List<SystemAlertDTO> getAllAlerts();

    /**
     * Updates the resolution status of a system alert.
     *
     * @param id the ID of the alert to update
     * @param newStatus the new status value
     * @return true if updated successfully, false otherwise
     */
    boolean updateAlertStatus(int id, String newStatus);
}