/*
    Author       : Nabila Msiah 041146732
    Course       : CST8288 - Group Project - Authentication Module (Nabila Msiah Part)
    Description  : DAO Interface for user-related DB operations.
*/

package com.group.project.nabila.msiah.DataAccessLayer;

import com.group.project.nabila.msiah.transferobject.UserDTO;

public interface UserDAO {
    boolean insertUser(UserDTO user);

    UserDTO authenticate(String email, String hashedPassword);
}
