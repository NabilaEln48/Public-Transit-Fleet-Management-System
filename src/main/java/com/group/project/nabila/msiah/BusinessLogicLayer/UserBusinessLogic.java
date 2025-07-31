/*
    Author       : Nabila Msiah 041146732
    Course       : CST8288 - Group Project - Authentication Module (Nabila Msiah Part)
    Description  : Handles business logic related to user operations, such as authentication.
*/

package com.group.project.nabila.msiah.BusinessLogicLayer;

import com.group.project.nabila.msiah.DataAccessLayer.DataSource;
import com.group.project.nabila.msiah.DataAccessLayer.UserDAO;
import com.group.project.nabila.msiah.DataAccessLayer.UserDAOImpl;
import com.group.project.nabila.msiah.transferobject.UserDTO;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Business logic layer for user-related operations.
 */
public class UserBusinessLogic {

    /**
     * Authenticates the user using email and password.
     * Delegates DB lookup to UserDAO using a shared connection.
     *
     * @param email    user's email
     * @param password user's password (plain or hashed as stored)
     * @return UserDTO if authentication is successful, null otherwise
     * @throws SQLException if a database error occurs
     */
    public UserDTO authenticateUser(String email, String password) throws SQLException {
        try (Connection conn = DataSource.getInstance().getConnection()) {
            UserDAO userDAO = new UserDAOImpl(conn); // FIXED: Use implementation
            return userDAO.authenticate(email, password);
        }
    }
}
