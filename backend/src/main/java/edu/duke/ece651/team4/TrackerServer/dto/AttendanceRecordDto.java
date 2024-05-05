package edu.duke.ece651.team4.TrackerServer.dto;

import java.util.List;

public class AttendanceRecordDto {
    private String date;
    private List<StudentDto> students;
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public List<StudentDto> getStudents() {
        return students;
    }
    public void setStudents(List<StudentDto> students) {
        this.students = students;
    }

}
