import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;

public class SubmitAssignmentServlet extends HttpServlet {

    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        String studentName = request.getParameter("studentName");
        String assignmentTitle = request.getParameter("assignmentTitle");
        String fileName = request.getParameter("fileName");

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO submissions(student_name,assignment_title,file_name) VALUES(?,?,?)"
            );

            ps.setString(1, studentName);
            ps.setString(2, assignmentTitle);
            ps.setString(3, fileName);

            ps.executeUpdate();

            response.sendRedirect("student-dashboard.html");

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}