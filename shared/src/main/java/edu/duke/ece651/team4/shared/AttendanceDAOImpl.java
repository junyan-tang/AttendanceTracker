package edu.duke.ece651.team4.shared;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class AttendanceDAOImpl implements AttendanceDAO{
    public Attendance getAttendance(String studentID, String sectionID, Instant date){
        String formattedDate = LocalDate.ofInstant(date, ZoneId.systemDefault()).toString();
        Connection conn = DatabaseConnectionUtil.getConnection();
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM ATTENDANCE WHERE studentID = '" + studentID + 
                        "' AND sectionID = '" + sectionID + "' AND date = '" + formattedDate + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Attendance res = new Attendance(rs.getString("courseID"), rs.getString("sectionID"), 
                                            rs.getString("studentID"), rs.getTimestamp("date").toInstant(), 
                                            rs.getString("status"));
            return res;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void addAttendance(Attendance attendance){
        Connection conn = DatabaseConnectionUtil.getConnection();
        try{
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO ATTENDANCE (studentID, sectionID, date, status) VALUES ('" + 
                        attendance.studentID + "', '" + attendance.sectionID + "', '" + 
                        attendance.attendanceTime + "', '" + attendance.status + "')";
            stmt.executeUpdate(sql);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void updateAttendance(Attendance attendance){
        Connection conn = DatabaseConnectionUtil.getConnection();
        try{
            Statement stmt = conn.createStatement();
            String formattedDate = LocalDate.ofInstant(attendance.attendanceTime, ZoneId.systemDefault()).toString();
            String sql = "UPDATE ATTENDANCE SET status = '" + attendance.status + 
                        "' WHERE studentID = '" + attendance.studentID + "' AND sectionID = '" + 
                        attendance.sectionID + "' AND date = '" + formattedDate + "'";
            stmt.executeUpdate(sql);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void deleteAttendance(String studentID, String sectionID, Instant date){
        String formattedDate = LocalDate.ofInstant(date, ZoneId.systemDefault()).toString();
        Connection conn = DatabaseConnectionUtil.getConnection();
        try{
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM ATTENDANCE WHERE studentID = '" + studentID + 
                        "' AND sectionID = '" + sectionID + "' AND date = '" + formattedDate +"'";
            stmt.executeUpdate(sql);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Attendance> getAttendance(String studentID, String sectionID){
        List<Attendance> attendanceList = new ArrayList<>();
        Connection conn = DatabaseConnectionUtil.getConnection();
        try{
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM ATTENDANCE WHERE studentID = '" + studentID + 
                        "' AND sectionID = '" + sectionID + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Attendance attendance = new Attendance(rs.getString("courseID"), rs.getString("sectionID"), 
                                                        rs.getString("studentID"), rs.getTimestamp("date").toInstant(), 
                                                        rs.getString("status"));
                attendanceList.add(attendance);
            }
            return attendanceList;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    public List<Attendance> getAttendanceListStu(String studentID){
        List<Attendance> attendanceList = new ArrayList<>();
        Connection conn = DatabaseConnectionUtil.getConnection();
        try{
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM ATTENDANCE WHERE studentID = '" + studentID + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Attendance attendance = new Attendance(rs.getString("courseID"), rs.getString("sectionID"), 
                                                        rs.getString("studentID"), rs.getTimestamp("date").toInstant(), 
                                                        rs.getString("status"));
                attendanceList.add(attendance);
            }
            return attendanceList;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    public List<Attendance> getAttendanceListSec(String sectionID){
        List<Attendance> attendanceList = new ArrayList<>();
        Connection conn = DatabaseConnectionUtil.getConnection();
        try{
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM ATTENDANCE WHERE sectionID = '" + sectionID + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Attendance attendance = new Attendance(rs.getString("courseID"), rs.getString("sectionID"), 
                                                        rs.getString("studentID"), rs.getTimestamp("date").toInstant(), 
                                                        rs.getString("status"));
                attendanceList.add(attendance);
            }
            return attendanceList;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    public List<Attendance> getAttendanceListSecDate(String sectionID, Instant date){
        String formattedDate = LocalDate.ofInstant(date, ZoneId.systemDefault()).toString();
        List<Attendance> attendanceList = new ArrayList<>();
        Connection conn = DatabaseConnectionUtil.getConnection();
        try{
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM ATTENDANCE WHERE sectionID = '" + sectionID + 
                        "' AND date = '" + formattedDate + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Attendance attendance = new Attendance(rs.getString("courseID"), rs.getString("sectionID"), 
                                                        rs.getString("studentID"), rs.getTimestamp("date").toInstant(), 
                                                        rs.getString("status"));
                attendanceList.add(attendance);
            }
            return attendanceList;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    public List<Attendance> getAttendanceListSecDateSta(String sectionID, Instant date, String status){
        String formattedDate = LocalDate.ofInstant(date, ZoneId.systemDefault()).toString();
        List<Attendance> attendanceList = new ArrayList<>();
        Connection conn = DatabaseConnectionUtil.getConnection();
        try{
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM ATTENDANCE WHERE sectionID = '" + sectionID + 
                        "' AND date = '" + formattedDate + "' AND status = '" + status + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Attendance attendance = new Attendance(rs.getString("courseID"), rs.getString("sectionID"), 
                                                        rs.getString("studentID"), rs.getTimestamp("date").toInstant(), 
                                                        rs.getString("status"));
                attendanceList.add(attendance);
            }
            return attendanceList;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    public List<String> getAttendanceDatelist(String sectionID){
        List<String> dateList = new ArrayList<>();
        Connection conn = DatabaseConnectionUtil.getConnection();
        try{
            Statement stmt = conn.createStatement();
            String sql = "SELECT DISTINCT date FROM ATTENDANCE WHERE sectionID = '" + sectionID + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                dateList.add(rs.getString("date"));
            }
            return dateList;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    public List<String> getAttendanceStulist(String sectionID, Instant date){
        String formattedDate = LocalDate.ofInstant(date, ZoneId.systemDefault()).toString();
        List<String> studentList = new ArrayList<>();
        Connection conn = DatabaseConnectionUtil.getConnection();
        try{
            Statement stmt = conn.createStatement();
            String sql = "SELECT DISTINCT studentID FROM ATTENDANCE WHERE sectionID = '" + sectionID + 
                        "' AND date = '" + formattedDate + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                studentList.add(rs.getString("studentID"));
            }
            return studentList;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }


    public String getAttendanceStuScoreInSec(String sectionID){
        Connection conn = DatabaseConnectionUtil.getConnection();
        try{
            Statement stmt = conn.createStatement();
            String sql = "SELECT studentID, SUM(CASE WHEN status = 'PRESENT' THEN 1 " +
                        "WHEN status = 'TARDY' THEN 0.8 ELSE 0 END) AS score FROM ATTENDANCE WHERE sectionID = '" + sectionID + "' GROUP BY studentID";
            ResultSet rs = stmt.executeQuery(sql);
            StringBuilder res = new StringBuilder("");
            while(rs.next()){
                res.append(rs.getString("studentID"));
                int len = 10 - rs.getString("studentID").length();
                for(int i = 0; i < len; i++){
                    res.append(" ");
                }
                res.append(rs.getString("score"));
                res.append("\n");
            }
            return res.toString();
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }

    }
//return a string that contains the information on all attendance scores of one student.
//e.g. Course: Math SectionID: 123 TotalAttendanceScores: 100\nDate:xxxxxx Status:Tardy Score:0.8\n  
    public String getAttendanceStuScore(String studentID){
        Connection conn = DatabaseConnectionUtil.getConnection();
        try{
            Statement stmt = conn.createStatement();
            String sql = "SELECT courseID, sectionID, SUM(CASE WHEN status = 'PRESENT' THEN 1" + 
                        " WHEN status = 'TARDY' THEN 0.8 ELSE 0 END) AS score FROM ATTENDANCE WHERE studentID = '" + studentID + 
                        "' GROUP BY sectionID";
            ResultSet rs = stmt.executeQuery(sql);
            StringBuilder result = new StringBuilder("");
            while (rs.next()) {
                String courseID = rs.getString("courseID");
                String sectionID = rs.getString("sectionID");
                result.append("Course: ");
                String sql_for_course = "SELECT courseName FROM COURSE WHERE courseID = '" + courseID + "'";
                ResultSet rs_course = stmt.executeQuery(sql_for_course);
                if(rs_course.next()){
                    result.append(rs_course.getString("courseName"));
                    result.append("   ");
                } 
                result.append("SectionID: ");
                result.append(sectionID);
                result.append("   ");
                result.append("Score: ");
                result.append(rs.getString("score"));
                result.append("\n");
                String sql_for_attendance = "SELECT * FROM ATTENDANCE WHERE studentID = '" + studentID + 
                                            "' AND sectionID = '" + sectionID + "'";
                ResultSet rs_attendance = stmt.executeQuery(sql_for_attendance);
                while(rs_attendance.next()){
                    result.append("Date: ");
                    result.append(rs_attendance.getString("date"));
                    result.append("   ");
                    result.append("Status: ");
                    result.append(rs_attendance.getString("status"));
                    result.append("   ");
                    result.append("Score: ");
                    if(rs_attendance.getString("status").equals("PRESENT")){
                        result.append("1");
                    } else if(rs_attendance.getString("status").equals("TARDY")){
                        result.append("0.8");
                    } else {
                        result.append("0");
                    }
                    result.append("\n");
                }
            }
            return result.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
}
