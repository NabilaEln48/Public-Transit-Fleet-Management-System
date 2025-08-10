/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Purnima Purnima
 * Student Id: 041161250
 * Professor Name: Teddy Yap
 */
package Purnima.DTO;

import java.sql.Timestamp;

/**
 * Data Transfer Object for service log information.
 * This class is used to transfer log data for events like vehicle breaks or
 * when a vehicle is taken out of service.
 * @author Purnima
 */
public class ServiceLogDTO {
    private int id;
    private String vehicleRef;
    private Timestamp breakStart;
    private Timestamp breakEnd;
    private String logType; // Break or OutOfService
    private int operatorRef;
    private String operatorName; // For display
    private String vehicleRegistration; // For display
    private String notes;

    /**
     * Default constructor.
     */
    public ServiceLogDTO() {}
    
    /**
     * Constructor for creating a new service log record.
     * @param id The unique identifier of the service log.
     * @param vehicleRef The unique identifier of the vehicle.
     * @param breakStart The timestamp when the break or service event started.
     * @param breakEnd The timestamp when the break or service event ended.
     * @param logType The type of log (e.g., "Break" or "OutOfService").
     * @param operatorRef The unique identifier of the operator.
     */
    public ServiceLogDTO(int id, String vehicleRef, Timestamp breakStart,
                         Timestamp breakEnd, String logType, int operatorRef) {
        this.id = id;
        this.vehicleRef = vehicleRef;
        this.breakStart = breakStart;
        this.breakEnd = breakEnd;
        this.logType = logType;
        this.operatorRef = operatorRef;
    }
    
    // Getters and Setters with Javadoc
    
    /**
     * Gets the unique identifier of the service log.
     * @return The log ID.
     */
    public int getId() { return id; }
    /**
     * Sets the unique identifier of the service log.
     * @param id The new log ID.
     */
    public void setId(int id) { this.id = id; }
    
    /**
     * Gets the unique identifier of the vehicle.
     * @return The vehicle reference.
     */
    public String getVehicleRef() { return vehicleRef; }
    /**
     * Sets the unique identifier of the vehicle.
     * @param vehicleRef The new vehicle reference.
     */
    public void setVehicleRef(String vehicleRef) { this.vehicleRef = vehicleRef; }
    
    /**
     * Gets the start time of the break or service event.
     * @return The break start timestamp.
     */
    public Timestamp getBreakStart() { return breakStart; }
    /**
     * Sets the start time of the break or service event.
     * @param breakStart The new break start timestamp.
     */
    public void setBreakStart(Timestamp breakStart) { this.breakStart = breakStart; }
    
    /**
     * Gets the end time of the break or service event.
     * @return The break end timestamp.
     */
    public Timestamp getBreakEnd() { return breakEnd; }
    /**
     * Sets the end time of the break or service event.
     * @param breakEnd The new break end timestamp.
     */
    public void setBreakEnd(Timestamp breakEnd) { this.breakEnd = breakEnd; }
    
    /**
     * Gets the type of the log (e.g., "Break", "OutOfService").
     * @return The log type.
     */
    public String getLogType() { return logType; }
    /**
     * Sets the type of the log.
     * @param logType The new log type.
     */
    public void setLogType(String logType) { this.logType = logType; }
    
    /**
     * Gets the unique identifier of the operator.
     * @return The operator reference.
     */
    public int getOperatorRef() { return operatorRef; }
    /**
     * Sets the unique identifier of the operator.
     * @param operatorRef The new operator reference.
     */
    public void setOperatorRef(int operatorRef) { this.operatorRef = operatorRef; }
    
    /**
     * Gets the operator's name for display purposes.
     * @return The operator's name.
     */
    public String getOperatorName() { return operatorName; }
    /**
     * Sets the operator's name for display purposes.
     * @param operatorName The new operator's name.
     */
    public void setOperatorName(String operatorName) { this.operatorName = operatorName; }
    
    /**
     * Gets the vehicle's registration number for display purposes.
     * @return The vehicle registration.
     */
    public String getVehicleRegistration() { return vehicleRegistration; }
    /**
     * Sets the vehicle's registration number for display purposes.
     * @param vehicleRegistration The new vehicle registration.
     */
    public void setVehicleRegistration(String vehicleRegistration) { this.vehicleRegistration = vehicleRegistration; }
    
    /**
     * Gets any additional notes for the log.
     * @return The notes.
     */
    public String getNotes() { return notes; }
    /**
     * Sets any additional notes for the log.
     * @param notes The new notes.
     */
    public void setNotes(String notes) { this.notes = notes; }
}
