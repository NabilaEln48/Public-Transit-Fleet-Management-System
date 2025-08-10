/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Prabhsimran Kaur
 * Student Id: 041119310
 * Professor Name: Teddy Yap
 */

package Simran.alerts;

/**
 * Subject interface for the Observer design pattern.
 * 
 * Defines methods to register, remove, and notify observers 
 * about an alert event.
 * 
 * 
 * @author Prabhsimran Kaur
 */
public interface Subject {

    /**
     * Registers an observer to receive alerts.
     *
     * @param o the observer to register
     */
    void registerObserver(Observer o);

    /**
     * Removes an observer so it no longer receives alerts.
     *
     * @param o the observer to remove
     */
    void removeObserver(Observer o);

    /**
     * Sends an alert notification to all registered observers.
     *
     * @param alertCategory the category/type of alert
     * @param vehicleRef    the vehicle reference ID
     * @param alertMessage  the alert message
     * @param severity      the severity level of the alert
     */
    void notifyObservers(String alertCategory, String vehicleRef, String alertMessage, String severity);
}