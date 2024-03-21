package edu.duke.ece651.team4.server;

import java.util.LinkedHashMap;

// import java.io.BufferedReader;
// import java.io.PrintStream;

public class Lecture implements Course {
  private int courseId;
  private String courseName;
  private LinkedHashMap<String, User> studentMap;
  private LinkedHashMap<String, User> professorMap;
  private LinkedHashMap<String, Attendance> attendanceMap;
  private MessageSender messageSender;

  
  // final BufferedReader inputReader;
  // final PrintStream outputWriter;

  public int getId() {
    return courseId;
  }

  public String getName() {
    return courseName;
  }

  public Lecture(int courseId,
      String courseName,
      LinkedHashMap<String, User> studentMap,
      LinkedHashMap<String, User> professorMap,
      LinkedHashMap<String, Attendance> attendanceMap,
      MessageSender messageSender
      // BufferedReader inputReader,
      // PrintStream outputWriter
      ) {
    this.courseId = courseId;
    this.courseName = courseName;
    this.studentMap = studentMap;
    this.professorMap = professorMap;
    this.attendanceMap = attendanceMap;
    this.messageSender = messageSender;
    // this.inputReader = inputReader;
    // this.outputWriter = outputWriter;
  }

  public Boolean modifyName(String oldName, String newName) {
    if (studentMap.containsKey(oldName)) {

      User tempStudent = studentMap.get(oldName);
      studentMap.remove(oldName);
      tempStudent.changeName(newName);
      studentMap.put(newName, tempStudent);
      return true;
    } else {
      return false;
    }
  }

  public void notifyAll(String message) {
    for (User student : studentMap.values()) {
      student.updateStatus(message);
    }

    for (User professor : professorMap.values()) {
      professor.updateStatus(message);
    }

  }

  public String generateReport() {
    String report = "";
    for (Attendance attendance : attendanceMap.values()) {
      report += attendance.getReport();
    }
    return report;
  }

  public void addStudent(User student) {
    studentMap.put(student.getName(), student);
  }

  public Boolean removeStudent(String studentName) {
    if (!studentMap.containsKey(studentName)) {
      return false;
    }
    studentMap.remove(studentName);
    return true;
  }

  public void addProfessor(User professor) {
    professorMap.put(professor.getName(), professor);
  }

  public Boolean removeProfessor(String professorName) {
    if (!professorMap.containsKey(professorName)) {
      return false;
    }
    professorMap.remove(professorName);
    return true;
  }

  public void addAttendance(Attendance attendance) {
    attendanceMap.put(attendance.getCourseDate().toString(), attendance);
  }

  public Boolean removeAttendance(String date) {
    if (!attendanceMap.containsKey(date)) {
      return false;
    }
    attendanceMap.remove(date);
    return true;
  }

  // public void haveClass(){

  //   Attendance new_attendance = new Attendance(courseId, courseName, new LinkedHashMap<User, AttendanceStatus>());
  //   for (User student : studentMap.values()) {
  //     new_attendance.recordAttendance(student, AttendanceStatus.DEFAULT, "");
  //   }

  // }

}
