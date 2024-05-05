package edu.duke.ece651.team4.TrackerServer.dto;

import java.util.List;

public class CourseSelectionDto {
    private String course_name;
    private String course_id;
    private List<SectionDto> sections;
    public String getCourse_name() {
        return course_name;
    }
    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }
    public String getCourse_id() {
        return course_id;
    }
    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }
    public List<SectionDto> getSections() {
        return sections;
    }
    public void setSections(List<SectionDto> sections) {
        this.sections = sections;
    }
}
