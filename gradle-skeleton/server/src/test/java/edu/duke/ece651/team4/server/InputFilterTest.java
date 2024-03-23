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

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


public class InputFilterTest {
  String[] line1 = new String[] { "Name", "Email", "Phone Number" };
  String[] line2 = new String[] { "Mary Sue", "ms123@duke.edu", "9197328384" };
  String[] line3 = new String[] { "Taylor Brown", "tb67@duke.edu", "9198264409" };
  String[] line4 = new String[] { "Hao Zhang", "hz506@duke.edu", "9192283827" };
  String[] line5 = new String[] { "name", "email", "mobile" };
  String[] line6 = new String[] { "tb67@duke.edu", "Taylor Brown", "9198264409" };
  String[] line7 = new String[] { "hz506@duke.edu", "Hao Zhang", "9192283827" };
  String[] unorderedFormalArr = new String[] { "Email", "Name", "PhoneNumber" };
  List<String> list1 = Arrays.asList(line1);
  List<String> list2 = Arrays.asList(line2);
  List<String> list3 = Arrays.asList(line3);
  List<String> list4 = Arrays.asList(line4);
  List<String> list5 = Arrays.asList(line5);
  List<String> list6 = Arrays.asList(line6);
  List<String> list7 = Arrays.asList(line7);
  List<String> unorderedFormalHeader = Arrays.asList(unorderedFormalArr);

  public InputFilter readInputHelper(String file, String userIn, PrintStream ps) {
    InputStream is = InputFilter.class.getResourceAsStream(file);
    BufferedReader fileReader = new BufferedReader(new InputStreamReader(is));
    BufferedReader inputReader = new BufferedReader(new StringReader(userIn));
    InputFilter ft = new InputFilter(fileReader, inputReader, ps);
    return ft;
  }

  @Disabled
  @Test
  public void test_readCSVFile() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes, true);
    String file = "/roster2.csv";
    InputFilter ft = readInputHelper(file, "", out);
    List<List<String>> rec = ft.readCSVFile();
    List<List<String>> expectedRec = new ArrayList<>();
    expectedRec.add(list1);
    expectedRec.add(list2);
    expectedRec.add(list3);
    expectedRec.add(list4);
    assertEquals(expectedRec, rec);
  }

  @Disabled
  @Test
  public void test_getFirstLine() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes, true);
    String file1 = "/roster1.csv";
    InputFilter ft1 = readInputHelper(file1, "", out);
    List<List<String>> rec = new ArrayList<>();
    List<String> fl1 = ft1.getFirstLine(rec);
    assertEquals(null, fl1);
    String file2 = "/roster1.csv";
    rec.add(list1);
    InputFilter ft2 = readInputHelper(file2, "", out);
    List<String> fl2 = ft2.getFirstLine(rec);
    String[] expLine = new String[] { "Name", "Email", "Phone Number" };
    List<String> expected = Arrays.asList(expLine);
    assertEquals(expected, fl2);
  }

  @Disabled
  @Test
  public void test_readUserInput_EOFException() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes, true);
    String inputStr = "";
    InputFilter ft = readInputHelper("", inputStr, out);
    String prompt = "Enter Y for yes.";
    assertThrows(EOFException.class, () -> ft.readUserInput(prompt));
  }

  @Disabled
  @Test
  public void test_readUserInput() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes, true);
    String inputStr = "Y";
    InputFilter ft = readInputHelper("", inputStr, out);
    String prompt = "Enter Y for yes.";
    String userInput = ft.readUserInput(prompt);
    assertEquals("Y", userInput);
  }

  @Disabled
  @Test
  public void test_checkHeaderExist() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes, true);
    String inputStr1 = "Y";
    InputFilter ft1 = readInputHelper("", inputStr1, out);
    String prompt = "Enter Y for yes.";
    assertTrue(ft1.checkHeaderExist(prompt));
    String inputStr2 = "N";
    InputFilter ft2 = readInputHelper("", inputStr2, out);
    assertFalse(ft2.checkHeaderExist(prompt));
    String inputStr3 = "T\nS\nY\n";
    InputFilter ft3 = readInputHelper("", inputStr3, out);
    assertTrue(ft3.checkHeaderExist(prompt));
    String inputStr4 = "T\nS\nN\n";
    InputFilter ft4 = readInputHelper("", inputStr4, out);
    assertFalse(ft4.checkHeaderExist(prompt));
  }

  @Disabled
  @Test
  public void test_formatHeader() throws IOException {
    String[] formalArr = new String[] { "Name", "Email", "PhoneNumber" };
    List<String> formalHeader = Arrays.asList(formalArr);
    List<List<String>> expected1 = new ArrayList<>();
    expected1.add(formalHeader);
    expected1.add(list2);
    expected1.add(list3);
    expected1.add(list4);

    List<List<String>> recWithHeader = new ArrayList<>();
    recWithHeader.add(list5);
    recWithHeader.add(list2);
    recWithHeader.add(list3);
    recWithHeader.add(list4);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes, true);
    String inputStr1 = "Y\nN\nE\nP\n";
    InputFilter ft1 = readInputHelper("", inputStr1, out);
    List<List<String>> formatRec1 = ft1.formatHeader(recWithHeader);
    assertEquals(expected1, formatRec1);

    List<List<String>> recWithoutHeader = new ArrayList<>();
    recWithoutHeader.add(list2);
    recWithoutHeader.add(list3);
    recWithoutHeader.add(list4);
    String inputStr2 = "N\nN\nE\nP\n";
    InputFilter ft2 = readInputHelper("", inputStr2, out);
    List<List<String>> formatRec2 = ft2.formatHeader(recWithoutHeader);
    assertEquals(expected1, formatRec2);

    List<List<String>> unorderedRec = new ArrayList<>();
    unorderedRec.add(list6);
    unorderedRec.add(list7);
    String inputStr3 = "N\nE\nN\nP\n";
    InputFilter ft3 = readInputHelper("", inputStr3, out);
    List<List<String>> formatRec3 = ft3.formatHeader(unorderedRec);
    List<List<String>> expected2 = new ArrayList<>();
    expected2.add(unorderedFormalHeader);
    expected2.add(list6);
    expected2.add(list7);
    assertEquals(expected2, formatRec3);
  }

  @Disabled
  @Test
  public void test_createStudentList() throws IOException {
    List<List<String>> unorderedRec = new ArrayList<>();
    unorderedRec.add(list6);
    unorderedRec.add(list7);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes, true);
    String inputStr = "N\nE\nN\nP\n";
    InputFilter ft = readInputHelper("", inputStr, out);
    List<List<String>> formatRec = ft.formatHeader(unorderedRec);
    List<User> studentList = ft.createStudentList(formatRec);
    List<User> expectedStuList = new ArrayList<>();
    Student s1 = new Student("9198264409", "Taylor Brown", "tb67@duke.edu");
    Student s2 = new Student("9192283827", "Hao Zhang", "hz506@duke.edu");
    expectedStuList.add(s1);
    expectedStuList.add(s2);
    for (int i = 0; i < studentList.size(); i++) {
      assertEquals(expectedStuList.get(i), studentList.get(i));
    }
  }
}
