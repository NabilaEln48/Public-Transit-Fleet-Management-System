package ZhiruXie.DTO;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ServiceLogDTO {
    private int id;
    private String vehicleRef;
    private Timestamp breakStart;
    private Timestamp breakEnd;
    private String logType; // Break or OutOfService
    private int operatorRef;
    private String operatorName; // For display
    private String vehicleRegistration; // For display
    private String notes; // This is the field that was missing

    // Default constructor
    public ServiceLogDTO() {}
    
    // Constructor
    public ServiceLogDTO(int id, String vehicleRef, Timestamp breakStart,
                         Timestamp breakEnd, String logType, int operatorRef) {
        this.id = id;
        this.vehicleRef = vehicleRef;
        this.breakStart = breakStart;
        this.breakEnd = breakEnd;
        this.logType = logType;
        this.operatorRef = operatorRef;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getVehicleRef() { return vehicleRef; }
    public void setVehicleRef(String vehicleRef) { this.vehicleRef = vehicleRef; }
    
    public Timestamp getBreakStart() { return breakStart; }
    public void setBreakStart(Timestamp breakStart) { this.breakStart = breakStart; }
    
    public Timestamp getBreakEnd() { return breakEnd; }
    public void setBreakEnd(Timestamp breakEnd) { this.breakEnd = breakEnd; }
    
    public String getLogType() { return logType; }
    public void setLogType(String logType) { this.logType = logType; }
    
    public int getOperatorRef() { return operatorRef; }
    public void setOperatorRef(int operatorRef) { this.operatorRef = operatorRef; }
    
    public String getOperatorName() { return operatorName; }
    public void setOperatorName(String operatorName) { this.operatorName = operatorName; }
    
    public String getVehicleRegistration() { return vehicleRegistration; }
    public void setVehicleRegistration(String vehicleRegistration) { this.vehicleRegistration = vehicleRegistration; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}