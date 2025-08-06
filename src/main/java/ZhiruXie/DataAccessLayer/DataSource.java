/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Zhiru Xie
 * Student Id: 041143904
 * Professor Name: Teddy Yap
 */
package ZhiruXie.DataAccessLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author 61963
 */
public class DataSource {
    /** Unique instance used for accessing the database. */
    private static Connection connection = null;
    
    /** Make the default constructor private to prevent other members from instantiating it. */
    private DataSource() { }
    
    /** Retrieve the database connection Singleton. Re-generate one if there's no one functioning.
     * @param username The username to access the database.
     * @param password The password to access the database.
     * @return The unique instance of database connection.
     */
    public static Connection getConnection(String username, String password) {
        DatabaseProperty props = new DatabaseProperty(username, password);
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                // Use the properties from DatabaseProperty
                connection = DriverManager.getConnection(props.getUrl(), 
                                                        props.getUsername().isEmpty() ? "cst8288" : props.getUsername(), 
                                                        props.getPassword().isEmpty() ? "cst8288" : props.getPassword());
                System.out.println("New database connection established.");
            } else {
                System.out.println("Cannot create new connection, using existing one.");
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception during connection: " + e.getMessage());
            e.printStackTrace(); // Print full stack trace for debugging
            connection = null; // Ensure connection is null on failure
        } catch(ClassNotFoundException e){
            System.err.println("Driver not found: " + e.getMessage());
            e.printStackTrace(); // Print full stack trace for debugging
            connection = null; // Ensure connection is null on failure
        }
        return connection;
    }
    
    /**
     * Closes the singleton database connection.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    System.out.println("Database connection closed.");
                }
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
                e.printStackTrace();
            } finally {
                connection = null; // Ensure connection is null after closing
            }
        }
    }
}