package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3306/gym_management";
    private static final String USER = "root"; // Change if needed
    private static final String PASSWORD = "1234"; // Add your MySQL password

    private static Connection connection = null;

    // Get database connection
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Re-crée la connexion si elle est fermée ou null
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connected successfully! (nouvelle connexion)");
            }
            return connection;
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Connection failed! URL: " + URL);
            e.printStackTrace();
        }
        return connection; // même en cas d'erreur, on retourne ce qu'on a
    }

    // Close connection
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Test connection
    public static void testConnection() {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("Connection test successful!");
        } else {
            System.out.println("Connection test failed!");
        }
    }
}