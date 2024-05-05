package edu.duke.ece651.team4.TrackerServer.dto;

import java.util.List;

public class AttendanceStuDto {
    private String course_id;
    private String course_name;
    private String section_id;
    private List<AttendanceRecordStuDto> attendance_records;

    public AttendanceStuDto(){}
    public String getCourse_id() {
        return course_id;
    }
    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }
    public String getCourse_name() {
        return course_name;
    }
    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }
    public String getSection_id() {
        return section_id;
    }
    public void setSection_id(String section_id) {
        this.section_id = section_id;
    }
    public List<AttendanceRecordStuDto> getAttendance_records() {
        return attendance_records;
    }
    public void setAttendance_records(List<AttendanceRecordStuDto> attendance_records) {
        this.attendance_records = attendance_records;
    }
}
