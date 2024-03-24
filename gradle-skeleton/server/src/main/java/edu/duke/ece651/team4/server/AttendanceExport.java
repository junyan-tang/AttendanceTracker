package edu.duke.ece651.team4.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.HashMap;

import com.google.gson.Gson;
// import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class AttendanceExport {
  private final Attendance attend;

  public AttendanceExport(Attendance attend) {
    this.attend = attend;
  } 

  public String exportAttendanceToJson() {
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

  public void writeJsonIntoFile(String jsonStr) throws IOException {
    String date = attend.getCourseDateStr();
    String f = "src/main/resources/attendance_" + date + ".txt";
    File attendanceRec = new File(f);
    attendanceRec.createNewFile();

    FileOutputStream fileOut = new FileOutputStream(attendanceRec, true);
    byte[] bytes = jsonStr.getBytes();
    fileOut.write(bytes);
    fileOut.close();
  }
}
