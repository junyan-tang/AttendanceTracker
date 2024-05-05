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
            String sql = "INSERT INTO COURSE (course_id, course_name) VALUES ('" + 
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
            ResultSet res = stmt.executeQuery("SELECT * FROM COURSE WHERE course_id = '" + courseID+ "'");
            while (res.next()){
                Course course = new Course(res.getString("course_id"), res.getString("course_name"));
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
            ResultSet res = stmt.executeQuery("SELECT * FROM COURSE WHERE course_name = '" + courseName + "'");
            while (res.next()) {
                Course course = new Course(res.getString("course_id"), res.getString("course_name"));
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
            String sql = "UPDATE COURSE SET course_name = '" + course.courseName + "' WHERE course_id = '" + course.courseID + "'";
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
            String sql = "DELETE FROM COURSE WHERE course_name = '" + courseName + "'";
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
                Course course = new Course(res.getString("course_id"), res.getString("course_name"));
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
