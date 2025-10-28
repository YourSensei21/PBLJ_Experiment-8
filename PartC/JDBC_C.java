import java.sql.*;
import java.util.*;

// ===== Model Class =====
class Student {
    private int studentId;
    private String name;
    private String department;
    private double marks;

    public Student(int studentId, String name, String department, double marks) {
        this.studentId = studentId;
        this.name = name;
        this.department = department;
        this.marks = marks;
    }

    public int getStudentId() { return studentId; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getMarks() { return marks; }

    public void setMarks(double marks) { this.marks = marks; }

    @Override
    public String toString() {
        return "Student ID: " + studentId + ", Name: " + name +
               ", Department: " + department + ", Marks: " + marks;
    }
}

// ===== Controller Class =====
class StudentDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/studentdb";
    private static final String USER = "root";
    private static final String PASSWORD = "your_password";

    // Add Student
    public void addStudent(Student s) {
        String query = "INSERT INTO students VALUES (?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, s.getStudentId());
            ps.setString(2, s.getName());
            ps.setString(3, s.getDepartment());
            ps.setDouble(4, s.getMarks());
            ps.executeUpdate();
            System.out.println("✅ Student added successfully!");
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    // View All Students
    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        String query = "SELECT * FROM students";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                list.add(new Student(
                    rs.getInt("studentId"),
                    rs.getString("name"),
                    rs.getString("department"),
                    rs.getDouble("marks")
                ));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        return list;
    }

    // Update Marks
    public void updateStudentMarks(int id, double marks) {
        String query = "UPDATE students SET marks=? WHERE studentId=?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setDouble(1, marks);
            ps.setInt(2, id);
            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("✅ Marks updated successfully!");
            else
                System.out.println("⚠️ Student not found!");
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    // Delete Student
    public void deleteStudent(int id) {
        String query = "DELETE FROM students WHERE studentId=?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("✅ Student deleted successfully!");
            else
                System.out.println("⚠️ Student not found!");
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}

// ===== View + Main Class =====
public class StudentManagementApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentDAO dao = new StudentDAO();

        while (true) {
            System.out.println("\n===== Student Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student Marks");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    System.out.print("Enter Student ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Department: ");
                    String dept = sc.nextLine();
                    System.out.print("Enter Marks: ");
                    double marks = sc.nextDouble();
                    dao.addStudent(new Student(id, name, dept, marks));
                    break;

                case 2:
                    List<Student> list = dao.getAllStudents();
                    if (list.isEmpty()) System.out.println("⚠️ No students found!");
                    else list.forEach(System.out::println);
                    break;

                case 3:
                    System.out.print("Enter Student ID to update: ");
                    int uid = sc.nextInt();
                    System.out.print("Enter new Marks: ");
                    double newMarks = sc.nextDouble();
                    dao.updateStudentMarks(uid, newMarks);
                    break;

                case 4:
                    System.out.print("Enter Student ID to delete: ");
                    int did = sc.nextInt();
                    dao.deleteStudent(did);
                    break;

                case 5:
                    System.out.println("👋 Exiting... Bye!");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("❌ Invalid choice! Try again.");
            }
        }
    }
}
