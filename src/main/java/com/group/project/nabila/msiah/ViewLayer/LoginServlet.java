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
import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@code LoginServlet} handles user authentication and session management.
 * <p>
 * Mapped to both {@code /login} and {@code /logout}, it performs:
 * <ul>
 *     <li>Login validation and session creation on POST</li>
 *     <li>Logout and session invalidation on GET</li>
 * </ul>
 * This servlet connects to the business layer {@link UserBusinessLogic} for authentication logic.
 */
@WebServlet(name = "LoginServlet", urlPatterns = { "/login", "/logout" })
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());

    /**
     * Handles GET requests for logout or showing the login page.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @throws ServletException if servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        // ---------- Logout ----------
        if ("/logout".equals(path)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
        // ---------- Show Login ----------
        else {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    /**
     * Handles POST requests to authenticate users.
     * Validates email and password, hashes password, and checks credentials using the business layer.
     * If successful, stores user info in session and redirects to appropriate dashboard.
     *
     * @param request  the HTTP request containing login form data
     * @param response the HTTP response used for redirection
     * @throws ServletException if servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        logger.info("Login attempt for user: " + email);

        // ---------- Input Validation ----------
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Email and password are required.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        try {
            UserBusinessLogic userLogic = new UserBusinessLogic();
            String hashedPassword = hashPassword(password);

            UserDTO user = userLogic.authenticateUser(email, hashedPassword);

            // ---------- Successful Login ----------
            if (user != null) {
                HttpSession session = request.getSession(true);
                session.setAttribute("user", user);
                session.setAttribute("userId", user.getUserId());
                session.setAttribute("userName", user.getName());
                session.setAttribute("userType", user.getUserType());

                String contextPath = request.getContextPath();
                String redirectUrl;

                // ---------- Role-based Redirection ----------
                switch (user.getUserType().toLowerCase()) {
                    case "manager":
                        redirectUrl = contextPath + "/manager.jsp";
                        break;
                    case "operator":
                        redirectUrl = contextPath + "/operator.jsp";
                        break;
                    default:
                        redirectUrl = contextPath + "/login.jsp";
                        break;
                }

                logger.info("Redirecting to: " + redirectUrl);
                response.sendRedirect(redirectUrl);
            }

            // ---------- Failed Login ----------
            else {
                request.setAttribute("error", "Invalid email or password.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Database error during login", ex);
            request.setAttribute("error", "Internal error. Please try again later.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    /**
     * Hashes a password using SHA-256 algorithm.
     *
     * @param password the plain text password
     * @return the hashed password as a hexadecimal string
     */
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashed = md.digest(password.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashed) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Password hashing failed", e);
            return null;
        }
    }
}
