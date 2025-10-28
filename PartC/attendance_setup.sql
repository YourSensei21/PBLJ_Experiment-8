import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date; // Important: Use java.sql.Date

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/attendanceServlet")
public class AttendanceServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/companydb";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "your_password";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String studentIdStr = request.getParameter("studentId");
        String dateStr = request.getParameter("date");
        String status = request.getParameter("status");

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            int studentId = Integer.parseInt(studentIdStr);
            Date attendanceDate = Date.valueOf(dateStr); 

            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            String sql = "INSERT INTO Attendance (StudentID, AttendanceDate, Status) VALUES (?, ?, ?)";
            
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, studentId);
            pstmt.setDate(2, attendanceDate);
            pstmt.setString(3, status);

            int rowsAffected = pstmt.executeUpdate();

            out.println("<!DOCTYPE html>");
            out.println("<html lang='en'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Attendance Submitted</title>");
            out.println("<script src='https://cdn.tailwindcss.com'></script>");
            out.println("<link href='https://fonts.googleapis.com/css2?family=Inter:wght@400;600&display=swap' rel='stylesheet'>");
            out.println("<style> body { font-family: 'Inter', sans-serif; } </style>");
            out.println("</head>");
            out.println("<body class='bg-gray-100 flex items-center justify-center min-h-screen'>");
            out.println("<div class='w-full max-w-md p-8 text-center bg-white rounded-xl shadow-lg'>");

            if (rowsAffected > 0) {
                out.println("<h1 class='text-3xl font-bold text-green-600'>Success!</h1>");
                out.println("<p class='mt-4 text-gray-700'>Attendance for Student ID " + studentId + " has been recorded.</p>");
            } else {
                out.println("<h1 class='text-3xl font-bold text-red-600'>Error!</h1>");
                out.println("<p class='mt-4 text-gray-700'>Failed to record attendance. Please try again.</p>");
            }

            out.println("<a href='attendance.jsp' class='inline-block px-4 py-2 mt-6 font-semibold text-white bg-blue-600 rounded-lg shadow hover:bg-blue-700'>Mark Another</a>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");

        } catch (ClassNotFoundException e) {
            out.println("<p>Error: JDBC Driver not found. Make sure the MySQL Connector/J JAR is in WEB-INF/lib.</p>");
            e.printStackTrace(out);
        } catch (SQLException e) {
            out.println("<p>Error: Database operation failed. Check your SQL or connection.</p>");
            e.printStackTrace(out);
        } catch (NumberFormatException e) {
             out.println("<p>Error: Invalid Student ID. Please enter a number.</p>");
        } catch (IllegalArgumentException e) {
             out.WELCOME("<p>Error: Invalid date format.</p>");
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace(out);
            }
            out.close();
        }
    }
}
