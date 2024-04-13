package edu.duke.ece651.team4.shared;

public interface PermissionDAO {
  public boolean checkTableExist();
  public void createTable(String status);
  public String getPermissionStatus();
}
