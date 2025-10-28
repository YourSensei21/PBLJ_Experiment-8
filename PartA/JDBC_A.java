import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class partA {


    private static final String DB_URL = "jdbc:mysql://bytexldb.com:5051/db_43zwhhhy5";
    private static final String USER = "user_43zwhhhy5";
    private static final String PASS = "p43zwhhhy5";

    public static void main(String[] args) {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
            return; 
        }


        try (
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement()
        ) {

            String sqlQuery = "SELECT EmpID, Name, Salary FROM Employee";

            System.out.println("Executing query: " + sqlQuery);
            System.out.println("------------------------------------");

            ResultSet rs = stmt.executeQuery(sqlQuery);

            System.out.println("Employee Data:");
            System.out.printf("%-10s %-20s %-10s\n", "EmpID", "Name", "Salary");
            System.out.println("------------------------------------");

            while (rs.next()) {
                int id = rs.getInt("EmpID");
                String name = rs.getString("Name");
                double salary = rs.getDouble("Salary");

                System.out.printf("%-10d %-20s %-10.2f\n", id, name, salary);
            }

            System.out.println("------------------------------------");
            System.out.println("Data fetching complete.");

        } catch (SQLException e) {
            System.err.println("Database error occurred.");
            e.printStackTrace();
        }
    }
}
