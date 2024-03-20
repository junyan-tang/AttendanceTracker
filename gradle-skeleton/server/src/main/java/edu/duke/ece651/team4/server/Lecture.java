package edu.duke.ece651.team4.server;

import edu.duke.ece651.team4.server.Course;
import edu.duke.ece651.team4.server.User;
import edu.duke.ece651.team4.server.Attendance;
import edu.duke.ece651.team4.server.MessageSender;
import java.util.LinkedHashMap;

public class Lecture implements Course{
  private int courseId;
  private String courseName;
  private LinkedHashMap<String, User> studentMap;
  private LinkedHashMap<String, User> professorMap;
  private LinkedHashMap<String, Attendance> attendanceMap;
  private MessageSender messageSender;

  

}
