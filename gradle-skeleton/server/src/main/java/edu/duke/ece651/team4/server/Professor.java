package edu.duke.ece651.team4.server;

public class Professor implements User {
  private String phoneNum;
  private String name;
  private String email;

  public Professor(String phoneNum, String name, String email) {
    this.phoneNum = phoneNum;
    this.name = name;
    this.email = email;
  }
}
