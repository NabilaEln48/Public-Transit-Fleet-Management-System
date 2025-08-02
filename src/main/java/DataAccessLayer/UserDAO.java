/*
    Author       : Nabila Msiah 041146732
    Course       : CST8288 - Group Project - Authentication Module (Nabila Msiah Part)
    Description  : DAO Interface for user-related DB operations.
*/

package DataAccessLayer;

import Transferobject.UserDTO;

/**
 * UserDAO defines contract for user authentication and registration operations.
 */
public interface UserDAO {

    /**
     * Inserts a new user into the database.
     *
     * @param user UserDTO containing name, email, password, and role
     * @return true if insert is successful, false if email already exists
     */
    boolean insertUser(UserDTO user);

    /**
     * Authenticates a user by matching email and hashed password.
     *
     * @param email          user's email
     * @param hashedPassword hashed password
     * @return UserDTO if credentials are valid, otherwise null
     */
    UserDTO authenticate(String email, String hashedPassword);
}
