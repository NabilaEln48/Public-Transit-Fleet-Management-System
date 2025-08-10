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
 * Command interface for the Command design pattern.
 * 
 * Defines a single method to execute a specific action.
 * All concrete command classes will implement this interface.
 * 
 * @author : Prabhsimran Kaur
 */
public interface Command {

    /**
     * Executes the action defined by the concrete command.
     */
    void execute();
}