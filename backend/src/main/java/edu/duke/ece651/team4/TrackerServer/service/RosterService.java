package edu.duke.ece651.team4.TrackerServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import edu.duke.ece651.team4.TrackerServer.entity.Roster;
import edu.duke.ece651.team4.TrackerServer.entity.Section;
import edu.duke.ece651.team4.TrackerServer.entity.User;
import edu.duke.ece651.team4.TrackerServer.repository.RosterRepository;

@Service
public class RosterService {
    @Autowired
    private RosterRepository rosterRepository;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private UserService userService;

    public void addStudentToSection(String studentID, String sectionID){
        Section section = sectionService.getSection(sectionID);
        User student = userService.getUser(studentID);
        Roster roster = new Roster();
        roster.setStudent(student);
        roster.setSection(section);
        rosterRepository.save(roster);
    }

    public boolean isStudentInSection(String studentID, String sectionID){
        if (rosterRepository.findByStudentIdAndSectionId(studentID, sectionID).isPresent()){
            return true;
        }
        return false;
    }

    public void removeStudentFromSection(String studentID, String sectionID){
        Roster roster = rosterRepository.findByStudentIdAndSectionId(studentID, sectionID).get();
        rosterRepository.delete(roster);
    }

    public List<String> getStudentsInSection(String sectionID){
        List<String> students = new ArrayList<>();
        List<Roster> rosters = rosterRepository.findBySectionId(sectionID);
        for (Roster roster : rosters) {
            students.add(roster.getStudent().getNetid());
        }
        return students;
    }

    public List<String> getSectionsOfStudent(String studentID){
        List<String> sections = new ArrayList<>();
        List<Roster> rosters = rosterRepository.findByStudentId(studentID);
        for (Roster roster : rosters) {
            sections.add(roster.getSection().getSectionID());
        }
        return sections;
    }
}
