package edu.duke.ece651.team4.UserAdminApp;

import edu.duke.ece651.team4.shared.*;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserOperationTest {
  UserManager userManager = new UserOperation();
  User userStu1 = new Student("yx123", "Yuhan", "Xue", "YuhanX", "$12@8976", "student", "yuhan@duke.edu");
  User userStu2 = new Student("yx123", "Yuhan", "Xue", "YuhanX", "$12@8976352", "student", "yuhan@duke.edu");
  User userProf = new Student("wy45", "Wang", "Yu", "Daniel", "$145@8222", "professor", "wang.yu@duke.edu");
  User userAdm = new Student("xz87", "Xinyi", "Zhang", "Amy", "$0987@8we6", "admin", "xinyi.zhang@duke.edu");

  @Test
  public void test_userOperation() {
    Connection conn = DatabaseConnectionUtil.getConnection();
    DatabaseConnectionUtil.dropTables(conn);
    DatabaseConnectionUtil.initDatabase(conn);
    boolean userAdd = userManager.addUser(userStu1);
    assertTrue(userAdd);
    boolean userUpdate = userManager.updateUser(userStu2);
    assertTrue(userUpdate);
    boolean removeUpdate = userManager.removeUser(userStu2);
    assertTrue(removeUpdate);
  }
}
