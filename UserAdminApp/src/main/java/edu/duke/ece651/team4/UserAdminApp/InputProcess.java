package edu.duke.ece651.team4.UserAdminApp;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.EOFException;
import java.io.IOException;

public class InputProcess implements InputManager {
  private final BufferedReader fileReader;
  private final BufferedReader inputReader;
  private final PrintStream out;

  public InputProcess(BufferedReader fileReader, BufferedReader inputReader, PrintStream out) {
    this.fileReader = fileReader;
    this.inputReader = inputReader;
    this.out = out;
  }

  @Override
  public String readInput(String prompt) throws IOException {
    out.println(prompt);
    String input = inputReader.readLine();
    if (input == null) {
      throw new EOFException("No more input to read.");
    }
    return input;
  }

  @Override
  public String readFile() throws IOException {
    String line = fileReader.readLine();
    return line;
  }
}