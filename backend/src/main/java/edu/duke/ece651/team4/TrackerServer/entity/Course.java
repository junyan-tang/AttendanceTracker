package edu.duke.ece651.team4.TrackerServer.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "course")
public class Course {
    @Id
    @Column(name = "course_id", length = 20)
    private String courseID;

    @Column(name = "course_name", nullable = false, length = 200)
    private String courseName;

    public Course() {}

    public Course(String courseID, String courseName) {
        this.courseID = courseID;
        this.courseName = courseName;
    }
    
    public String getCourseID() {
        return courseID;
    }
    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }
    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
