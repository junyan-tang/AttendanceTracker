package edu.duke.ece651.team4.UserAdminApp;

import java.io.IOException;

public interface InputManager {
  public String readInput(String prompt) throws IOException;
  public String readFile() throws IOException;
}
