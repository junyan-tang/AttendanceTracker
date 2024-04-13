package edu.duke.ece651.team4.CourseManagementApp;

import edu.duke.ece651.team4.shared.*;

import java.util.List;

public class StuController {
  public static List<User> getAllStudent() {
    UserDAO helper = new UserDAOImpl();
    return helper.getAllStudent();
  }

  public static List<String> getStudentSections(String sectionID) {
    RosterDAO helper = new RosterDAOImpl();
    return helper.getStudentsInSection(sectionID);
  }

  public static Boolean addStudentToSection(String studentID, String sectionID) {
    RosterDAO helper = new RosterDAOImpl();
    if (helper.isStudentInSection(studentID, sectionID)) {
      return false;
    }
    helper.addStudentToSection(studentID, sectionID);
    return true;
  }
}
