/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Prabhsimran Kaur
 * Student Id: 041119310
 * Professor Name: Teddy Yap
 */

package Simran.commands;

import Simran.BusinessLayer.FuelEnergyLogBusinessLogic;
import Simran.transferobject.FuelEnergyLogDTO;

/**
 * Command implementation for updating an existing fuel/energy log.
 * 
 * Encapsulates the action of updating a log so it can be executed
 * through the Command design pattern.
 * 
 * This ensures the invoker does not need to know the internal
 * update logic or permission checks.
 * 
 * @author Prabhsimran Kaur
 */
public class UpdateFuelLogCommand implements Command {

    /** Reference to the business logic layer for fuel/energy logs. */
    private final FuelEnergyLogBusinessLogic businessLogic;

    /** The updated log entry data. */
    private final FuelEnergyLogDTO log;

    /** Role of the user performing the update (used for permission checks). */
    private final String role;

    /**
     * Creates a new UpdateFuelLogCommand.
     *
     * @param businessLogic the business logic object to handle the action
     * @param log           the updated fuel/energy log data
     * @param role          the role of the current user
     */
    public UpdateFuelLogCommand(FuelEnergyLogBusinessLogic businessLogic, FuelEnergyLogDTO log, String role) {
        this.businessLogic = businessLogic;
        this.log = log;
        this.role = role;
    }

    /**
     * Executes the command to update the fuel/energy log.
     * The actual role validation and database update
     * are handled in the business logic layer.
     */
    @Override
    public void execute() {
        // Delegate the update operation to the business logic layer
        businessLogic.update(log, role);
    }
}