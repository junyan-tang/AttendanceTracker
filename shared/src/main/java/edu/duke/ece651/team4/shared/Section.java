package edu.duke.ece651.team4.shared;

public class Section {
    public String sectionID;
    public String courseID;
    public String teacherID;
    
    public Section(String sectionID, String courseID, String teacherID) {
        this.sectionID = sectionID;
        this.courseID = courseID;
        this.teacherID = teacherID;
    }

    public String getSectionID() {
        return sectionID;
    }

    public void setSectionID(String sectionID) {
        this.sectionID = sectionID;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }
}
