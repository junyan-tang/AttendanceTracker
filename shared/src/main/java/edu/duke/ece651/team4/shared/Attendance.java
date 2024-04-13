package edu.duke.ece651.team4.shared;
import java.time.Instant;

public class Attendance {
    public String courseID;
    public String sectionID;
    public String studentID;
    public Instant attendanceTime;
    public String status;

    public Attendance(String courseID, String sectionID, String studentID, Instant attendanceTime, String status) {
        this.courseID = courseID;
        this.sectionID = sectionID;
        this.studentID = studentID;
        this.attendanceTime = attendanceTime;
        this.status = status;
    }
}
