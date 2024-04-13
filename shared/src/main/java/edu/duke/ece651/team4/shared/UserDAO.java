package edu.duke.ece651.team4.shared;

import java.util.List;

/**
 * Interface for User Account Creation and Management
 */
public interface UserDAO {
    public void createUser(User user);
    public void deleteUser(String netid);
    public void updateUser(User user);
    public User getUser(String netid);
    public int userLogin(String netid, String password);
    public boolean isFaculty(String netid);
    public List<User> getAllProfessor();
    public List<User> getAllStudent();
    public boolean isStudent(String netid);
    public void changeNotificationPreference(String netid, String choice);
    public List<String> getUsernameListByID(List<String> netidList);
    public List<String> getIDListByUsername(List<String> usernameList);
    public String getUsrenameByID(String netid);
    public String getIDByUsername(String username);
}
