package edu.duke.ece651.team4.CourseManagementApp;

import java.util.List;

import edu.duke.ece651.team4.shared.*;


public class CourseController {
  public static Boolean addCourse(String courseID, String courseName){
    CourseDAO courseDAO = new CourseDAOImpl();
    if (courseDAO.getCourse(courseName) != null || courseDAO.getCourseByID(courseID) != null) {
      // throw new IllegalArgumentException("Course already exists");
      return false;
    }
    courseDAO.addCourse(new Course(courseID, courseName));
    return true;
  }

  public static Boolean removeCourse(String courseName){
    CourseDAO courseDAO = new CourseDAOImpl();
    if (courseDAO.getCourse(courseName) == null) {
      // throw new IllegalArgumentException("Course does not exist");
      return false;
    }
    courseDAO.deleteCourse(courseName);
    return true;
  }

  public static Boolean updateCourse(String courseID, String courseName){
    CourseDAO courseDAO = new CourseDAOImpl();
    Course course = courseDAO.getCourseByID(courseID);
    if (course == null) {
      // throw new IllegalArgumentException("Course does not exist");
      return false;
    }
    course.courseID = courseID;
    course.courseName = courseName;
    courseDAO.updateCourse(course);
    return true;
  }

  public static Boolean deleteCourse(String courseName){
    CourseDAO courseDAO = new CourseDAOImpl();
    if (courseDAO.getCourse(courseName) == null) {
      // throw new IllegalArgumentException("Course does not exist");
      return false;
    }
    courseDAO.deleteCourse(courseName);
    return true;
  }

  public static Course getCourse(String courseName) {
    CourseDAO courseDAO = new CourseDAOImpl();
    return courseDAO.getCourse(courseName);
  }

  public static String getCourseID(String courseName) {
    CourseDAO courseDAO = new CourseDAOImpl();
    Course course = courseDAO.getCourse(courseName);
    if (course == null) {
      return null;
    }
    return course.courseID;
  }

  public static String getCourseName(String courseID) {
    CourseDAO courseDAO = new CourseDAOImpl();
    Course course = courseDAO.getCourse(courseID);
    if (course == null) {
      return null;
    }
    return course.courseName;
  }

  public static List<Course> getAllCourses() {
    CourseDAO courseDAO = new CourseDAOImpl();
    return courseDAO.getAllCourses();
  }  

  public static String generateWeekReport(String courseName) {
    CourseDAO courseDAO = new CourseDAOImpl();
    return courseDAO.generateWeekReport(courseName);
  }
}
