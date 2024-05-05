package edu.duke.ece651.team4.TrackerServer.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "section")
public class Section {
    @Id
    @Column(name = "section_id", length = 20)
    private String sectionID;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher;

    public Section(String sectionID, Course course, User teacher){
        this.sectionID = sectionID;
        this.course = course;
        this.teacher = teacher;
    }

    public Section() {
    }

    public String getSectionID() {
        return sectionID;
    }

    public void setSectionID(String sectionID) {
        this.sectionID = sectionID;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }
}
