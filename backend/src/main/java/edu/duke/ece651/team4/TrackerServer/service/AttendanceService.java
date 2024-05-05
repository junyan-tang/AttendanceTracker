package edu.duke.ece651.team4.TrackerServer.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.duke.ece651.team4.TrackerServer.entity.Attendance;
import edu.duke.ece651.team4.TrackerServer.entity.Course;
import edu.duke.ece651.team4.TrackerServer.repository.AttendanceRepository;
import edu.duke.ece651.team4.TrackerServer.repository.CourseRepository;
import edu.duke.ece651.team4.TrackerServer.repository.SectionRepository;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final CourseRepository courseRepository;
    private final SectionRepository sectionRepository;

    @Autowired
    public AttendanceService(AttendanceRepository attendanceRepository, CourseRepository courseRepository, SectionRepository sectionRepository) {
        this.attendanceRepository = attendanceRepository;
        this.courseRepository = courseRepository;
        this.sectionRepository = sectionRepository;
    }

    public Attendance getAttendance(String studentID, String sectionID, Instant date){
        return attendanceRepository.findByStudentIdAndSectionIdAndDate(studentID, sectionID, date).orElse(null);
    }

    public void addAttendance(Attendance a) {
        attendanceRepository.save(a);
    }

    public void updateAttendance(Attendance a) {
        attendanceRepository.save(a);
    }

    public void deleteAttendance(String studentID, String sectionID, Instant date){
        Attendance attendance = attendanceRepository.findByStudentIdAndSectionIdAndDate(studentID, sectionID, date).orElse(null);
        attendanceRepository.delete(attendance);
    }

    public List<Attendance> getAttendance(String studentID, String sectionID) {
        return attendanceRepository.findByStudentIdAndSectionId(studentID, sectionID);
    }

    public List<Attendance> getAttendanceRecord(String sectionID, String date) {
        Instant instant = getDateFromString(date);
        return attendanceRepository.findBySectionIdAndDate(sectionID, instant);
    }

    public List<Attendance> getAttendanceRecord2(String sectionID, Instant date) {
        return attendanceRepository.findBySectionIdAndDate(sectionID, date);
    }

    public List<Attendance> getSectionAttendance(String sectionID) {
        return attendanceRepository.findBySectionId(sectionID);
    }

    public String getAttendanceStuScore(String studentID){
        List<AttendanceScore> scores = attendanceRepository.findAttendanceScoresByStudentId(studentID);
        StringBuilder result = new StringBuilder();
        for (AttendanceScore score : scores) {
            Course course = (sectionRepository.findBySectionID(score.getSectionId())).getCourse();
            if (course != null) {
                result.append("Course: ").append(course.getCourseName()).append("   ");
            }
            result.append("SectionID: ").append(score.getSectionId()).append("   ");
            result.append("Score: ").append(score.getScore()).append("\n");
        }
        return result.toString();
    }

    public String getAttendanceProScore(String sectionID) {
        List<StudentScore> scores = attendanceRepository.findScoresBySection(sectionID);
        StringBuilder result = new StringBuilder();
        for (StudentScore score : scores) {
            result.append("StudentID: ").append(score.getStudentID()).append("   ");
            result.append("Score: ").append(score.getScore()).append("\n");
        }
        return result.toString();
    }

    // public List<String> getAttendanceDates(String sectionID) {
    //     List<Instant> dates = attendanceRepository.findDatesBySectionId(sectionID);
    //     List<String> dateStrings = new ArrayList<>();
    //     for (Instant date: dates) {
    //         // To be implemented
    //     }
    //     return attendanceRepository.findDatesBySectionId(sectionID);
    // }
    // public List<Attendance> getAttendanceListStu(String studentID) {
    //     return null;
    // }

    // public List<Attendance> getAttendanceListSec(String sectionID) {
    //     return null;
    // }

    // public List<Attendance> getAttendanceListSecDate(String sectionID, Instant date) {
    //     return null;
    // }

    // public List<Attendance> getAttendanceListSecDateSta(String sectionID, Instant date, String status) {
    //     return null;
    // }

    // public List<String> getAttendanceDatelist(String sectionID) {
    //     return null;
    // }

    // public List<String> getAttendanceStulist(String sectionID, Instant date){
    //     return null;
    // }

    // public String getAttendanceStuScoreInSec(String sectionID) {
    //     return null;
    // }

    // public String getAttendanceStuScore(String studentID) {
    //     return null;
    // }

    public Instant getDateFromString(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate localDate = LocalDate.parse(date, formatter);


        ZoneId zoneId = ZoneId.systemDefault(); 
        Instant instant = localDate.atStartOfDay(zoneId).toInstant();
        return instant;
    }
}
