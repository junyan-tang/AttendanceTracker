package edu.duke.ece651.team4.shared;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;

public class CourseDAOImpl implements CourseDAO{
    /**
     * Adds course
     * @param course course information
     */
    public void addCourse(Course course){
        Connection conn = DatabaseConnectionUtil.getConnection();
        try{
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO COURSE (courseID, courseName) VALUES ('" + 
                        course.courseID + "', '" + course.courseName + "')";
            stmt.executeUpdate(sql);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * Gets all courses
     * @param courseID unique ID of course
     * @return list of courses
     */
    public Course getCourseByID(String courseID){
        Connection conn = DatabaseConnectionUtil.getConnection();
        try{
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM COURSE WHERE courseID = '" + courseID+ "'");
            while (res.next()){
                Course course = new Course(res.getString("courseID"), res.getString("courseName"));
                return course;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets course by name
     * @param courseName course name
     * @return course information
     */
    public Course getCourse(String courseName) {
        Connection conn = DatabaseConnectionUtil.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM COURSE WHERE courseName = '" + courseName + "'");
            while (res.next()) {
                Course course = new Course(res.getString("courseID"), res.getString("courseName"));
                // System.out.println("1 done");
                return course;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Updates course
     * @param course course information
     */
    public void updateCourse(Course course){
        Connection conn = DatabaseConnectionUtil.getConnection();
        try {
            Statement stmt = conn.createStatement();
            String sql = "UPDATE COURSE SET courseName = '" + course.courseName + "' WHERE courseID = '" + course.courseID + "'";
            stmt.executeUpdate(sql);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    /**
     * Deletes course
     * @param courseName course name
     */
    public void deleteCourse(String courseName){
        Connection conn = DatabaseConnectionUtil.getConnection();
        try {
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM COURSE WHERE courseName = '" + courseName + "'";
            stmt.executeUpdate(sql);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Gets all courses
     * @return list of courses
     */
    public List<Course> getAllCourses(){
        List<Course> courses = new ArrayList<>();
        Connection conn = DatabaseConnectionUtil.getConnection();
        try{
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM COURSE");

            while (res.next()) {
                Course course = new Course(res.getString("courseID"), res.getString("courseName"));
                courses.add(course);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return courses;
    }    

    public String generateWeekReport(String courseName){
        return null;
    }
}
