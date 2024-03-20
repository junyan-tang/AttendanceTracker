package edu.duke.ece651.team4.server;

import java.util.Date;
import java.util.LinkedHashMap;
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

  public String getReport() {
    StringBuilder sb = new StringBuilder();
    sb.append("Course: " + courseName + "\n");
    sb.append("Course" + courseId + "\n");
    sb.append("Date: " + courseDate + "\n");
    for (User user : attendanceRecord.keySet()) {
      if(attendanceRecord.get(user) == AttendanceStatus.EXCUSED){
        sb.append(user.getName() + " " + attendanceRecord.get(user) + " " + excuse.get(user) + "\n");
      }else{
        sb.append(user.getName() + " " + attendanceRecord.get(user) + "\n");
      }
    }
    return sb.toString();
  }
  
  public void recordAttendance(User user, AttendanceStatus status, String excuse) {
    attendanceRecord.put(user, status);
    if (status == AttendanceStatus.EXCUSED) {
      this.excuse.put(user, excuse);
    }
  }
}
