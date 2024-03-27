package edu.duke.ece651.team4.server;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringReader;
import java.io.IOException;
import java.io.EOFException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class AttendanceExportTest {
  int courseID = 651;
  String courseName = "Software Engineering";
  String[] line1 = new String[] { "Name", "Email", "PhoneNumber" };
  String[] line2 = new String[] { "Mary Sue", "ms123@duke.edu", "9197328384" };
  String[] line3 = new String[] { "Taylor Brown", "tb67@duke.edu", "9198264409" };
  List<String> list1 = Arrays.asList(line1);
  List<String> list2 = Arrays.asList(line2);
  List<String> list3 = Arrays.asList(line3);
  ByteArrayOutputStream bytes = new ByteArrayOutputStream();
  PrintStream out = new PrintStream(bytes, true);
  String name = "Tong";
  String phone = "9843728873";
  String email = "tt15@duke.edu";

  @Test
  public void test_readUserInput_EOFException() {
    InputStream is = InputFilter.class.getResourceAsStream("");
    BufferedReader fileReader = new BufferedReader(new InputStreamReader(is));
    BufferedReader inputReader = new BufferedReader(new StringReader(""));
    InputFilter ft = new InputFilter(fileReader, inputReader, out);
    String prompt = "Enter Y for yes.";
    assertThrows(EOFException.class, () -> ft.readUserInput(prompt));
  }

  @Test
  public void test_exportToJson() throws IOException {
    InputStream is = InputFilter.class.getResourceAsStream("/roster2.csv");
    BufferedReader fileReader = new BufferedReader(new InputStreamReader(is));
    BufferedReader inputReader = new BufferedReader(new StringReader(""));
    InputFilter ft = new InputFilter(fileReader, inputReader, out);
    List<List<String>> stu = new ArrayList<>();
    stu.add(list1);
    stu.add(list2);
    stu.add(list3);
    List<User> stuList = ft.createStudentList(stu);
    Attendance att = new Attendance(courseID, courseName, stuList);
    AttendanceExport atte = new AttendanceExport(inputReader, out);
    User student = new Student(phone, name, email);
    att.getExcuse().put(student, "sick");
    String js = atte.exportAttendanceToJson(att);
    // atte.writeJsonIntoFile(js, att);
  }
}
