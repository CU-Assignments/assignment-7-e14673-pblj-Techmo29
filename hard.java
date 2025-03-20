import java.sql.*;
import java.util.*;

public class StudentManagement {
    static final String URL = "jdbc:mysql://localhost:3306/testdb";
    static final String USER = "root";
    static final String PASSWORD = "";
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1. Add Student\n2. View Students\n3. Update Student Marks\n4. Delete Student\n5. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewStudents();
                case 3 -> updateStudent();
                case 4 -> deleteStudent();
                case 5 -> System.exit(0);
                default -> System.out.println("Invalid choice");
            }
        }
    }

    static void addStudent() {
        System.out.println("Enter Student ID:");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter Name:");
        String name = scanner.nextLine();
        System.out.println("Enter Department:");
        String dept = scanner.nextLine();
        System.out.println("Enter Marks:");
        double marks = scanner.nextDouble();
        scanner.nextLine();
        String sql = "INSERT INTO Students (StudentID, Name, Department, Marks) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, dept);
            stmt.setDouble(4, marks);
            stmt.executeUpdate();
            conn.commit();
            System.out.println("Student added.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void viewStudents() {
        String sql = "SELECT * FROM Students";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getInt("StudentID") + " | " + rs.getString("Name") + " | " +
                        rs.getString("Department") + " | " + rs.getDouble("Marks"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void updateStudent() {
        System.out.println("Enter Student ID to update:");
        int id = scanner.nextInt();
        System.out.println("Enter New Marks:");
        double marks = scanner.nextDouble();
        scanner.nextLine();
        String sql = "UPDATE Students SET Marks = ? WHERE StudentID = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            stmt.setDouble(1, marks);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            conn.commit();
            System.out.println("Student updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void deleteStudent() {
        System.out.println("Enter Student ID to delete:");
        int id = scanner.nextInt();
        scanner.nextLine();
        String sql = "DELETE FROM Students WHERE StudentID = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            conn.commit();
            System.out.println("Student deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
