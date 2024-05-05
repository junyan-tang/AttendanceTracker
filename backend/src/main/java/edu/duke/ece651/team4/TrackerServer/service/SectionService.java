package edu.duke.ece651.team4.TrackerServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import edu.duke.ece651.team4.TrackerServer.entity.Section;
import edu.duke.ece651.team4.TrackerServer.entity.Course;
import edu.duke.ece651.team4.TrackerServer.entity.User;
import edu.duke.ece651.team4.TrackerServer.repository.SectionRepository;
import edu.duke.ece651.team4.TrackerServer.repository.CourseRepository;
import edu.duke.ece651.team4.TrackerServer.repository.UserRepository;

@Service
public class SectionService {
    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    public void addSection(String sectionID, String courseID, String teacherID) {
        Course course = courseRepository.findById(courseID).get();
        User teacher = userRepository.findById(teacherID).get();
        Section section = new Section(sectionID, course, teacher);
        sectionRepository.save(section);
    }

    public void addTeacherToSection(String sectionID, String teacherID) {
        Section section = getSection(sectionID);
        User teacher = userRepository.findById(teacherID).get();
        section.setTeacher(teacher);
        sectionRepository.save(section);
    }

    public void addSection(Section section){
        sectionRepository.save(section);
    }
    public Section getSection(String sectionID){
        if(sectionRepository.findById(sectionID).isEmpty()){
            return null;
        }
        return sectionRepository.findById(sectionID).get();
    }
    public void removeSection(String sectionID){
        sectionRepository.deleteById(sectionID);
    }
    public List<Section> getAllSections(){
        return sectionRepository.findAll();
    }
    public List<Section> getAllSectionsCour(String courseID){
        return sectionRepository.findByCourseID(courseID);
    }
    public List<Section> getAllSectionsTea(String teacherID){
        return sectionRepository.findByTeacherID(teacherID);
    }
    public void updateSection(Section section){
        sectionRepository.save(section);
    }

    public void updateSection(String sectionID, String teacherID) {
        User teacher = userRepository.findById(teacherID).get();
        Section section = sectionRepository.findById(sectionID).get();
        section.setTeacher(teacher);
        sectionRepository.save(section);
    }

    public Course getCourse(String sectionID){
        return sectionRepository.findById(sectionID).get().getCourse();
    }

    // public boolean teacherSelectSection(String teacherID, String sectionID){
    //     return false;
    // }

    // public List<User> getAllStudentsInSection(String sectionID){
    //     return null;
    // }
    // public boolean isTeacherHasThatSection(String teacherID, String sectionID){
    //     return false;
    // }

    // public Professor getProfessor(String sectionID){
    //     return null;
    // }
    
    // public String generateWeekReport(String sectionID){
    //     return null;
    // }

}
