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
 * Observer interface for the Observer design pattern.
 * 
 * Classes that implement this interface can receive alert notifications
 * from a Subject. This allows a loosely coupled design where 
 * multiple observers can react to the same event without being directly 
 * dependent on the subject's implementation.
 * 
 * 
 * 
 * 
 * @author Prabhsimran Kaur
 */
public interface Observer {

    /**
     * Called by the Subject to notify the Observer of an alert.
     *
     * @param alertCategory the category/type of alert (e.g., "Fuel", "Energy")
     * @param vehicleRef    the reference ID of the vehicle related to the alert
     * @param alertMessage  the description of the alert
     * @param severity      the severity level of the alert (e.g., "CRITICAL", "WARNING")
     */
    void update(String alertCategory, String vehicleRef, String alertMessage, String severity);
}