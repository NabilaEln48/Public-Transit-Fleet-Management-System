/*
    Author       : Nabila Msiah 041146732
    Course       : CST8288 - Group Project - Authentication Module (Nabila Msiah Part)
    Description  : Front Controller Servlet that handles all navigation actions such as
                   showing login/register pages, logging out users, and redirecting
                   to dashboards based on roles.
*/

package com.group.project.nabila.msiah.ViewLayer;

import com.group.project.nabila.msiah.transferobject.UserDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/controller")
public class FrontControllerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null || action.trim().isEmpty()) {
            // Default fallback: redirect to login
            response.sendRedirect("login.jsp");
            return;
        }

        switch (action) {

            // Show login page
            case "showLogin":
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                break;

            // Show register page
            case "showRegister":
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                break;

            // Handle logout and session invalidation
            case "logout":
                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.invalidate();
                }
                response.sendRedirect("login.jsp");
                break;

            // Redirect to correct dashboard based on user type
            case "dashboardRedirect":
                HttpSession currentSession = request.getSession(false);
                if (currentSession != null) {
                    UserDTO user = (UserDTO) currentSession.getAttribute("user");
                    if (user != null) {
                        String role = user.getUserType();
                        if ("MANAGER".equalsIgnoreCase(role)) {
                            response.sendRedirect("manager.jsp");
                        } else if ("OPERATOR".equalsIgnoreCase(role)) {
                            response.sendRedirect("operator.jsp");
                        } else {
                            response.sendRedirect("login.jsp"); // unknown role
                        }
                        return;
                    }
                }
                response.sendRedirect("login.jsp"); // no session or user
                break;

            // Unknown or unsupported action
            default:
                response.sendRedirect("login.jsp");
                break;
        }
    }
}
