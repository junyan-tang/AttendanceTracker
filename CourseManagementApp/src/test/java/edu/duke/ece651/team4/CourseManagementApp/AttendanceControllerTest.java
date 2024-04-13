// package edu.duke.ece651.team4.CourseManagementApp;

// import org.junit.jupiter.api.Test;

// import com.google.api.client.util.Data;

// import edu.duke.ece651.team4.shared.Attendance;


// import edu.duke.ece651.team4.shared.*;

// //import assert
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// import java.sql.SQLException;
// import java.sql.Connection;
// import java.sql.ResultSet;
// import java.sql.Statement;

// public class AttendanceControllerTest {
//   @Test
//   void testAddAttendanceDated() {

//     Connection conn = DatabaseConnectionUtil.getConnection();
//     DatabaseConnectionUtil.dropTables(conn);
//     DatabaseConnectionUtil.initDatabase(conn);
//     CourseController.addCourse("testcourseid", "testcoursename");
//     SectionController.addSection("testid", "testcourseid", "testteacherid");
//     AttendanceController.addAttendence("teststudent", "testid", "PRESENT");
//     AttendanceController.addAttendanceDated("teststudent", "testid", AttendanceController.parseDate("2024-03-10"), "PRESENT");
//     AttendanceController.addAttendanceDated("teststudent", "testid", AttendanceController.parseDate("2024-03-11"), "ABSENT");
//     AttendanceController.addAttendanceDated("teststudent", "testid", AttendanceController.parseDate("2024-03-12"), "PRESENT");
//     AttendanceController.updateAttendence("teststudent", "testid", AttendanceController.parseDate("2024-04-10"), "ABSENT");
//     AttendanceController.removeAttendence("teststudent", "testid", AttendanceController.parseDate("2024-04-10"));
//     AttendanceController.removeAttendence("teststudent", "testid", AttendanceController.parseDate("2024-04-10"));
//     AttendanceController.getAttendenceListStuSec("teststudent", "testid");
//     AttendanceController.getAttendenceListSec("testid");
//     AttendanceController.getAttendenceListStu("teststudent");
//     AttendanceController.getAttendenceDatelist("testid");
//     AttendanceController.getAttendenceStulist("testid", java.time.Instant.now());
//     // DatabaseConnectionUtil.dropTables(conn);
//   }
// }