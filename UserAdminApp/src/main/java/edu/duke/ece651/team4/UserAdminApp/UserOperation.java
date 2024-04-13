package edu.duke.ece651.team4.UserAdminApp;

import edu.duke.ece651.team4.shared.*;

import java.util.List;

public class UserOperation implements UserManager {
  private UserDAO helper;

  public UserOperation() {
    helper = new UserDAOImpl();
  }

  @Override
  public User getUser(String netid) {
    User curUser = helper.getUser(netid);
    if (curUser == null) {
      return null;
    }
    return curUser;
  }

  @Override
  public boolean addUser(User user) {
    if (getUser(user.netid) != null) {
      return false;
    }
    helper.createUser(user);
    return true;
  }

  @Override
  public boolean removeUser(User user) {
    if (getUser(user.netid) == null) {
      return false;
    }
    helper.deleteUser(user.netid);
    return true;
  }

  @Override
  public boolean updateUser(User user) {
    if (getUser(user.netid) == null) {
      return false;
    }
    helper.updateUser(user);
    return true;
  }

  @Override
  public List<User> getAllStudent() {
    return helper.getAllStudent();
  }

  @Override
  public List<User> getAllProfessor() {
    return helper.getAllProfessor();
  }
}
