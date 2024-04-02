package edu.duke.ece651.team4.shared;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Establishes a connection to the postgres database
 */
public class DatabaseConnectionUtil {
    private static final String URL = "jdbc:postgresql://localhost:5432/attendance_tracker";
    private static final String USER = "postgres";
    private static final String PASSWORD = "password";

    /**
     * Load the postgres driver
     */
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Establishes a connection according to specific URL and USER
     * @return A database connection
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
