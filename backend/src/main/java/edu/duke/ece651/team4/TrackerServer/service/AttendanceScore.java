package edu.duke.ece651.team4.TrackerServer.service;

public class AttendanceScore {
    private String sectionId;
    private String netid;
    private String firstname;
    private String lastname;
    private Double score;

    public AttendanceScore(String sectionId, String netid, String firstname, String lastname, Double score) {
        this.sectionId = sectionId;
        this.netid = netid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.score = score;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getSectionId() {
        return sectionId;
    }
    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }
    public String getNetid() {
        return netid;
    }
    public void setNetid(String netid) {
        this.netid = netid;
    }
    public Double getScore() {
        return score;
    }
    public void setScore(Double score) {
        this.score = score;
    }
    
}
