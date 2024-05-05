package edu.duke.ece651.team4.TrackerServer.dto;

public class AttendanceModifyDto {
    private String date;
    private String section_id;
    private String student_id;
    private String new_status;

    public AttendanceModifyDto(){}
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getSection_id() {
        return section_id;
    }
    public void setSection_id(String section_id) {
        this.section_id = section_id;
    }
    public String getStudent_id() {
        return student_id;
    }
    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }
    public String getNew_status() {
        return new_status;
    }
    public void setNew_status(String new_status) {
        this.new_status = new_status;
    }
}
