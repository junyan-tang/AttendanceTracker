package edu.duke.ece651.team4.shared;
import edu.duke.ece651.team4.CourseManagementApp.Course;

public interface CourseDAO {
    public void addCourse(Course course);
    public Course getCourse(String courseName);
    public void updateCourse(Course course);
    public void deleteCourse(String courseName);
}
