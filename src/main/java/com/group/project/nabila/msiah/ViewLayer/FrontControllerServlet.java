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

/**
 * {@code FrontControllerServlet} acts as a central navigation controller for
 * the web application.
 * <p>
 * It handles:
 * <ul>
 * <li>Showing login and register pages</li>
 * <li>User logout</li>
 * <li>Redirecting users to role-based dashboards</li>
 * </ul>
 * <p>
 * This servlet uses a query parameter <code>action</code> to determine what
 * navigation to perform.
 */
@WebServlet("/controller")
public class FrontControllerServlet extends HttpServlet {

    /**
     * Handles all GET requests for UI navigation.
     *
     * @param request  the HTTP request containing the "action" parameter
     * @param response the HTTP response object used for redirection or forwarding
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        // Redirect to login if no action provided
        if (action == null || action.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        switch (action) {

            case "showLogin":
                /**
                 * Forwards the user to the login page.
                 */
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                break;

            case "showRegister":
                /**
                 * Forwards the user to the registration page.
                 */
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                break;

            case "logout":
                /**
                 * Logs out the user by invalidating the session.
                 */
                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.invalidate();
                }
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                break;

            case "dashboardRedirect":
                /**
                 * Redirects logged-in users to their respective dashboards based on user role.
                 * - MANAGER → manager.jsp
                 * - OPERATOR → operator.jsp
                 * - Otherwise → login.jsp
                 */
                HttpSession currentSession = request.getSession(false);
                if (currentSession != null) {
                    UserDTO user = (UserDTO) currentSession.getAttribute("user");
                    if (user != null) {
                        String role = user.getUserType();
                        if ("MANAGER".equalsIgnoreCase(role)) {
                            response.sendRedirect(request.getContextPath() + "/manager.jsp");
                        } else if ("OPERATOR".equalsIgnoreCase(role)) {
                            response.sendRedirect(request.getContextPath() + "/operator.jsp");
                        } else {
                            response.sendRedirect(request.getContextPath() + "/login.jsp");
                        }
                        return;
                    }
                }
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                break;

            default:
                /**
                 * For any unknown action, redirect to login page.
                 */
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                break;
        }
    }
}
