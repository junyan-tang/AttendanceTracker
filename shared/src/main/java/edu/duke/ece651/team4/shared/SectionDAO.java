package edu.duke.ece651.team4.shared;

import java.util.List;

public interface SectionDAO {
    public void addSection(Section section);
    public Section getSection(String sectionID);
    public void removeSection(String sectionID);
    public List<Section> getAllSections();
    public List<Section> getAllSectionsCour(String courseID);
    public List<Section> getAllSectionsTea(String teacherID);
    public void updateSection(Section section);
    public boolean teacherSelectSection(String teacherID, String sectionID);
    public boolean isTeacherHasSection(String teacherID);
    public List<Student> getAllStudentsInSection(String sectionID);
    public boolean isTeacherHasThatSection(String teacherID, String sectionID);

    public Professor getProfessor(String sectionID);
    public String generateWeekReport(String sectionID);
}
