package edu.duke.ece651.team4.TrackerServer.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.duke.ece651.team4.TrackerServer.dto.AttendanceRecordStuDto;
import edu.duke.ece651.team4.TrackerServer.dto.AttendanceStuDto;
import edu.duke.ece651.team4.TrackerServer.dto.CourseSelectionDto;
import edu.duke.ece651.team4.TrackerServer.dto.SectionDto;
import edu.duke.ece651.team4.TrackerServer.dto.TeachCourseDto;
import edu.duke.ece651.team4.TrackerServer.entity.Attendance;
import edu.duke.ece651.team4.TrackerServer.entity.Section;
import edu.duke.ece651.team4.TrackerServer.entity.User;
import edu.duke.ece651.team4.TrackerServer.service.AttendanceService;
import edu.duke.ece651.team4.TrackerServer.service.CourseService;
import edu.duke.ece651.team4.TrackerServer.service.RosterService;
import edu.duke.ece651.team4.TrackerServer.service.SectionService;
import edu.duke.ece651.team4.TrackerServer.service.UserService;

@RestController
public class CourseController {
    private final SectionService sectionService;
    private final UserService userService;
    private final RosterService rosterService;
    private final CourseService courseService;
    private final AttendanceService attendanceService;

    @Autowired
    public CourseController(SectionService sectionService, UserService userService, RosterService rosterService,
            CourseService courseService, AttendanceService attendanceService) {
        this.sectionService = sectionService;
        this.userService = userService;
        this.rosterService = rosterService;
        this.courseService = courseService;
        this.attendanceService = attendanceService;
    }

    //

    @GetMapping("/studentSections")
    public List<AttendanceStuDto> getStudentSections(Authentication authentication) {
        String netid = (String) authentication.getPrincipal();
        List<String> sectionids = rosterService.getSectionsOfStudent(netid);
        List<AttendanceStuDto> attendanceDtos = new ArrayList<>();
        for (String secid : sectionids) {
            Section sec = sectionService.getSection(secid);
            AttendanceStuDto attendanceDto = new AttendanceStuDto();
            attendanceDto.setSection_id(sec.getSectionID());
            attendanceDto.setCourse_id(sec.getCourse().getCourseID());
            attendanceDto.setCourse_name(sec.getCourse().getCourseName());

            List<AttendanceRecordStuDto> attendanceRecords = new ArrayList<>();
            //add attendance records
            List<Attendance> attendances = attendanceService.getAttendance(netid,sec.getSectionID());
            for (Attendance att : attendances) {
                AttendanceRecordStuDto record = new AttendanceRecordStuDto();
                record.setDate(InstantToString(att.getAttendanceTime()));
                record.setStatus(att.getStatus());
                attendanceRecords.add(record);
            }
            
            attendanceDto.setAttendance_records(attendanceRecords);
            attendanceDtos.add(attendanceDto);
        }
        return attendanceDtos;
    }

    private String InstantToString(Instant date) {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = date.atZone(zoneId).toLocalDate();
        String dateString = localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        return dateString;
    }

    @GetMapping("/courses")
    public List<CourseSelectionDto> getCourses(Authentication authentication) {
        System.out.println("Get courses");
        List<CourseSelectionDto> coursesToDisplay = new ArrayList<>();
        List<Section> sections = sectionService.getAllSections();
        System.out.println("Section size: " + sections.size());

        for (Section section : sections) {
            String courseName = section.getCourse().getCourseName();
            String courseID = section.getCourse().getCourseID();
            String sectionID = section.getSectionID();
            String teacherID = section.getTeacher().getNetid();

            CourseSelectionDto existingCourse = coursesToDisplay.stream()
                    .filter(c -> c.getCourse_id().equals(courseID))
                    .findFirst()
                    .orElse(null);

            if (existingCourse == null) {
                CourseSelectionDto newCourse = new CourseSelectionDto();
                newCourse.setCourse_name(courseName);
                newCourse.setCourse_id(courseID);
                List<SectionDto> sectionsDto = new ArrayList<>();

                SectionDto sectionDto = new SectionDto();
                sectionDto.setSection_id(sectionID);
                sectionDto.setProfessor_id(teacherID);
                sectionDto.setAssigned_to_professor(!"null".equals(teacherID));

                sectionsDto.add(sectionDto);
                newCourse.setSections(sectionsDto);
                coursesToDisplay.add(newCourse);
            } else {
                SectionDto sectionDto = new SectionDto();
                sectionDto.setSection_id(sectionID);
                sectionDto.setProfessor_id(teacherID);
                sectionDto.setAssigned_to_professor(!"null".equals(teacherID));
                existingCourse.getSections().add(sectionDto);
            }
        }
        return coursesToDisplay;
    }

    @PostMapping("/teachCourse")
    public ResponseEntity<String> chooseCourse(@RequestBody TeachCourseDto request) {
        System.out.println("sectionId: " + request.getSection_id()+" netid: "+request.getNetid());

        Section section = sectionService.getSection(request.getSection_id());
        if (section == null) {
            System.out.println("Section not found.");
            return ResponseEntity.badRequest().body("Section not found.");
        }
        // if(section.getTeacher().getNetid() != "null"){
        //     System.out.println("Section already has a teacher.");
        //     return ResponseEntity.badRequest().body("Section already has a teacher.");
        // }
        User teacher = userService.getUser(request.getNetid());
        System.out.println("Teacher: " + teacher.getNetid());
        section.setTeacher(teacher);
        sectionService.updateSection(section);
        System.out.println("Course chosen successfully");
        return ResponseEntity.ok("Course chosen successfully");
    }

    // @GetMapping("/courses/teacher")
    // public List<CourseSelectionDto> getCourseOfProfessor(Authentication
    // authentication) {
    // String netid = (String) authentication.getPrincipal();
    // User user = userService.getUser(netid);
    // List<CourseSelectionDto> courseToDisplay = new ArrayList<>();
    // List<Section> sections = sectionService.getAllSectionsTea(user.getNetid());
    // for (Section section : sections) {
    // CourseSelectionDto currCourse = new CourseSelectionDto();
    // currCourse.setCourseName(section.getCourse().getCourseName());
    // currCourse.setCourseID(section.getCourse().getCourseID());
    // currCourse.setSectionID(section.getSectionID());
    // currCourse.setIsSelected(true);
    // courseToDisplay.add(currCourse);
    // }
    // return courseToDisplay;
    // }

    // @PostMapping("/courses")
    // public void selectCourse(@RequestParam String sectionID, @RequestParam String
    // teacherID) {
    // sectionService.addTeacherToSection(sectionID, teacherID);
    // }

    // //This is for student
    // @GetMapping("/courses/student")
    // public List<CourseDisplayDto> getCourseOfStudent(Authentication
    // authentication) {
    // UserDetails userDetail = (UserDetails) authentication.getPrincipal();
    // User user = userService.getUser(userDetail.getUsername());
    // List<CourseDisplayDto> courseToDisplay = new ArrayList<>();
    // List<String> sections = rosterService.getSectionsOfStudent(user.getNetid());
    // for (String sectionID : sections) {
    // Section section = sectionService.getSection(sectionID);
    // String teacherName = section.getTeacher().getFirstName() + " " +
    // section.getTeacher().getLastName();
    // CourseDisplayDto currCourse = new CourseDisplayDto();
    // currCourse.setCourseName(section.getCourse().getCourseName());
    // currCourse.setTeacherName(teacherName);
    // currCourse.setSectionID(section.getSectionID());
    // courseToDisplay.add(currCourse);
    // }
    // return courseToDisplay;
    // }
}
