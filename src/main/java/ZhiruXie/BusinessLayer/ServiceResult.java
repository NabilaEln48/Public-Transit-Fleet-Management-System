package ZhiruXie.BusinessLayer;

/**
 * Standard service result wrapper for business layer operations
 * Provides consistent way to return success/failure status with messages and data
 * 
 * @author Team
 */
public class ServiceResult {
    
    private boolean success;
    private String message;
    private Object data;
    
    /**
     * Constructor for simple success/failure result
     * @param success Whether the operation was successful
     * @param message Result message
     */
    public ServiceResult(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.data = null;
    }
    
    /**
     * Constructor for result with data
     * @param success Whether the operation was successful
     * @param message Result message
     * @param data Result data object
     */
    public ServiceResult(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
    
    /**
     * Check if the operation was successful
     * @return true if successful, false otherwise
     */
    public boolean isSuccess() {
        return success;
    }
    
    /**
     * Get the result message
     * @return Result message
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * Get the result data
     * @return Result data object (can be null)
     */
    public Object getData() {
        return data;
    }
    
    /**
     * Set success status
     * @param success Success status
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    /**
     * Set result message
     * @param message Result message
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    /**
     * Set result data
     * @param data Result data
     */
    public void setData(Object data) {
        this.data = data;
    }
    
    /**
     * Get typed data with casting
     * @param <T> Expected data type
     * @param clazz Class type for casting
     * @return Typed data object
     * @throws ClassCastException if data cannot be cast to specified type
     */
    @SuppressWarnings("unchecked")
    public <T> T getData(Class<T> clazz) {
        if (data == null) {
            return null;
        }
        
        if (clazz.isInstance(data)) {
            return (T) data;
        }
        
        throw new ClassCastException("Cannot cast data to " + clazz.getSimpleName());
    }
    
    /**
     * Create a successful result
     * @param message Success message
     * @return ServiceResult with success = true
     */
    public static ServiceResult success(String message) {
        return new ServiceResult(true, message);
    }
    
    /**
     * Create a successful result with data
     * @param message Success message
     * @param data Result data
     * @return ServiceResult with success = true and data
     */
    public static ServiceResult success(String message, Object data) {
        return new ServiceResult(true, message, data);
    }
    
    /**
     * Create a failure result
     * @param message Error message
     * @return ServiceResult with success = false
     */
    public static ServiceResult failure(String message) {
        return new ServiceResult(false, message);
    }
    
    /**
     * Create a failure result with data
     * @param message Error message
     * @param data Error data (e.g., validation errors)
     * @return ServiceResult with success = false and data
     */
    public static ServiceResult failure(String message, Object data) {
        return new ServiceResult(false, message, data);
    }
    
    @Override
    public String toString() {
        return "ServiceResult{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}