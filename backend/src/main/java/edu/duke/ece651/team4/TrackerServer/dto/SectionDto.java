package edu.duke.ece651.team4.TrackerServer.dto;

public class SectionDto {
    private String section_id;
    private Boolean assigned_to_professor;
    private String professor_id;
    public String getSection_id() {
        return section_id;
    }
    public void setSection_id(String section_id) {
        this.section_id = section_id;
    }
    public Boolean getAssigned_to_professor() {
        return assigned_to_professor;
    }
    public void setAssigned_to_professor(Boolean assigned_to_professor) {
        this.assigned_to_professor = assigned_to_professor;
    }
    public String getProfessor_id() {
        return professor_id;
    }
    public void setProfessor_id(String professor_id) {
        this.professor_id = professor_id;
    }
}
