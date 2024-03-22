package edu.duke.ece651.team4.server;

public class Student implements User {
  private String phoneNum;
  private String legalName;
  private String displayName;
  private String email;

  public Student(String phoneNum, String legalName, String displayName, String email) {
    this.phoneNum = phoneNum;
    this.legalName = legalName;
    this.displayName = displayName;
    this.email = email;
  }

  public Student(String phoneNum, String legalName, String email) {
    this.phoneNum = phoneNum;
    this.legalName = legalName;
    this.displayName = legalName;
    this.email = email;
  }

  public String getName() {
    return displayName;
  }

  public String getPhoneNum() {
    return phoneNum;
  }

  public String getEmail() {
    return email;
  }

  public Boolean changeName(String newName){
    this.displayName = newName;
    return true;
  }

  public Boolean updateStatus(String message){
    return true;
  }
}
