package edu.duke.ece651.team4.TrackerServer.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.duke.ece651.team4.TrackerServer.entity.Attendance;
import edu.duke.ece651.team4.TrackerServer.service.AttendanceScore;
import edu.duke.ece651.team4.TrackerServer.service.StudentScore;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long>{
    @Query("select a from Attendance a where a.student.netid = :studentID and a.section.sectionID = :sectionID and a.attendanceTime = :date")
    Optional<Attendance> findByStudentIdAndSectionIdAndDate(String studentID, String sectionID, Instant date);
    
    @Query("select a from Attendance a where a.student.netid = :studentID and a.section.sectionID = :sectionID")
    List<Attendance> findByStudentIdAndSectionId(String studentID, String sectionID);

    @Query("select distinct a.attendanceTime from Attendance a where a.section.sectionID = :sectionID")
    List<Instant> findDatesBySectionId(String sectionID);

    @Query("select a from Attendance a where a.section.sectionID = :sectionID and a.attendanceTime = :date")
    List<Attendance> findBySectionIdAndDate(String sectionID, Instant date);

    @Query("select a from Attendance a where a.section.sectionID = :sectionID")
    List<Attendance> findBySectionId(String sectionID);

    @Query("select a from Attendance a where a.student.netid = :studentID")
    List<Attendance> findByStudentId(String studentID);

    @Query("SELECT new edu.duke.ece651.team4.TrackerServer.service.AttendanceScore(a.section.sectionID as sectionId, u.netid as netid, u.firstName as firstname, u.lastName as lastname, SUM(CASE WHEN a.status = 'PRESENT' THEN 1 WHEN a.status = 'TARDY' THEN 0.8 ELSE 0 END) as score) FROM Attendance a JOIN a.student u WHERE u.netid = :studentId GROUP BY a.section.sectionID, u.netid, u.firstName, u.lastName")
    List<AttendanceScore> findAttendanceScoresByStudentId(@Param("studentId") String studentId);

    @Query("SELECT new edu.duke.ece651.team4.TrackerServer.service.StudentScore(a.student.netid, SUM(CASE WHEN a.status = 'PRESENT' THEN 1 WHEN a.status = 'TARDY' THEN 0.8 ELSE 0 END)) FROM Attendance a WHERE a.section.sectionID = :sectionID GROUP BY a.student.netid")
    List<StudentScore> findScoresBySection(@Param("sectionID") String sectionID);


    // List<Attendance> findByStudentIDAndSectionIDAndDate(String studentID, String sectionID, Instant date);
    
}
