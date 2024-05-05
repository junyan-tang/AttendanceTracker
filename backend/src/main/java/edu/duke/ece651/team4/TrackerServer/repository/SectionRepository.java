package edu.duke.ece651.team4.TrackerServer.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

import edu.duke.ece651.team4.TrackerServer.entity.Section;

@Repository
public interface SectionRepository extends JpaRepository<Section, String>{
    @Query("select s from Section s where s.course.courseID = :courseID")
    List<Section> findByCourseID(@Param("courseID") String courseID);

    @Query("select s from Section s where s.teacher.netid = :teacherID")
    List<Section> findByTeacherID(@Param("teacherID") String teacherID);

    @Query("select s from Section s where s.sectionID = :sectionID")
    Section findBySectionID(@Param("sectionID") String sectionID);

}
