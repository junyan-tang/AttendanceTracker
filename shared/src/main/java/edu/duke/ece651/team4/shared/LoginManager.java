package edu.duke.ece651.team4.shared;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginManager {
  public static Boolean checkUserExist(String netid) {
    UserDAO helper = new UserDAOImpl();
    User findUser = helper.getUser(netid);
    if (findUser == null) {
      return false;
    }
    return true;
  }

  public static String hashPassword(String password) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(password.getBytes());
      byte[] digest = md.digest();
      StringBuilder hashedpwd = new StringBuilder();
      for (int i = 0; i < digest.length; i++) {
        hashedpwd.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
      }
      return hashedpwd.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static String logUser(String netID, String password, String type) {
    UserDAO helper = new UserDAOImpl();
    if (!checkUserExist(netID)) {
      return "User does not exist";
    }
    User findUser = helper.getUser(netID);
    String hashed_password = hashPassword(password);
    if (!findUser.identity.equals(type)) {
      return "User is not a " + type;
    }
    if (findUser.password.equals(hashed_password)) {
      return "Login successful";
    }
    return "Password is incorrect";
  }
}
