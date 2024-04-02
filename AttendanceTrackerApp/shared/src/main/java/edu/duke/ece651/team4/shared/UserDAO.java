package edu.duke.ece651.team4.shared;
import edu.duke.ece651.team4.UserAdminApp.User;

/**
 * Interface for User Account Creation and Management
 */
public interface UserDAO {
    public void createUser(User user);
    public void deleteUser(String netid);
    public void updateUser(User user);
    public User getUser(String netid);

}
