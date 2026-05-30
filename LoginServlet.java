import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;

public class LoginServlet extends HttpServlet {

    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM users WHERE email=? AND password=? AND role=?"
            );

            ps.setString(1, email);
            ps.setString(2, password);
            ps.setString(3, role);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

                if(role.equals("student")) {

                    response.sendRedirect("student-dashboard.html");

                } else {

                    response.sendRedirect("faculty-dashboard.html");
                }

            } else {

                response.getWriter().println("Invalid Credentials");
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}