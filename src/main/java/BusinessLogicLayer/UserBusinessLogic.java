/*
    Author       : Nabila Msiah 041146732
    Course       : CST8288 - Group Project - Authentication Module (Nabila Msiah Part)
    Description  : Handles business logic related to user operations, such as authentication and registration.
*/

package BusinessLogicLayer;

import DataAccessLayer.DataSource;
import DataAccessLayer.UserDAO;
import DataAccessLayer.UserDAOImpl;
import Transferobject.UserDTO;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Business logic layer for user-related operations.
 */
public class UserBusinessLogic {

    /**
     * Authenticates the user using email and password.
     *
     * @param email    user's email
     * @param password user's password (plain or hashed)
     * @return UserDTO if credentials are valid, null otherwise
     * @throws SQLException if a database access error occurs
     */
    public UserDTO authenticateUser(String email, String password) throws SQLException {
        try (Connection conn = DataSource.getInstance().getConnection()) {
            UserDAO userDAO = new UserDAOImpl(conn);
            return userDAO.authenticate(email, password);
        }
    }

    /**
     * Registers a new user in the system.
     *
     * @param userDTO User object containing name, email, hashed password, and role
     * @return true if registration is successful, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean registerUser(UserDTO userDTO) throws SQLException {
        try (Connection conn = DataSource.getInstance().getConnection()) {
            UserDAO userDAO = new UserDAOImpl(conn);
            return userDAO.insertUser(userDTO);
        }
    }
}
