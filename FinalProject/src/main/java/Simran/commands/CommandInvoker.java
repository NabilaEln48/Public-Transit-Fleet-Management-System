/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Prabhsimran Kaur
 * Student Id: 041119310
 * Professor Name: Teddy Yap
 */

package Simran.commands;

/**
 * Invoker class in the Command design pattern.
 * 
 * Responsible for executing commands without knowing the specific
 * implementation details. It calls the execute() method
 * of the provided command object.
 * 
 * This decouples the object that invokes the operation from the one
 * that knows how to perform it.
 * 
 * @author Prabhsimran Kaur
 */
public class CommandInvoker {

    /**
     * Executes the given command.
     *
     * @param command the command to be executed
     */
    public void executeCommand(Command command) {
        // Call the command's execute method
        command.execute();
    }
}