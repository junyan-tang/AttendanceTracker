package edu.duke.ece651.team4.JavafxInterf;

import edu.duke.ece651.team4.shared.*;

import java.util.List;

public class SectionController {
  public static Boolean addSection(String sectionID, String courseID, String teacherID) {
    Section section = new Section(sectionID, courseID, teacherID);
    SectionDAO sectionDAO = new SectionDAOImpl();
    CourseDAO courseDAO = new CourseDAOImpl();
    if (courseDAO.getCourseByID(courseID) == null) {
      return false;
    }
    if (sectionDAO.getSection(sectionID) != null) {
      return false;
    }
    sectionDAO.addSection(section);
    return true;
  }

  public static Boolean deleteSection(String sectionID) {
    SectionDAO sectionDAO = new SectionDAOImpl();
    Section section = sectionDAO.getSection(sectionID);
    if (section == null) {
      return false;
    }
    sectionDAO.removeSection(sectionID);
    return true;
  }

  public static Boolean updateSection(String sectionID, String teacherID){
    SectionDAO sectionDAO = new SectionDAOImpl();
    Section section = sectionDAO.getSection(sectionID);
    if (section == null) {
      return false;
    }
    section.teacherID = teacherID;
    sectionDAO.updateSection(section);
    return true;
  }

  public static List<Section> getAllSectionsCour(String courseID) {
    SectionDAO sectionDAO = new SectionDAOImpl();
    return sectionDAO.getAllSectionsCour(courseID);
  }

  public static List<Section> getAllSections() {
    SectionDAO helper = new SectionDAOImpl();
    return helper.getAllSections();
  }
}
