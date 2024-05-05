package edu.duke.ece651.team4.TrackerServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import edu.duke.ece651.team4.TrackerServer.entity.Course;
import edu.duke.ece651.team4.TrackerServer.repository.CourseRepository;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public void addCourse(Course course){
        courseRepository.save(course);
    }

    public Course getCourseByID(String courseID){
        if (courseRepository.findById(courseID).isEmpty()){
            return null;
        }
        return courseRepository.findById(courseID).get();
    }

    public Course getCourse(String courseName){
        return courseRepository.findByCourseName(courseName);
    }

    public void updateCourse(Course course){
        courseRepository.save(course);
    }

    public void deleteCourse(String courseName){
        Course course = courseRepository.findByCourseName(courseName);
        courseRepository.delete(course);
    }

    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }
}
