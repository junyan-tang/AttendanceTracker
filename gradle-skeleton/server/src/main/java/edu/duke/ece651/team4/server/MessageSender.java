package edu.duke.ece651.team4.server;

public interface MessageSender {
  public String generateMessage();
  public boolean sendMessage(String destination, String message);
}
