package edu.duke.ece651.team4.shared;

import java.util.List;
import java.time.Instant;

public interface AttendanceDAO {
    public Attendance getAttendance(String studentID, String sectionID, Instant date);
    public List<Attendance> getAttendance(String studentID, String sectionID);
    public void addAttendance(Attendance attendance);
    public void updateAttendance(Attendance attendance);
    public void deleteAttendance(String studentID, String sectionID, Instant date);
    public List<Attendance> getAttendanceListStu(String studentID);
    public List<Attendance> getAttendanceListSec(String sectionID);
    public List<Attendance> getAttendanceListSecDate(String sectionID, Instant date);
    public List<Attendance> getAttendanceListSecDateSta(String sectionID, Instant date, String status);
    public List<String> getAttendanceDatelist(String sectionID);
    public List<String> getAttendanceStulist(String sectionID, Instant date);
    public String getAttendanceStuScoreInSec(String sectionID);
    public String getAttendanceStuScore(String studentID);
}
