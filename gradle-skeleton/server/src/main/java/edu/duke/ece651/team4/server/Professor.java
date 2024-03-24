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

  public String getName() {
    return name;
  }

  public String getPhoneNum() {
    return phoneNum;
  }

  public String getEmail() {
    return email;
  }

  public Boolean changeName(String newName){
    this.name = newName;
    return true;
  }

  public Boolean updateStatus(String message, MessageSender messageSender) throws Exception{
      return messageSender.sendMessage(this.email, message);
  }

}
