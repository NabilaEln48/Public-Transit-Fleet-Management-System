/*
    Author       : Nabila Msiah 041146732
    Course       : CST8288 - Group Project - Authentication Module (Nabila Msiah Part)
    Description  : Singleton class responsible for providing a shared database connection
                   to the ptfms_db schema for authentication and user management.
*/

package com.group.project.nabila.msiah.DataAccessLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DataSource is a singleton that manages a single JDBC connection to ptfms_db.
 */
public class DataSource {

    private static DataSource instance;
    private Connection connection;

    private final String url = "jdbc:mysql://localhost:3306/ptfms_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private final String username = "cst8288";
    private final String password = "cst8288";

    /**
     * Private constructor to initialize JDBC connection.
     */
    private DataSource() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); // Replace with logger in production
        }
    }

    /**
     * Returns the singleton instance of DataSource.
     *
     * @return DataSource instance
     */
    public static synchronized DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }
        return instance;
    }

    /**
     * Returns the shared JDBC connection.
     *
     * @return active database connection
     */
    public Connection getConnection() {
        return connection;
    }
}
