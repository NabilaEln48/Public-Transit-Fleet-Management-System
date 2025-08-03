/*
    Author       : Nabila Msiah 041146732
    Course       : CST8288 - Group Project - Authentication Module (Nabila Msiah Part)
    Description  : Front Controller Servlet that handles all navigation actions such as
                   showing login/register pages, logging out users, and redirecting
                   to dashboards based on roles.
*/

package ViewLayer;

import Transferobject.UserDTO;

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
            // Default fallback: redirect to login page
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        switch (action) {

            case "showLogin":
                // Forward to login.jsp
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                break;

            case "showRegister":
                // Forward to register.jsp
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                break;

            case "logout":
                // Logout logic
                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.invalidate();
                }
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                break;

            case "dashboardRedirect":
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
                            response.sendRedirect(request.getContextPath() + "/login.jsp"); // unknown role
                        }
                        return;
                    }
                }
                response.sendRedirect(request.getContextPath() + "/login.jsp"); // no session or user
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                break;
        }
    }
}
