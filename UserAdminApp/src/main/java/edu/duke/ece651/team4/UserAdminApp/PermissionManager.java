package edu.duke.ece651.team4.UserAdminApp;

import edu.duke.ece651.team4.shared.*;

public class PermissionManager {
  PermissionDAO helper = new PermissionDAOImpl();
  public boolean checkTableExist() {
    if (!helper.checkTableExist()) {
      return false;
    }
    return true;
  }

  public void createTable(String status) {
    helper.createTable(status);
  }

  public String getStatus() {
    return helper.getPermissionStatus();
  }
}
