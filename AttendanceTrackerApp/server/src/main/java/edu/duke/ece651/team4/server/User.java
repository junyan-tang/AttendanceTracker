package edu.duke.ece651.team4.server;

public interface User {
  public Boolean updateStatus(String message, MessageSender messageSender) throws Exception;
  public String getName();
  public Boolean changeName(String newName);
  
}
