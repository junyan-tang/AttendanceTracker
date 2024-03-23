package edu.duke.ece651.team4.server;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.IOException;
import java.io.EOFException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class InputFilter {
  private final BufferedReader inputReader;
  private final PrintStream out;

  public InputFilter(BufferedReader inputReader, PrintStream out) {
    this.inputReader = inputReader;
    this.out = out;
  }

  public List<List<String>> readCSVFile() throws IOException {
    List<List<String>> studentRec = new ArrayList<>();
    String line = "";
    while ((line = inputReader.readLine()) != null) {
      String[] elements = line.split(",");
      studentRec.add(Arrays.asList(elements));
    }
    return studentRec;
  }
  
  public List<String> getFirstLine(List<List<String>> studentRec) {
    if (studentRec.size() == 0) {
      return null;
    }
    return studentRec.get(0);
  }

  public String readUserInput(String prompt) throws IOException {
    out.println(prompt);
    String userIn = inputReader.readLine();
    if (userIn == null) {
      throw new EOFException("No more input to read.");
    }
    return userIn;
  }

  public boolean checkHeaderExist(String prompt) throws IOException {
    boolean validInput = false;
    boolean res = true;
    while (!validInput) {
      String s = readUserInput(prompt);
      if (s.equals("Y") || s.equals("y")) {
        validInput = true;
        res = true;
      }
      else if (s.equals("N") || s.equals("n")) {
        validInput = true;
        res = false;
      }
      else {
        continue;
      }
    }
    return res;
  }
  
  public List<List<String>> formatHeader(List<List<String>> studentRec) throws IOException {
    int colNum = studentRec.get(0).size();
    String headerPrompt = "Does this CSV file has header? Enter Y for yes and N for no.";
    boolean headerExist = checkHeaderExist(headerPrompt);
    if (!headerExist) {
      List<String> newHeader = new ArrayList<>(Collections.nCopies (colNum, null));
      studentRec.add(0, newHeader);
    }
    List<String> firstLine = studentRec.get(0);
    List<String> data = studentRec.get(1);
    String inputPrompt = "Choose which column '" + data + "' belongs to. Enter N for name, E for email and P for phone number.";
    for (int i = 0; i < colNum; i++) {
      boolean validInput = false;
      while (!validInput) {
        String s = readUserInput(inputPrompt);
        if (s.equals("N") || s.equals("n")) {
          firstLine.set(i, "Name");
          validInput = true;
        }
        else if (s.equals("E") || s.equals("e")) {
          firstLine.set(i, "Email");
          validInput = true;
        }
        else if (s.equals("P") || s.equals("p")) {
          firstLine.set(i, "PhoneNumber");
          validInput = true;
        }
        else {
          continue;
        }
      }
    }
    return studentRec;
  }

  public List<User> createStudentList(List<List<String>> studentRec) {
    List<User> studentList = new ArrayList<>();
    List<String> header = studentRec.get(0);
    int[] colOrder = new int[header.size()];
    for(int i = 0; i < header.size(); i++) {
      if (header.get(i).equals("PhoneNumber")) {
        colOrder[0] = i;
      }
      else if (header.get(i).equals("Name")) {
        colOrder[1] = i;
      }
      else {
        colOrder[2] = i;
      }
    }
    for (int i = 1; i < studentRec.size(); i++) {
      List<String> rec = studentRec.get(i);
      User stu = new Student(rec.get(colOrder[0]),rec.get(colOrder[1]), rec.get(colOrder[2]));
      studentList.add(stu);
    }
    return studentList;
  }
}
