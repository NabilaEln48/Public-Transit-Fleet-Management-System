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

/**
 * Command implementation for deleting a fuel/energy log.
 * 
 * Encapsulates the action of deleting a log so it can be executed
 * through the Command design pattern.
 * 
 * This ensures that the invoker does not need to know the internal
 * details of how deletion is performed.
 * 
 * @author Prabhsimran Kaur
 */
public class DeleteFuelLogCommand implements Command {

    /** Reference to the business logic layer for fuel/energy logs. */
    private final FuelEnergyLogBusinessLogic businessLogic;

    /** The ID of the log to be deleted. */
    private final int id;

    /** Role of the user performing the deletion (used for permission checks). */
    private final String role;

    /**
     * Creates a new DeleteFuelLogCommand.
     *
     * @param businessLogic the business logic object to handle the action
     * @param id            the ID of the fuel/energy log to be deleted
     * @param role          the role of the current user
     */
    public DeleteFuelLogCommand(FuelEnergyLogBusinessLogic businessLogic, int id, String role) {
        this.businessLogic = businessLogic;
        this.id = id;
        this.role = role;
    }

    /**
     * Executes the command to delete the fuel/energy log.
     * The actual role validation and database deletion
     * are handled in the business logic layer.
     */
    @Override
    public void execute() {
        // Delegate the delete operation to the business logic layer
        businessLogic.delete(id, role);
    }
}