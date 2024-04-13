package edu.duke.ece651.team4.shared;

public abstract class User {
  public String netid;
  public String firstName;
  public String lastName;
  public String preferredName;
  public String password;
  public String identity;
  public String email;

  public User(String netid, String firstName, String lastName, String preferredName, String password, String identity, String email) {
    this.netid = netid;
    this.firstName = firstName;
    this.lastName = lastName;
    this.preferredName = preferredName;
    this.password = password;
    this.identity = identity;
    this.email = email;
  }
}
