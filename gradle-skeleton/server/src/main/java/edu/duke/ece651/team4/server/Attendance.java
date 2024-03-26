package edu.duke.ece651.team4.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

// enum AttendanceStatus {
//   PRESENT, ABSENT, TARDY, EXCUSED, DEFAULT
// }

public class Attendance {
  private Instant courseDate;
  private int courseId;
  private String courseName;
  private LinkedHashMap<User, AttendanceStatus> attendanceRecord;
  private HashMap<User, String> excuse;


  public Attendance(int courseId, String courseName, List<User> studentList) {
    this.courseId = courseId;
    this.courseName = courseName;
    this.courseDate = Instant.now();
    this.excuse = new HashMap<>();
    this.attendanceRecord = new LinkedHashMap<>();
    this.excuse = new HashMap<>();
    for(User student : studentList) {
      attendanceRecord.put(student, AttendanceStatus.DEFAULT);
    }
  }

  public Instant getCourseDate() {
    return courseDate;
  }

  public String getCourseDateStr() {
    ZonedDateTime zonedDateTime = courseDate.atZone(ZoneId.of("America/New_York"));

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    return zonedDateTime.format(formatter);
  }

  public int getCourseId() {
    return courseId;
  }

  public String getCourseName() {
    return courseName;
  }

  public LinkedHashMap<User, AttendanceStatus> getAttendanceRecord() {
    return attendanceRecord;
  }

  public List<User> getStudentList() {
    return List.copyOf(attendanceRecord.keySet());
  }

  public AttendanceStatus getAttendanceStatus(User user) {
    return attendanceRecord.get(user);
  }

  public HashMap<User, String> getExcuse() {
    return excuse;
  }

  public String getReport() {
    StringBuilder sb = new StringBuilder();
    sb.append("Course: " + courseName + "\n");
    sb.append("Course ID: " + courseId + "\n");
    sb.append("Date: " + this.getCourseDateStr() + "\n");
    int present = 0;
    int absent = 0;
    int tardy = 0;
    int excused = 0;
    for (User user : attendanceRecord.keySet()) {
      if(attendanceRecord.get(user) == AttendanceStatus.PRESENT){
        present++;
      }
      if(attendanceRecord.get(user) == AttendanceStatus.ABSENT){
        absent++;
      }
      if(attendanceRecord.get(user) == AttendanceStatus.TARDY){
        tardy++;
      }
      if(attendanceRecord.get(user) == AttendanceStatus.EXCUSED){
        excused++;
      }
      if(attendanceRecord.get(user) == AttendanceStatus.EXCUSED){
        sb.append(user.getName() + " " + attendanceRecord.get(user) + " " + excuse.get(user) + "\n");
      }else{
        sb.append(user.getName() + " " + attendanceRecord.get(user) + "\n");
      }
    }
    sb.append("Present: " + present + "\n");
    sb.append("Absent: " + absent + "\n");
    sb.append("Tardy: " + tardy + "\n");
    sb.append("Excused: " + excused + "\n");
    return sb.toString();
  }
  
  public void recordAttendance(User user, AttendanceStatus status, String excuse) {
    attendanceRecord.put(user, status);
    if (status == AttendanceStatus.EXCUSED) {
      this.excuse.put(user, excuse);
    }
  }

  public Boolean hasStudent(String studentName) {
    for (User student : attendanceRecord.keySet()) {
      if (student.getName().equals(studentName)) {
        return true;
      }
    }
    return false;
  }

  public User getStudent(String studentName) {
    for (User student : attendanceRecord.keySet()) {
      if (student.getName().equals(studentName)) {
        return student;
      }
    }
    return null;
  }

  public void autoExportAttendance() throws IOException {
    ZonedDateTime zonedDateTime = courseDate.atZone(ZoneId.of("America/New_York"));
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String date = zonedDateTime.format(formatter);
    String f = "src/main/resources/attendance_" + date + ".csv";
    File attendanceRec = new File(f);
    attendanceRec.createNewFile();
    FileOutputStream outputFile = new FileOutputStream(attendanceRec, true);
    String id = "Course ID," + Integer.toString(courseId) + "\n";
    String name = "Course Name," + courseName + "\n";
    date = "Course Date," + date + "\n";
    String header = "Name,Attendance\n";
    byte[] bytes = id.getBytes();
    outputFile.write(bytes);
    bytes = name.getBytes();
    outputFile.write(bytes);
    bytes = date.getBytes();
    outputFile.write(bytes);
    bytes = header.getBytes();
    outputFile.write(bytes);
    for (Map.Entry<User, AttendanceStatus> entry : attendanceRecord.entrySet()) {
      User stu = entry.getKey();
      AttendanceStatus status = entry.getValue();
      String stuName = stu.getName();
      String stat = status.getShortCode();
      String row = stuName + "," + stat + "\n";
      bytes = row.getBytes();
      outputFile.write(bytes);
    }
    outputFile.close();
  }
}
