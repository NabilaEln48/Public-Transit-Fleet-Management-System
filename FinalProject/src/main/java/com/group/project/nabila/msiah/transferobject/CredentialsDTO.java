/*
    Author       : Nabila Msiah 041146732
    Course       : CST8288 - Group Project - Authentication Module (Nabila Msiah Part)
    Description  : DTO for holding authentication credentials, such as username and password.
*/

package Transferobject;

/**
 * A Data Transfer Object used to encapsulate credentials
 * (e.g., for authenticating users or DB connection).
 */
public class CredentialsDTO {

    private String username;
    private String password;

    public CredentialsDTO() {
    }

    public CredentialsDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
