package edu.duke.ece651.team4.TrackerServer.dto;

public class CourseDisplayDto {
    private String courseName;
    private String sectionID;
    private String teacherName;
    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    public String getSectionID() {
        return sectionID;
    }
    public void setSectionID(String sectionID) {
        this.sectionID = sectionID;
    }
    public String getTeacherName() {
        return teacherName;
    }
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
