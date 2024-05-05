package edu.duke.ece651.team4.TrackerServer.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.duke.ece651.team4.TrackerServer.dto.AttendanceDto;
import edu.duke.ece651.team4.TrackerServer.dto.AttendanceModifyDto;
import edu.duke.ece651.team4.TrackerServer.dto.AttendanceRecordDto;
import edu.duke.ece651.team4.TrackerServer.dto.StudentDto;
import edu.duke.ece651.team4.TrackerServer.entity.Attendance;
import edu.duke.ece651.team4.TrackerServer.entity.Section;
import edu.duke.ece651.team4.TrackerServer.service.AttendanceService;
import edu.duke.ece651.team4.TrackerServer.service.SectionService;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;
    private final SectionService sectionService;

    @Autowired
    public AttendanceController(AttendanceService attendanceService, SectionService sectionService) {
        this.attendanceService = attendanceService;
        this.sectionService = sectionService;
    }

    // This is for professor this url might be different
    @GetMapping()
    public List<AttendanceDto> getAttendanceAll(Authentication authentication) {
        String netid = (String) authentication.getPrincipal();
        List<Section> sections = sectionService.getAllSectionsTea(netid); // get all sections

        List<AttendanceDto> attendanceDtos = new ArrayList<>();

        for (Section sec : sections) {
            HashSet<Instant> dates = new HashSet<>(); // set for all dates
            List<Attendance> attendances = attendanceService.getSectionAttendance(sec.getSectionID());
            for (Attendance att : attendances) {
                dates.add(att.getAttendanceTime());
            } // get all dates for this section

            AttendanceDto attendanceDto = new AttendanceDto();
            attendanceDto.setSection_id(sec.getSectionID());
            attendanceDto.setCourse_id(sec.getCourse().getCourseID());
            attendanceDto.setCourse_name(sec.getCourse().getCourseName());
            List<AttendanceRecordDto> attendanceRecordDtos = new ArrayList<>();

            for (Instant date : dates) {
                List<Attendance> attendancesperDate = attendanceService.getAttendanceRecord2(sec.getSectionID(),
                        date);
                AttendanceRecordDto attendanceRecordDto = new AttendanceRecordDto();
                // attendanceRecordDto.setDate(date.toString());
                attendanceRecordDto.setDate(InstantToString(date));
                List<StudentDto> studentDtos = new ArrayList<>();
                for (Attendance att : attendancesperDate) {
                    StudentDto studentDto = new StudentDto();
                    studentDto.setNetid(att.getStudent().getNetid());
                    studentDto.setFull_name(att.getStudent().getFirstName() + " " + att.getStudent().getLastName());
                    studentDto.setAttendance_status(att.getStatus());
                    studentDtos.add(studentDto);
                }
                attendanceRecordDto.setStudents(studentDtos);
                attendanceRecordDtos.add(attendanceRecordDto);
            }
            attendanceDto.setAttendance_records(attendanceRecordDtos);
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

    // for (Section section : sections) {
    // AttendanceDto attendanceDto = new AttendanceDto();
    // attendanceDto.setSection_id(section.getSectionID());
    // attendanceDto.setCourse_id(section.getCourse().getCourseID());
    // attendanceDto.setCourse_name(section.getCourse().getCourseName());

    // List<AttendanceRecordDto> attendanceRecordDtos = new ArrayList<>();
    // Set<Instant> dates = new HashSet<>();

    // String sectionId = section.getSectionID();
    // List<Attendance> attendanceList =
    // attendanceService.getSectionAttendance(sectionId);
    // for (Attendance attendance : attendanceList) {
    // AttendanceRecordDto currRecord = new
    // Instant date = attendance.getAttendanceTime();
    // if (dates.contains(date)) {
    // StudentDto studentDto = new StudentDto();
    // studentDto.setNetid(attendance.getStudent().getNetid());
    // String first_name = attendance.getStudent().getFirstName();
    // String last_name = attendance.getStudent().getLastName();
    // String full_name = first_name + " " + last_name;
    // studentDto.setFull_name(full_name);
    // studentDto.setAttendance_status(attendance.getStatus());
    // for (AttendanceRecordDto )
    // }
    // }
    // }

    // List<String> dates = new ArrayList<>();
    // return null;
    // }

    @PostMapping("/modify")
    public void modifyAttendance(@RequestBody AttendanceModifyDto attendanceModifyDto) {
        Attendance selected = attendanceService.getAttendance(
                attendanceModifyDto.getStudent_id(), attendanceModifyDto.getSection_id(),
                convertDateToInstant(attendanceModifyDto.getDate()));
        selected.setStatus(attendanceModifyDto.getNew_status());
        attendanceService.updateAttendance(selected);
    }

    private Instant convertDateToInstant(String dateString) {
        // Parse the input string to LocalDate
        LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);

        // Convert LocalDate to LocalDateTime at the start of the day (midnight)
        LocalDateTime localDateTime = localDate.atStartOfDay();

        // Convert LocalDateTime to ZonedDateTime using UTC as the time zone
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());

        // Return the Instant representation of the start of the day
        return zonedDateTime.toInstant();
    }

    @GetMapping("/{sectionId}/{date}")
    public AttendanceDto getAttendance(@PathVariable String sectionId, @PathVariable String date) {
        List<Attendance> attendanceList = attendanceService.getAttendanceRecord(sectionId, date);

        Section section = sectionService.getSection(sectionId);
        AttendanceDto attendanceDto = new AttendanceDto();
        attendanceDto.setSection_id(sectionId);
        attendanceDto.setCourse_id(section.getCourse().getCourseID());
        attendanceDto.setCourse_name(section.getCourse().getCourseName());

        List<AttendanceRecordDto> attendanceRecordDtos = new ArrayList<>();
        AttendanceRecordDto attendanceRecordDto = new AttendanceRecordDto();
        attendanceRecordDto.setDate(date);

        for (Attendance attendance : attendanceList) {
            StudentDto studentDto = new StudentDto();
            studentDto.setNetid(attendance.getStudent().getNetid());
            String first_name = attendance.getStudent().getFirstName();
            String last_name = attendance.getStudent().getLastName();
            String full_name = first_name + " " + last_name;
            studentDto.setFull_name(full_name);
            studentDto.setAttendance_status(attendance.getStatus());
            attendanceRecordDto.getStudents().add(studentDto);
        }
        attendanceRecordDtos.add(attendanceRecordDto);
        return attendanceDto;
    }

}
