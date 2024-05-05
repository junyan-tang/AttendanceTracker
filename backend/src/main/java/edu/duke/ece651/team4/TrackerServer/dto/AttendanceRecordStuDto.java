package edu.duke.ece651.team4.TrackerServer.dto;

public class AttendanceRecordStuDto {
    private String date;
    private String status;

    public AttendanceRecordStuDto(){}
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
