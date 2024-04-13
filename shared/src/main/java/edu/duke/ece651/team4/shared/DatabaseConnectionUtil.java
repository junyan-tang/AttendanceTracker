package edu.duke.ece651.team4.shared;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Establishes a connection to the postgres database
 */
public class DatabaseConnectionUtil {
    private static Connection connection = null;
    private static final String URL = "jdbc:postgresql://localhost:5432/attendance_tracker";
    private static final String USER = "postgres";
    private static final String PASSWORD = "password";

    /**
     * Establishes a connection according to specific URL and USER
     * @return A database connection
     */
    public static Connection getConnection() {
        if(connection != null){
            return connection;
        }
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Initializes the database with specific tables
     * @param conn The connection to the postgres database
     */
    public static void initDatabase(Connection conn){
        try {
            String userSQL = "CREATE TABLE IF NOT EXISTS user_ (" +
                             "netid VARCHAR(255) PRIMARY KEY," +
                             "firstName VARCHAR(255)," +
                             "lastName VARCHAR(255)," +
                             "password VARCHAR(255)," +
                             "preferredName VARCHAR(255)," +
                             "identity VARCHAR(255)," +
                             "email VARCHAR(255))";
            // System.out.println(userSQL);
            String courseSQL = "CREATE TABLE IF NOT EXISTS course (" +
                               "courseID VARCHAR(255) PRIMARY KEY," +
                               "courseName VARCHAR(255))";
            String sectionSQL = "CREATE TABLE IF NOT EXISTS section (" +
                                "sectionID VARCHAR(255) PRIMARY KEY," +
                                "courseID VARCHAR(255)," + 
                                "teacherID VARCHAR(255))" ;
            String attendanceSQL = "CREATE TABLE IF NOT EXISTS attendance (" +
                                   "attendanceID SERIAL PRIMARY KEY," +
                                   "courseID VARCHAR(255)," +
                                   "sectionID VARCHAR(255)," +
                                   "studentID VARCHAR(255)," +
                                   "date DATE," +
                                   "status VARCHAR(255))";
            String rosterSQL = "CREATE TABLE IF NOT EXISTS roster (" +
                               "sectionID VARCHAR(255) PRIMARY KEY," +
                               "studentID VARCHAR(255)," +
                               "FOREIGN KEY (sectionID) REFERENCES section(sectionID)," +
                               "FOREIGN KEY (studentID) REFERENCES user_(netid))";
            String notifySQL = "CREATE TABLE IF NOT EXISTS notify (" +
                               "netid VARCHAR(255) PRIMARY KEY," +
                               "choice VARCHAR(255))";
            Statement stmt = conn.createStatement();
            stmt.execute(userSQL);
            stmt.execute(courseSQL);
            stmt.execute(sectionSQL);
            stmt.execute(attendanceSQL);
            stmt.execute(rosterSQL);
            stmt.execute(notifySQL);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void dropTables(Connection conn){
        try {
            String dropRoster = "DROP TABLE IF EXISTS roster";
            String dropAttendance = "DROP TABLE IF EXISTS attendance";
            String dropSection = "DROP TABLE IF EXISTS section";
            String dropCourse = "DROP TABLE IF EXISTS course";
            String dropUser = "DROP TABLE IF EXISTS user_";
            Statement stmt = conn.createStatement();
            stmt.execute(dropRoster);
            stmt.execute(dropAttendance);
            stmt.execute(dropSection);
            stmt.execute(dropCourse);
            stmt.execute(dropUser);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Closes the connection to the postgres database
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
