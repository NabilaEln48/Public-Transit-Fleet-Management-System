/*
    Author       : Nabila Msiah 041146732
    Course       : CST8288 - Group Project - Authentication Module (Nabila Msiah Part)
    Description  : Servlet that handles user login and logout functionality.
                   It authenticates users using the business layer and stores user details
                   in session upon successful login.
*/

package com.group.project.nabila.msiah.ViewLayer;

import com.group.project.nabila.msiah.BusinessLogicLayer.UserBusinessLogic;
import com.group.project.nabila.msiah.transferobject.UserDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet for handling login and logout operations.
 * Supports user session management and role-based redirection.
 */
@WebServlet(name = "LoginServlet", urlPatterns = { "/login", "/logout" })
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());

    /**
     * Handles GET requests for login and logout.
     * Invalidates session on logout and displays login form otherwise.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        if ("/logout".equals(path)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            // Forward to login page (inside WEB-INF/Auth)
            request.getRequestDispatcher("/WEB-INF/Auth/login.jsp").forward(request, response);
        }
    }

    /**
     * Handles POST requests for login form submission.
     * Authenticates user and sets session attributes or shows error message.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // ✅ Log the login attempt
        logger.info("Login attempt for user: " + email);

        // Validate input
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Email and password are required.");
            request.getRequestDispatcher("/WEB-INF/Auth/login.jsp").forward(request, response);
            return;
        }

        try {
            UserBusinessLogic userLogic = new UserBusinessLogic();
            String hashedPassword = password; // Add hashing later

            UserDTO user = userLogic.authenticateUser(email, hashedPassword);

            if (user != null) {
                // Store user info in session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("userId", user.getUserId());
                session.setAttribute("userName", user.getName());
                session.setAttribute("userType", user.getUserType());

                // Redirect based on user type (corrected paths)
                switch (user.getUserType().toLowerCase()) {
                    case "manager":
                        response.sendRedirect(request.getContextPath() + "/Dashboard/manager.jsp");
                        break;
                    case "operator":
                        response.sendRedirect(request.getContextPath() + "/Dashboard/operator.jsp");
                        break;
                    default:
                        // Safe fallback if unknown user type
                        response.sendRedirect(request.getContextPath() + "/login");
                        break;
                }

            } else {
                // Login failed
                request.setAttribute("error", "Invalid email or password.");
                request.getRequestDispatcher("/WEB-INF/Auth/login.jsp").forward(request, response);
            }

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Database error during login", ex);
            request.setAttribute("error", "Internal error. Please try again later.");
            request.getRequestDispatcher("/WEB-INF/Auth/login.jsp").forward(request, response);
        }
    }
}
