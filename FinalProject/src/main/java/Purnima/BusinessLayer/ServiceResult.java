/*
 * Assessment: Group Assignment
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Purnima Purnima
 * Student Id: 041161250
 * Professor Name: Teddy Yap
 */
package Purnima.BusinessLayer;

/**
 * Standard service result wrapper for business layer operations.
 * <p>
 * This class provides a consistent way to return the status of an operation,
 * along with a message and an optional data payload. It is a common pattern
 * in service-oriented architectures to encapsulate the result of a method
 * call, making it easier to handle success and failure cases uniformly.
 * </p>
 *
 * @author Team
 */
public class ServiceResult {

	/** A boolean flag indicating whether the operation was successful. */
	private boolean success;
	
	/** A message describing the result of the operation. */
	private String message;
	
	/** An optional data payload returned by the operation. */
	private Object data;
	
	/**
	 * Constructor for a simple success or failure result without a data payload.
	 *
	 * @param success Whether the operation was successful.
	 * @param message A message describing the result.
	 */
	public ServiceResult(boolean success, String message) {
		this.success = success;
		this.message = message;
		this.data = null;
	}
	
	/**
	 * Constructor for a result that includes a data payload.
	 *
	 * @param success Whether the operation was successful.
	 * @param message A message describing the result.
	 * @param data The data object returned by the operation.
	 */
	public ServiceResult(boolean success, String message, Object data) {
		this.success = success;
		this.message = message;
		this.data = data;
	}
	
	/**
	 * Checks if the operation was successful.
	 *
	 * @return {@code true} if successful, {@code false} otherwise.
	 */
	public boolean isSuccess() {
		return success;
	}
	
	/**
	 * Gets the result message.
	 *
	 * @return The result message as a {@code String}.
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Gets the result data.
	 *
	 * @return The result data object (can be {@code null}).
	 */
	public Object getData() {
		return data;
	}
	
	/**
	 * Sets the success status.
	 *
	 * @param success The success status.
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	/**
	 * Sets the result message.
	 *
	 * @param message The result message.
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * Sets the result data.
	 *
	 * @param data The result data.
	 */
	public void setData(Object data) {
		this.data = data;
	}
	
	/**
	 * Gets the result data and casts it to a specified type.
	 *
	 * @param <T> The expected data type.
	 * @param clazz The {@code Class} object representing the expected type.
	 * @return The typed data object.
	 * @throws ClassCastException if the data cannot be cast to the specified type.
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
	 * A static factory method to create a successful result.
	 *
	 * @param message The success message.
	 * @return A new {@code ServiceResult} with {@code success = true}.
	 */
	public static ServiceResult success(String message) {
		return new ServiceResult(true, message);
	}
	
	/**
	 * A static factory method to create a successful result with a data payload.
	 *
	 * @param message The success message.
	 * @param data The result data.
	 * @return A new {@code ServiceResult} with {@code success = true} and data.
	 */
	public static ServiceResult success(String message, Object data) {
		return new ServiceResult(true, message, data);
	}
	
	/**
	 * A static factory method to create a failure result.
	 *
	 * @param message The error message.
	 * @return A new {@code ServiceResult} with {@code success = false}.
	 */
	public static ServiceResult failure(String message) {
		return new ServiceResult(false, message);
	}
	
	/**
	 * A static factory method to create a failure result with a data payload.
	 *
	 * @param message The error message.
	 * @param data The error data (e.g., validation errors).
	 * @return A new {@code ServiceResult} with {@code success = false} and data.
	 */
	public static ServiceResult failure(String message, Object data) {
		return new ServiceResult(false, message, data);
	}
	
	/**
	 * Generates a string representation of the object.
	 *
	 * @return A string containing the class name and the values of the fields.
	 */
	@Override
	public String toString() {
		return "ServiceResult{" +
		       "success=" + success +
		       ", message='" + message + '\'' +
		       ", data=" + data +
		       '}';
	}
}
