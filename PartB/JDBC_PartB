import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * This program provides a menu-driven interface to perform CRUD
 * (Create, Read, Update, Delete) operations on a 'Product' table.
 * It uses PreparedStatement for security and transaction handling for data integrity.
 */
public class partB {

    // --- Database Credentials ---
    private static final String DB_URL = "jdbc:mysql://bytexldb.com:5051/db_43zwhhhy5";
    private static final String USER = "user_43zwhhhy5";
    private static final String PASS = "p43zwhhhy5";

    public static void main(String[] args) {
        // Explicitly load the driver once at the start.
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: MySQL JDBC Driver not found.");
            e.printStackTrace();
            return;
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Product Management Menu ---");
            System.out.println("1. Create (Add a new product)");
            System.out.println("2. Read (Display all products)");
            System.out.println("3. Update (Modify a product's details)");
            System.out.println("4. Delete (Remove a product)");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character left by nextInt()

            switch (choice) {
                case 1:
                    createProduct(scanner);
                    break;
                case 2:
                    readProducts();
                    break;
                case 3:
                    updateProduct(scanner);
                    break;
                case 4:
                    deleteProduct(scanner);
                    break;
                case 5:
                    System.out.println("Exiting application.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * CREATE: Inserts a new product into the database.
     */
    private static void createProduct(Scanner scanner) {
        System.out.print("Enter Product ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();

        String sql = "INSERT INTO Product (ProductID, ProductName, Price, Quantity) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setDouble(3, price);
            pstmt.setInt(4, quantity);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Product created successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Error creating product: " + e.getMessage());
        }
    }

    /**
     * READ: Fetches and displays all products from the database.
     */
    private static void readProducts() {
        String sql = "SELECT ProductID, ProductName, Price, Quantity FROM Product";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- List of All Products ---");
            System.out.printf("%-10s %-30s %-10s %-10s\n", "ID", "Name", "Price", "Quantity");
            System.out.println("------------------------------------------------------------");

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("%-10d %-30s %-10.2f %-10d\n",
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("Price"),
                        rs.getInt("Quantity"));
            }
            if (!found) {
                System.out.println("No products found in the database.");
            }
            System.out.println("------------------------------------------------------------");

        } catch (SQLException e) {
            System.err.println("Error reading products: " + e.getMessage());
        }
    }

    /**
     * UPDATE: Modifies an existing product's details using transaction handling.
     */
    private static void updateProduct(Scanner scanner) {
        System.out.print("Enter the Product ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter new Product Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter new Quantity: ");
        int quantity = scanner.nextInt();

        String sql = "UPDATE Product SET ProductName = ?, Price = ?, Quantity = ? WHERE ProductID = ?";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // 1. Start Transaction: Disable auto-commit
            conn.setAutoCommit(false);

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setDouble(2, price);
                pstmt.setInt(3, quantity);
                pstmt.setInt(4, id);

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    // 2. Commit Transaction: Make changes permanent
                    conn.commit();
                    System.out.println("Product updated successfully!");
                } else {
                    System.out.println("Product with ID " + id + " not found. No changes made.");
                    // No changes, so no need to commit or rollback
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error during update: " + e.getMessage());
            if (conn != null) {
                try {
                    // 3. Rollback Transaction: Revert changes on error
                    System.err.println("Rolling back transaction...");
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error during rollback: " + ex.getMessage());
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }

    /**
     * DELETE: Removes a product from the database using transaction handling.
     */
    private static void deleteProduct(Scanner scanner) {
        System.out.print("Enter the Product ID to delete: ");
        int id = scanner.nextInt();

        String sql = "DELETE FROM Product WHERE ProductID = ?";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            conn.setAutoCommit(false);

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    conn.commit();
                    System.out.println("Product deleted successfully!");
                } else {
                    System.out.println("Product with ID " + id + " not found.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error during deletion: " + e.getMessage());
            if (conn != null) {
                try {
                    System.err.println("Rolling back transaction...");
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error during rollback: " + ex.getMessage());
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }
}
