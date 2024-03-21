package edu.duke.ece651.team4.server;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.HashMap;

enum AttendanceStatus {
  PRESENT, ABSENT, TARDY, EXCUSED, DEFAULT
}

public class Attendance {
  private Date courseDate;
  private int courseId;
  private String courseName;
  private LinkedHashMap<User, AttendanceStatus> attendanceRecord;
  private HashMap<User, String> excuse;


  public Attendance(int courseId, String courseName, LinkedHashMap<User, AttendanceStatus> attendanceRecord) {
    this.courseId = courseId;
    this.courseName = courseName;
    this.courseDate = new Date(courseDate.getTime());
    this.attendanceRecord = attendanceRecord;
  }

  public Date getCourseDate() {
    return courseDate;
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

  public HashMap<User, String> getExcuse() {
    return excuse;
  }

  public String getReport() {
    StringBuilder sb = new StringBuilder();
    sb.append("Course: " + courseName + "\n");
    sb.append("Course" + courseId + "\n");
    sb.append("Date: " + courseDate + "\n");
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
}
