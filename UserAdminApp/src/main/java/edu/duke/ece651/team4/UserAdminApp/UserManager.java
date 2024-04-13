package edu.duke.ece651.team4.UserAdminApp;

import edu.duke.ece651.team4.shared.*;

import java.util.List;

public interface UserManager {
  public User getUser(String netid);
  public boolean addUser(User user);
  public boolean removeUser(User user);
  public boolean updateUser(User user);
  public List<User> getAllStudent();
  public List<User> getAllProfessor();
}
