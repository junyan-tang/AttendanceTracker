package edu.duke.ece651.team4.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.IOException;
import java.io.EOFException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
// import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class AttendanceExport {
  private final BufferedReader inputReader;
  private final PrintStream out;

  public AttendanceExport(BufferedReader consoleReader, PrintStream out) {
    this.inputReader = consoleReader;
    this.out = out;
  }

  public Attendance getOneAttendance(Lecture lecture, String date) {
    Attendance attend = lecture.getAttendance(date);
    return attend;
  }

  public String readUserInput(String prompt) throws IOException {
    out.print(prompt);
    String userIn = inputReader.readLine();
    if (userIn == null) {
      throw new EOFException("No more input to read.");
    }
    return userIn;
  }

  public String exportAttendanceToJson(Attendance attend) {
    Gson gson = new Gson();
    JsonObject attendObj = new JsonObject();
    String recDate = attend.getCourseDateStr();
    attendObj.addProperty("date", recDate);
    int courseID = attend.getCourseId();
    attendObj.addProperty("course ID", courseID);
    String courseName = attend.getCourseName();
    attendObj.addProperty("course name", courseName);

    // for LinkedHashMap
    JsonObject attRec = new JsonObject();
    LinkedHashMap<User, AttendanceStatus> attR = attend.getAttendanceRecord();
    for (Map.Entry<User, AttendanceStatus> entry : attR.entrySet()) {
      User stu = entry.getKey();
      AttendanceStatus status = entry.getValue();
      String userInfo = gson.toJson(stu);
      String statusCode = status.getShortCode();
      attRec.addProperty(userInfo, statusCode);
    }
    attendObj.add("attendance record", attRec);

    // for HashMap
    JsonObject excRec = new JsonObject();
    HashMap<User, String> excuse = attend.getExcuse();
    for (Map.Entry<User, String> entry : excuse.entrySet()) {
      User excStu = entry.getKey();
      String exc = entry.getValue();
      String stuInfo = gson.toJson(excStu);
      excRec.addProperty(stuInfo, exc);
    }
    attendObj.add("excuse", excRec);
    JsonElement element = gson.fromJson(attendObj.toString(), JsonElement.class);
    String res = element.toString();
    // check format
    // Gson gb = new GsonBuilder().setPrettyPrinting().create();
    // String prettyJsonStr = gb.toJson(element);
    // System.out.println(prettyJsonStr);
    return res;
  }

  public void writeJsonIntoFile(String jsonStr, Attendance attend) throws IOException {
    String date = attend.getCourseDateStr();
    String f = "src/main/resources/attendance_" + date + ".txt";
    File attendanceRec = new File(f);
    attendanceRec.createNewFile();
    FileOutputStream fileOut = new FileOutputStream(attendanceRec, true);
    byte[] bytes = jsonStr.getBytes();
    fileOut.write(bytes);
    fileOut.close();
  }

  public void exportOneAttendance(Lecture lecture) throws IOException {
    String prompt = "Please enter the date of the class you want to export attendance record (format: yyyy-MM-dd), choose from the following:\n";
    List<String> attendDate = lecture.getAttendaceDateList();
    for (int i = 0; i < attendDate.size(); i++) {
      prompt = prompt + attendDate.get(i) + "\n";
    }
    boolean validInput = false;
    String dateChosen = "";
    while (!validInput) {
      dateChosen = readUserInput(prompt);
      for (String date : attendDate) {
        if (dateChosen.equals(date)) {
          validInput = true;
        }
      }
    }
    Attendance attendChosen = getOneAttendance(lecture, dateChosen);
    String jsonStr = exportAttendanceToJson(attendChosen);
    writeJsonIntoFile(jsonStr, attendChosen);
    out.println("Attendance record exported to resources directory.");
  }
}
