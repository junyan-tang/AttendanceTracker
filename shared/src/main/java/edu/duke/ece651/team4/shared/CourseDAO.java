package edu.duke.ece651.team4.shared;
import java.util.List;

public interface CourseDAO {
    public void addCourse(Course course);
    public Course getCourseByID(String courseID);
    public Course getCourse(String courseName);
    public void updateCourse(Course course);
    //public void deleteCourse(String courseID);
    public void deleteCourse(String courseName);
    public List<Course> getAllCourses();
    public String generateWeekReport(String courseName);
}
