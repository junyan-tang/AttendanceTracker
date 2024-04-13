package edu.duke.ece651.team4.CourseManagementApp;

import edu.duke.ece651.team4.shared.*;

import java.time.Instant;

import java.util.List;

import java.time.LocalDate;
import java.time.ZoneId;

public class AttendanceController {
  public static Boolean addAttendence(String studentID, String sectionID, String status){
    AttendanceDAO attendenceDAO = new AttendanceDAOImpl();
    SectionDAO sectionDAO = new SectionDAOImpl();
    Section curSection = sectionDAO.getSection(sectionID);
    // AttendenceDAO attendenceDAO = new AttendenceDAOImpl();
    if (attendenceDAO.getAttendance(studentID, sectionID, Instant.now()) != null) {
      // throw new IllegalArgumentException("Attendence already exists");
      return false;
    }
    attendenceDAO.addAttendance(new Attendance(curSection.courseID, sectionID,  studentID, Instant.now(), status));
    return true;
  }

  public static Boolean addAttendanceDated(String studentID, String sectionID, Instant date, String status){
    AttendanceDAO attendenceDAO = new AttendanceDAOImpl();
    SectionDAO sectionDAO = new SectionDAOImpl();
    Section curSection = sectionDAO.getSection(sectionID);
    // AttendenceDAO attendenceDAO = new AttendenceDAOImpl();
    if (attendenceDAO.getAttendance(studentID, sectionID, date) != null) {
      // throw new IllegalArgumentException("Attendence already exists");
      return false;
    }
    attendenceDAO.addAttendance(new Attendance(curSection.courseID, sectionID,  studentID, date, status));
    return true;
  }

  public static Boolean removeAttendence(String studentID, String sectionID, Instant date){
    AttendanceDAO attendenceDAO = new AttendanceDAOImpl();
    if (attendenceDAO.getAttendance(studentID, sectionID, date) == null) {
      // throw new IllegalArgumentException("Attendence does not exist");
      return false;
    }
    attendenceDAO.deleteAttendance(studentID, sectionID, date);
    return true;
  }

  public static Boolean updateAttendence(String studentID, String sectionID, Instant date, String status){
    AttendanceDAO attendenceDAO = new AttendanceDAOImpl();
    Attendance attendence = attendenceDAO.getAttendance(studentID, sectionID, date);
    if (attendence == null) {
      // throw new IllegalArgumentException("Attendence does not exist");
      return false;
    }
    attendence.status = status;
    attendenceDAO.updateAttendance(attendence);
    return true;
  }

  public static List<Attendance> getAttendenceListStuSec(String studentID, String sectionID) {
    AttendanceDAO attendenceDAO = new AttendanceDAOImpl();
    return attendenceDAO.getAttendance(studentID, sectionID);
  }

  public static List<Attendance> getAttendenceListStu(String studentID) {
    AttendanceDAO attendenceDAO = new AttendanceDAOImpl();
    return attendenceDAO.getAttendanceListStu(studentID);
  }

  public static List<Attendance> getAttendenceListSec(String sectionID) {
    AttendanceDAO attendenceDAO = new AttendanceDAOImpl();
    return attendenceDAO.getAttendanceListSec(sectionID);
  }

  public static List<String> getAttendenceDatelist(String sectionID) {
    AttendanceDAO attendenceDAO = new AttendanceDAOImpl();
    return attendenceDAO.getAttendanceDatelist(sectionID);
  }

  public static List<String> getAttendenceStulist(String sectionID, Instant date) {
    AttendanceDAO attendenceDAO = new AttendanceDAOImpl();
    return attendenceDAO.getAttendanceStulist(sectionID, date);
  }

  public static Instant parseDate(String date_) {
    LocalDate date = LocalDate.parse(date_);
    return date.atStartOfDay(ZoneId.of("America/New_York")).toInstant();
  }
  // public static String generateWeekReport(String courseName) {
  //   AttendanceDAO attendenceDAO = new AttendanceDAOImpl();
  //   return attendenceDAO.generateWeekReport(courseName);
  // }
  
}
