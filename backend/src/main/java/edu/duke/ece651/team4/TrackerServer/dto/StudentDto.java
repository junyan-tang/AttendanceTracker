package edu.duke.ece651.team4.TrackerServer.dto;

public class StudentDto {
    private String netid;
    private String full_name;
    private String attendance_status;
    public String getNetid() {
        return netid;
    }
    public void setNetid(String netid) {
        this.netid = netid;
    }
    public String getFull_name() {
        return full_name;
    }
    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
    public String getAttendance_status() {
        return attendance_status;
    }
    public void setAttendance_status(String attendance_status) {
        this.attendance_status = attendance_status;
    }   
}
