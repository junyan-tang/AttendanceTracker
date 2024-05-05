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

  public String getNetid() {
    return netid;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getPreferredName() {
    return preferredName;
  }

  public String getPassword() {
    return password;
  }

  public String getIdentity() {
    return identity;
  }

  public String getEmail() {
    return email;
  }

  public void setNetid(String netid) {
    this.netid = netid;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setPreferredName(String preferredName) {
    this.preferredName = preferredName;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setIdentity(String identity) {
    this.identity = identity;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
