package edu.duke.ece651.team4.TrackerServer.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import edu.duke.ece651.team4.TrackerServer.entity.Roster;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface RosterRepository extends JpaRepository<Roster, Long>{
    @Query("select r from Roster r where r.student.netid = :studentID and r.section.sectionID = :sectionID")
    Optional<Roster> findByStudentIdAndSectionId(@Param("studentID") String studentID, @Param("sectionID") String sectionID);

    @Query("select r from Roster r where r.section.sectionID = :sectionID")
    List<Roster> findBySectionId(@Param("sectionID") String sectionID);

    @Query("select r from Roster r where r.student.netid = :studentID")
    List<Roster> findByStudentId(@Param("studentID") String studentID);

    @Query("select r.section.sectionID from Roster r where r.student.netid = :studentID")
    List<String> findSectionIdByStudentId(@Param("studentID") String studentID);
}
