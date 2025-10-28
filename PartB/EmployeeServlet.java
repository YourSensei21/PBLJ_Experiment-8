import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/employees")
public class EmployeeServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/companydb";
    private static final String DB_USER = "java";
    private static final String DB_PASS = "Mukul@123";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String searchId = request.getParameter("empId");

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            String sql;
            if (searchId != null && !searchId.trim().isEmpty()) {
                sql = "SELECT * FROM Employee WHERE EmpID = ?";
                pstmt = con.prepareStatement(sql);
                pstmt.setInt(1, Integer.parseInt(searchId.trim()));
            } else {
                sql = "SELECT * FROM Employee";
                pstmt = con.prepareStatement(sql);
            }
            
            rs = pstmt.executeQuery();

            out.println("<!DOCTYPE html>");
            out.println("<html lang='en'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("<title>Employee Records</title>");
            out.println("<script src='https://cdn.tailwindcss.com'></script>");
            out.println("<link href='https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap' rel='stylesheet'>");
            out.println("<style> body { font-family: 'Inter', sans-serif; } </style>");
            out.println("</head>");
            out.println("<body class='bg-gray-100'>");
            out.println("<div class='container mx-auto p-8'>");
            
            out.println("<h1 class='text-4xl font-bold text-gray-800 mb-6'>Employee Directory</h1>");

            out.println("<div class='bg-white p-6 rounded-xl shadow-lg mb-8'>");
            out.println("<form action='employees' method='GET' class='flex flex-col sm:flex-row gap-4'>");
            out.println("<label for='empId' class='block text-lg font-medium text-gray-700 self-center'>Search by Employee ID:</label>");
            out.println("<input type='text' id='empId' name='empId' class='flex-grow px-4 py-3 text-gray-800 bg-gray-50 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500' placeholder='Enter ID (e.g., 102)'>");
            out.println("<button type='submit' class='px-6 py-3 font-semibold text-white bg-blue-600 rounded-lg shadow-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 transition duration-200'>Search</button>");
            out.println("<a href='employees' class='px-6 py-3 text-center font-semibold text-gray-700 bg-gray-200 rounded-lg shadow-md hover:bg-gray-300 focus:outline-none focus:ring-2 focus:ring-gray-400 focus:ring-offset-2 transition duration-200'>View All</a>");
            out.println("</form>");
            out.println("</div>");

            out.println("<div class='bg-white rounded-xl shadow-lg overflow-hidden'>");
            out.println("<table class='w-full text-left table-auto'>");
            out.println("<thead>");
            out.println("<tr class='bg-gray-200 text-gray-700 uppercase text-sm leading-normal'>");
            out.println("<th class='py-3 px-6'>EmpID</th>");
            out.println("<th class='py-3 px-6'>Name</th>");
            out.println("<th class='py-3 px-6'>Salary</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody class='text-gray-800 text-sm font-light'>");

            boolean recordsFound = false;
            while (rs.next()) {
                recordsFound = true;
                out.println("<tr class='border-b border-gray-200 hover:bg-gray-100'>");
                out.println("<td class='py-4 px-6'>" + rs.getInt("EmpID") + "</td>");
                out.println("<td class='py-4 px-6'>" + rs.getString("Name") + "</td>");
                out.println("<td class='py-4 px-6'>$" + String.format("%,.2f", rs.getDouble("Salary")) + "</td>");
                out.println("</tr>");
            }

            if (!recordsFound) {
                out.println("<tr>");
                out.println("<td colspan='3' class='py-4 px-6 text-center text-gray-600'>No employee records found.</td>");
                out.println("</tr>");
            }

            out.println("</tbody>");
            out.println("</table>");
            out.println("</div>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");

        } catch (ClassNotFoundException e) {
            out.println("<p>Error: JDBC Driver not found. Please add the MySQL Connector/J JAR to your WEB-INF/lib folder.</p>");
            e.printStackTrace(out);
        } catch (SQLException e) {
            out.println("<p>Error: Database connection or query failed.</p>");
            e.printStackTrace(out);
        } catch (NumberFormatException e) {
             out.println("<p>Error: Invalid Employee ID. Please enter a number.</p>");
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace(out);
            }
            out.close();
        }
    }
}
