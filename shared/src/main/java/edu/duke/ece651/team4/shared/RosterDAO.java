package edu.duke.ece651.team4.shared;

import java.util.List;

public interface RosterDAO {
    public void addStudentToSection(String studentID, String sectionID);
    public boolean isStudentInSection(String studentID, String sectionID);
    public void removeStudentFromSection(String studentID, String sectionID);
    public List<String> getStudentsInSection(String sectionID); 
}
