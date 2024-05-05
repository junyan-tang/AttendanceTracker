package edu.duke.ece651.team4.JavafxInterf;

import edu.duke.ece651.team4.shared.*;

import java.util.List;

public class StuController {
  public static Boolean addUser(String netid, String firstName, String lastName, String preferredName, String password,
      String identity, String email) {
    UserDAO helper = new UserDAOImpl();
    User user;
    if(identity.equals("professor")){
      user = new Professor(netid, firstName, lastName, preferredName, LoginManager.hashPassword(password), identity, email);
    }else{
      user = new Student(netid, firstName, lastName, preferredName, LoginManager.hashPassword(password), identity, email);
    }
    if (helper.getUser(user.netid) != null) {
      return false;
    }
    helper.createUser(user);
    return true;
  }

  public static Boolean updateUser(String netid, String firstName, String lastName, String preferredName, String password,
      String identity, String email) {
    UserDAO helper = new UserDAOImpl();
    User user = helper.getUser(netid);
    if (user == null) {
      return false;
    }
    user.firstName = firstName;
    user.lastName = lastName;
    user.preferredName = preferredName;
    user.password = LoginManager.hashPassword(password);
    user.identity = identity;
    user.email = email;
    helper.updateUser(user);
    return true;
  }

  public static Boolean deleteUser(String netid) {
    UserDAO helper = new UserDAOImpl();
    User user = helper.getUser(netid);
    if (user == null) {
      return false;
    }
    helper.deleteUser(netid);
    return true;
  }

  public static List<User> getAllTeacher() {
    UserDAO helper = new UserDAOImpl();
    return helper.getAllProfessor();
  }

  public static List<User> getAllStudent() {
    UserDAO helper = new UserDAOImpl();
    return helper.getAllStudent();
  }

  public static List<String> getStudentSections(String sectionID) {
    RosterDAO helper = new RosterDAOImpl();
    return helper.getStudentsInSection(sectionID);
  }

  public static List<String> getStudentSectionsByStudentID(String studentID) {
    RosterDAO helper = new RosterDAOImpl();
    return helper.getSectionsOfStudent(studentID);
  }

  public static Boolean isStudentInSection(String studentID, String sectionID) {
    RosterDAO helper = new RosterDAOImpl();
    return helper.isStudentInSection(studentID, sectionID);
  }

  public static Boolean addStudentToSection(String studentID, String sectionID) {
    RosterDAO helper = new RosterDAOImpl();
    if (helper.isStudentInSection(studentID, sectionID)) {
      return false;
    }
    try{
    helper.addStudentToSection(studentID, sectionID);
    return true;
    }catch(Exception e){
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  public static Boolean removeStudentFromSection(String studentID, String sectionID) {
    RosterDAO helper = new RosterDAOImpl();
    if (!helper.isStudentInSection(studentID, sectionID)) {
      return false;
    }
    helper.removeStudentFromSection(studentID, sectionID);
    return true;
  }
}
