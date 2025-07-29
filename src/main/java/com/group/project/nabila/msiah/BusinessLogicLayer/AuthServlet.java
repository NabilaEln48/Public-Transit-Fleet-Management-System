/*
    Author       : Nabila Msiah 041146732
    Course       : CST8288 - Group Project - Authentication Module (Nabila Msiah Part)
    Description  : Servlet responsible for handling authentication logic including user login and registration.
                   It connects to the database using JDBC, processes login and registration requests via the
                   UserDAO, and routes users based on their roles to the appropriate dashboard pages.
*/

package com.group.project.nabila.msiah.BusinessLogicLayer;

import com.group.project.nabila.msiah.DTOLayer.User;
import com.group.project.nabila.msiah.DataAccessLayer.UserDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * AuthServlet handles user authentication and registration logic for the
 * application.
 * It connects to a MySQL database and interacts with the UserDAO to persist or
 * retrieve user data.
 * 
 * Servlet URL mapping: /auth
 * 
 * @author
 *         Nabila Msiah (041146732)
 * @version 1.0
 * @since 2025-07-29
 */
@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    private Connection conn;

    /**
     * Initializes the servlet by loading the JDBC driver and establishing a
     * connection to the database.
     */
    @Override
    public void init() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/PTFMS", "cst8288", "cst8288");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles POST requests for both login and registration actions.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input or output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        UserDAO dao = new UserDAO(conn);

        // Handle user registration
        if ("register".equals(action)) {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String role = request.getParameter("role");

            String hashedPassword = hashPassword(password);
            User user = new User(name, email, hashedPassword, role);
            boolean success = dao.insertUser(user);

            if (success) {
                request.setAttribute("msg", "Registration successful! Please login.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                request.setAttribute("msg", "Registration failed. Try again.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        }

        // Handle user login
        if ("login".equals(action)) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String hashedPassword = hashPassword(password);

            User user = dao.authenticate(email, hashedPassword);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("currentUser", user);
                if ("MANAGER".equalsIgnoreCase(user.getRole())) {
                    response.sendRedirect("managerDashboard.jsp");
                } else {
                    response.sendRedirect("operatorDashboard.jsp");
                }
            } else {
                request.setAttribute("msg", "Invalid email or password.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        }
    }

    /**
     * Hashes the given plain-text password using SHA-256.
     *
     * @param password the plain-text password
     * @return the hashed password in hexadecimal format, or null if hashing fails
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
            return null;
        }
    }

    /**
     * Closes the database connection when the servlet is destroyed.
     */
    @Override
    public void destroy() {
        try {
            if (conn != null)
                conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
