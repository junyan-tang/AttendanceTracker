package edu.duke.ece651.team4.UserAdminApp;

import edu.duke.ece651.team4.shared.*;

import java.io.PrintStream;
import java.io.IOException;

public class UserAdmin {
  private String permission;
  private InputManager inputManager;
  private UserManager userManager;
  private PrintStream out;

  public UserAdmin(InputManager inputManager, PrintStream out) {
    permission = "";
    this.out = out;
    this.inputManager = inputManager;
    this.userManager = new UserOperation();
  }

  public void setPermission(String status) {
    permission = status;
  }
  public User getUser(String netid) {
    User user = userManager.getUser(netid);
    if (user == null) {
      return null;
    }
    return user;
  }

  public boolean addAdminUser(String netid, String password) {
    User newAdmin = new Admin(netid, password, "admin");
    userManager.addUser(newAdmin);
    out.println("Admin register successfully.");
    return true;
  }

  public boolean addUser(String identity) throws IOException {
    out.println("You have chosen to add a new " + identity + ".");
    String promptNetid = "Enter the " + identity + "'s netid.";
    String netid = inputManager.readInput(promptNetid);
    // check if the user already exists
    if (userManager.getUser(netid) != null) {
      out.println("The user already exists.");
      return false;
    }
    String promptFirstname = "Enter the " + identity + "'s firstname.";
    String firstname = inputManager.readInput(promptFirstname);
    String promptLastname = "Enter the " + identity + "'s lastname.";
    String lastname = inputManager.readInput(promptLastname);
    String promptPreferredName = "Enter the " + identity + "'s preferred name.";
    String preferredName = inputManager.readInput(promptPreferredName);
    String promptPassword = "Enter the " + identity + "'s password.";
    String rawPassword = inputManager.readInput(promptPassword);
    String hashedPassword = LoginManager.hashPassword(rawPassword);
    String promptEmail = "Enter the " + identity + "'s email.";
    String email = inputManager.readInput(promptEmail);
    User newUser;
    if (identity.equals("professor")) {
      newUser = new Professor(netid, firstname, lastname, preferredName, hashedPassword, identity, email);
    }
    else {
      newUser = new Student(netid, firstname, lastname, preferredName, hashedPassword, identity, email);
    }
    userManager.addUser(newUser);
    out.println("The user is added successfully.");
    return true;
  }

  public boolean removeUser() throws IOException {
    out.println("You have chosen to remove a user.");
    String promptNetid = "Enter the netid of the user you want to remove.";
    String netid = inputManager.readInput(promptNetid);
    User curUser = userManager.getUser(netid);
    if (curUser == null) {
      out.println("The user did not exist.");
      return false;
    }
    userManager.removeUser(curUser);
    out.println("The user is removed successfully.");
    return true;
  }

  public boolean updateUser() throws IOException {
    out.println("You have chosen to update the user information.");
    String promptNetid = "Enter the netid of the user you want to update information.";
    String netid = inputManager.readInput(promptNetid);
    User curUser = userManager.getUser(netid);
    if (curUser == null) {
      out.println("The user did not exist.");
      return false;
    }
    out.println("Here is your information:");
    out.println("Firstname: " + curUser.firstName);
    out.println("Lastname: " + curUser.lastName);
    out.println("Preferred Name: " + curUser.preferredName);
    out.println("Email: " + curUser.email);
    out.println("Password: ******");
    boolean validUpdate = false;
    boolean noPermission = false;
    String promptUpdate = "Choose which information you want to update.\n" + "F for firstname, L for lastname, N for preferred name, E for email and P for password.";
    while (!validUpdate) {
      String updateInfo = inputManager.readInput(promptUpdate).toUpperCase();
      if (updateInfo == "F") {
        String promptFname = "Enter your new firstname.";
        String updateFname = inputManager.readInput(promptFname);
        curUser.firstName = updateFname;
        validUpdate = true;
      }
      else if (updateInfo == "L") {
        String promptLname = "Enter your new lastname.";
        String updateLname = inputManager.readInput(promptLname);
        curUser.lastName = updateLname;
        validUpdate = true;
      }
      else if (updateInfo == "N") {
        if (permission.equals("true")) {
          String promptPname = "Enter your new preferred name.";
          String updatePname = inputManager.readInput(promptPname);
          curUser.preferredName = updatePname;
          validUpdate = true;
        }
        else {
          out.println("You can't edit your preferred name!");
          noPermission = true;
          break;
        }
      }
      else if (updateInfo == "E") {
        String promptEmail = "Enter your new email.";
        String updateEmail = inputManager.readInput(promptEmail);
        curUser.email = updateEmail;
        validUpdate = true;
      }
      else if (updateInfo == "P") {
        String promptPassword = "Enter your new password.";
        String updatePassword = inputManager.readInput(promptPassword);
        String updateHash = LoginManager.hashPassword(updatePassword);
        curUser.password = updateHash;
        validUpdate = true;
      }
      else {
        continue;
      }
    }
    userManager.updateUser(curUser);
    if (!noPermission) {
      out.println("The user information is updated successfully.");
    }
    return true;
  }
}
