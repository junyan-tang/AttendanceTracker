package edu.duke.ece651.team4.TrackerServer.service;

public class StudentScore {
    private String studentID;
    private Double score;
    public StudentScore(String studentID, Double score) {
        this.studentID = studentID;
        this.score = score;
    }
    public String getStudentID() {
        return studentID;
    }
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
    public Double getScore() {
        return score;
    }
    public void setScore(Double score) {
        this.score = score;
    }
}
