package edu.duke.ece651.team4.TrackerServer.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.duke.ece651.team4.TrackerServer.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, String>{
    @Query("select c from Course c where c.courseName = :courseName")
    Course findByCourseName(@Param("courseName") String courseName);
}
