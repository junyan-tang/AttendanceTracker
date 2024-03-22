package edu.duke.ece651.team4.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.time.Instant;

public class LectureTest {
  @Test
  void testAddAttendance() {
    Student john = new Student("123", "John", "John", ".com");
    Student jane = new Student("456", "Jane", "Jane", ".com");
    Student jack = new Student("789", "Jack", "Jack", ".com");

    ArrayList<User> studentList = new ArrayList<>();
    studentList.add(john);
    studentList.add(jane);
    studentList.add(jack);

    LinkedHashMap <String, User> studentMap = new LinkedHashMap<>();
    studentMap.put("John", john);
    studentMap.put("Jane", jane);
    studentMap.put("Jack", jack);


    Lecture lecture = new Lecture(1, "CPS", studentMap, null, new LinkedHashMap<Instant, Attendance>(), null);
    Attendance attendance = new Attendance(1, "CPS", studentList);

    lecture.addAttendance(attendance);


  }

  @Test
  void testAddStudent() {

  }

  @Test
  void testGenerateReport() {

  }

  @Test
  void testModifyName() {

  }

  @Test
  void testNotifySingle() {

  }
}
