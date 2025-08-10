/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Purnima Purnima
 * Student Id: 041161250
 * Professor Name: Teddy Yap
 */

package Purnima.DataAccessLayer;

import Purnima.DTO.ServiceLogDTO;
import java.util.List;

/**
 * An interface for a Data Access Object (DAO) that handles service log data.
 * <p>
 * This interface defines the contract for interacting with a data source
 * to manage service logs, such as breaks or out-of-service events.
 * </p>
 */
public interface ServiceLogDAO {

    /**
     * Retrieves all service log records from the data source.
     *
     * @return A list of all {@link ServiceLogDTO} objects representing service logs.
     */
    List<ServiceLogDTO> getAllServiceLogs();

    /**
     * Retrieves all service log records for a specific operator.
     *
     * @param operatorId The unique identifier of the operator.
     * @return A list of {@link ServiceLogDTO} objects for the specified operator.
     */
    List<ServiceLogDTO> getLogsByOperator(int operatorId);

    /**
     * Inserts a new service log record into the data source.
     *
     * @param serviceLog The {@link ServiceLogDTO} object containing the service log data to be inserted.
     * @return {@code true} if the insertion was successful, {@code false} otherwise.
     */
    boolean insertServiceLog(ServiceLogDTO serviceLog);

    /**
     * Updates an existing service log record to mark it as ended.
     *
     * @param logId The unique identifier of the service log to be ended.
     * @return {@code true} if the update was successful, {@code false} otherwise.
     */
    boolean endServiceLog(int logId);
}
