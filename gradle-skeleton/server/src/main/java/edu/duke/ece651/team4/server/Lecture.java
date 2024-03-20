package edu.duke.ece651.team4.server;

import edu.duke.ece651.team4.server.Course;
import java.util.LinkedHashMap;

public class Lecture implements Course{
  private int courseId;
  private String courseName;
  private LinkedHashMap<String, User> studentMap;
  private LinkedHashMap<String, User> professorMap;
  

}
