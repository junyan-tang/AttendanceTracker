package edu.duke.ece651.team4.server;
import java.util.HashMap;
import java.util.Date;

public abstract class AttendanceView<T> {
    protected HashMap<Date, Attendance> attendanceMap;
    public abstract T displayAttendance();
}