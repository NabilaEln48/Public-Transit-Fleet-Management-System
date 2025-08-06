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
 * Command implementation for adding a new fuel/energy log.
 * 
 * Encapsulates the action of adding a log so it can be executed
 * through the Command pattern.
 * 
 * 
 * @author Prabhsimran Kaur
 */
public class AddFuelLogCommand implements Command {

    /** Reference to the business logic layer for fuel/energy logs. */
    private final FuelEnergyLogBusinessLogic businessLogic;

    /** The log entry to be added. */
    private final FuelEnergyLogDTO log;

    /** Role of the user performing the action (used for permission checks). */
    private final String role;

    /**
     * Creates a new AddFuelLogCommand.
     *
     * @param businessLogic the business logic object to handle the action
     * @param log           the fuel/energy log to be added
     * @param role          the role of the current user
     */
    public AddFuelLogCommand(FuelEnergyLogBusinessLogic businessLogic, FuelEnergyLogDTO log, String role) {
        this.businessLogic = businessLogic;
        this.log = log;
        this.role = role;
    }

    /**
     * Executes the command to add the fuel/energy log.
     * 
     * The actual role validation and database interaction
     * are handled in the business logic layer.
     * 
     */
    @Override
    public void execute() {
        // Delegate the add operation to the business logic layer
        businessLogic.add(log, role);
    }
}