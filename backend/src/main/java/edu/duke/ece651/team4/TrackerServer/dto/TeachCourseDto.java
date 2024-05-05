package edu.duke.ece651.team4.TrackerServer.dto;

public class TeachCourseDto {
    private String netid;
    private String section_id;
    public TeachCourseDto(){}
    public String getNetid() {
        return netid;
    }
    public void setNetid(String netid) {
        this.netid = netid;
    }
    public String getSection_id() {
        return section_id;
    }
    public void setSection_id(String section_id) {
        this.section_id = section_id;
    }
}
