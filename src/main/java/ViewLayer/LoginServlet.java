/*
    Author       : Nabila Msiah 041146732
    Course       : CST8288 - Group Project - Authentication Module (Nabila Msiah Part)
    Description  : Servlet that handles user login and logout functionality.
                   It authenticates users using the business layer and stores user details
                   in session upon successful login.
*/

package ViewLayer;

import BusinessLogicLayer.UserBusinessLogic;
import Transferobject.UserDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "LoginServlet", urlPatterns = { "/login", "/logout" })
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        if ("/logout".equals(path)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        } else {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        logger.info("Login attempt for user: " + email);

        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Email and password are required.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        try {
            UserBusinessLogic userLogic = new UserBusinessLogic();
            String hashedPassword = hashPassword(password);

            UserDTO user = userLogic.authenticateUser(email, hashedPassword);

            if (user != null) {
                HttpSession session = request.getSession(true);
                session.setAttribute("user", user);
                session.setAttribute("userId", user.getUserId());
                session.setAttribute("userName", user.getName());
                session.setAttribute("userType", user.getUserType());

                // Diagnostic logging
                String context = request.getContextPath();
                String role = user.getUserType().toLowerCase();
                System.out.println("CONTEXT: " + context);
                System.out.println("ROLE: " + role);

                // Hardcoded fallback
                String base = (context == null || context.isEmpty()) ? "/Group.Project.Nabila.Msiah" : context;
                String redirectUrl;

                switch (role) {
                    case "manager":
                        redirectUrl = base + "/manager.jsp";
                        break;
                    case "operator":
                        redirectUrl = base + "/operator.jsp";
                        break;
                    default:
                        redirectUrl = base + "/login.jsp";
                        break;
                }

                logger.info("Redirecting to: " + redirectUrl);
                response.sendRedirect(redirectUrl);

            } else {
                request.setAttribute("error", "Invalid email or password.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Database error during login", ex);
            request.setAttribute("error", "Internal error. Please try again later.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

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
