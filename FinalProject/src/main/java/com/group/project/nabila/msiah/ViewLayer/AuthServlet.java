/*
    Author       : Nabila Msiah 041146732
    Course       : CST8288 - Group Project - Authentication Module (Nabila Msiah Part)
    Description  : Servlet responsible for handling authentication logic including user login and registration.
                   It connects to the database using JDBC via the DataSource singleton, processes login and
                   registration requests via the UserDAOImpl, and routes users based on their roles.
*/

package com.group.project.nabila.msiah.ViewLayer;

import com.group.project.nabila.msiah.transferobject.UserDTO;
import com.group.project.nabila.msiah.DataAccessLayer.UserDAO;
import com.group.project.nabila.msiah.DataAccessLayer.UserDAOImpl;
import com.group.project.nabila.msiah.DataAccessLayer.DataSource;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.security.MessageDigest;
import java.sql.Connection;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try (Connection conn = DataSource.getInstance().getConnection()) {

            UserDAO dao = new UserDAOImpl(conn);

            // ---------- Registration ----------
            if ("register".equalsIgnoreCase(action)) {
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                String role = request.getParameter("role");

                if (name == null || email == null || password == null || role == null ||
                        name.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty()) {
                    request.setAttribute("msg", "All fields are required.");
                    request.getRequestDispatcher("/register.jsp").forward(request, response);
                    return;
                }

                String hashedPassword = hashPassword(password);
                UserDTO user = new UserDTO(0, name, email, hashedPassword, role);

                boolean success = dao.insertUser(user);

                if (success) {
                    request.setAttribute("success", "Registration successful. You may now log in.");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                } else {
                    request.setAttribute("msg", "Registration failed. Email might already be in use.");
                    request.getRequestDispatcher("/register.jsp").forward(request, response);
                }
            }

            // ---------- Login ----------
            else if ("login".equalsIgnoreCase(action)) {
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
                    request.setAttribute("msg", "Email and password are required.");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                    return;
                }

                String hashedPassword = hashPassword(password);
                UserDTO user = dao.authenticate(email, hashedPassword);

                if (user != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    session.setAttribute("userId", user.getUserId());
                    session.setAttribute("userName", user.getName());
                    session.setAttribute("userType", user.getUserType());

                    if ("MANAGER".equalsIgnoreCase(user.getUserType())) {
                        response.sendRedirect(request.getContextPath() + "/manager.jsp");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/operator.jsp");
                    }
                } else {
                    request.setAttribute("msg", "Invalid email or password.");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                }
            }

            // ---------- Show Register Form ----------
            else if ("showRegister".equalsIgnoreCase(action)) {
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "Internal error occurred. Please try again later.");
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
            return null;
        }
    }
}
