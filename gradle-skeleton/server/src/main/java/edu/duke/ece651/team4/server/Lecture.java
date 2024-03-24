package edu.duke.ece651.team4.server;

import java.util.LinkedHashMap;
import java.time.Instant;
import java.time.Duration;
import java.util.List;
import java.util.ArrayList;

// import java.util.Timer;
// import java.util.TimerTask;
// import java.util.Date;



public class Lecture implements Course {
  private int courseId;
  private String courseName;
  private LinkedHashMap<String, User> studentMap;
  private LinkedHashMap<String, User> professorMap;
  private LinkedHashMap<String, Attendance> attendanceMap;
  private MessageSender messageSender;
  // private Timer timer;
  // private TimerTask task;
  


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
      ) {
    this.courseId = courseId;
    this.courseName = courseName;
    this.studentMap = studentMap;
    this.professorMap = professorMap;
    this.attendanceMap = attendanceMap;
    this.messageSender = messageSender;
    // this.timer = new Timer();
    // this.task = new TimerTask() {
    //   @Override
    //   public void run() {
    //     professorMap.forEach((key, value) -> notifySingle(generateReport(), value));
    //   }
    // };
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
      // student.updateStatus(message);
      notifySingle(message, student);
    }

    for (User professor : professorMap.values()) {
      //professor.updateStatus(message);
      notifySingle(message, professor);
    }

  }

  public void notifySingle(String message, User user) {
    try{
      user.updateStatus(message, this.messageSender);
    }catch(Exception e){
      System.out.println("Failed to send message to " + user.getName());
      System.out.println(e);
    }
  }

  public String generateReport() {
    String report = "";
    for (Attendance attendance : attendanceMap.values()) {
      if(Duration.between(attendance.getCourseDate(), Instant.now()).toDays() > 7){
        continue;
      }
      report += attendance.getReport();
    }
    return report;
  }

  public List<User> getStudentList() {
    return new ArrayList<User>(studentMap.values());
  }

  // public Boolean hasStudentInLecture(String studentName) {
  //   return studentMap.containsKey(studentName);
  // }

  public List<User> getProfessorList() {
    return new ArrayList<User>(professorMap.values());
  }

  public List<String> getAttendaceDateList() {
    return new ArrayList<String>(attendanceMap.keySet());
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

  public Boolean hasStudent(String date, String studentName) {
    if (!attendanceMap.containsKey(date)) {
      return false;
    }
    return attendanceMap.get(date).hasStudent(studentName);
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
    attendanceMap.put(attendance.getCourseDateStr(), attendance);
  }

  public Boolean removeAttendance(String date) {
    if (!attendanceMap.containsKey(date)) {
      return false;
    }
    attendanceMap.remove(date);
    return true;
  }

  public Attendance getAttendance(String date) {
    return attendanceMap.get(date);
  }

  public Boolean modifyCertainAttendance(String date, String studentName, AttendanceStatus status,String excuse){
    if ((!attendanceMap.containsKey(date)) || (!attendanceMap.get(date).hasStudent(studentName))) {
      return false;
    }
    Attendance attendance = attendanceMap.get(date);
    attendance.recordAttendance(attendance.getStudent(studentName), status, excuse);
    return true;
  }

  public Boolean hasAttendance(String date) {
    return attendanceMap.containsKey(date);
  }

}
