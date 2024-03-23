package edu.duke.ece651.team4.server;

import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.StringReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.EOFException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class InputFilterTest {
  String[] line1 = new String[] { "Name", "Email", "Phone Number" };
  String[] line2 = new String[] { "Mary Sue", "ms123@duke.edu", "9197328384" };
  String[] line3 = new String[] { "Taylor Brown", "tb67@duke.edu", "9198264409" };
  String[] line4 = new String[] { "Hao Zhang", "hz506@duke.edu", "9192283827" };
  List<String> list1 = Arrays.asList(line1);
  List<String> list2 = Arrays.asList(line2);
  List<String> list3 = Arrays.asList(line3);
  List<String> list4 = Arrays.asList(line4);

  public InputFilter readFileHelper(String file, PrintStream ps) {
    InputStream is = InputFilter.class.getResourceAsStream(file);
    BufferedReader inputReader = new BufferedReader(new InputStreamReader(is));
    InputFilter ft = new InputFilter(inputReader, ps);
    return ft;
  }

  public InputFilter readUserInputHelper(String userIn, PrintStream ps) {
    InputStream is = InputFilter.class.getResourceAsStream(userIn);
    BufferedReader inputReader = new BufferedReader(new StringReader(userIn));
    InputFilter ft = new InputFilter(inputReader, ps);
    return ft;
  }

  @Test
  public void test_readCSVFile() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes, true);
    String file = "/roster2.csv";
    InputFilter ft = readFileHelper(file, out);
    List<List<String>> rec = ft.readCSVFile();
    List<List<String>> expectedRec = new ArrayList<>();
    expectedRec.add(list1);
    expectedRec.add(list2);
    expectedRec.add(list3);
    expectedRec.add(list4);
    assertEquals(expectedRec, rec);
  }

  @Test
  public void test_getFirstLine() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes, true);
    String file1 = "/roster1.csv";
    InputFilter ft1 = readFileHelper(file1, out);
    List<List<String>> rec = new ArrayList<>();
    List<String> fl1 = ft1.getFirstLine(rec);
    assertEquals(null, fl1);
    String file2 = "/roster1.csv";
    rec.add(list1);
    InputFilter ft2 = readFileHelper(file2, out);
    List<String> fl2 = ft2.getFirstLine(rec);
    String[] expLine = new String[] { "Name", "Email", "Phone Number" };
    List<String> expected = Arrays.asList(expLine);
    assertEquals(expected, fl2);
  }

  @Test
  public void test_readUserInput_EOFException() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes, true);
    String inputStr = "";
    InputFilter ft = readUserInputHelper(inputStr, out);
    String prompt = "Enter Y for yes.";
    assertThrows(EOFException.class, () -> ft.readUserInput(prompt));
  }

  @Test
  public void test_readUserInput() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes, true);
    String inputStr = "Y";
    InputFilter ft = readUserInputHelper(inputStr, out);
    String prompt = "Enter Y for yes.";
    String userInput = ft.readUserInput(prompt);
    assertEquals("Y", userInput);
  }
}
