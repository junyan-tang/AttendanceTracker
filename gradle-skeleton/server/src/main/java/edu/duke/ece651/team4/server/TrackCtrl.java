package edu.duke.ece651.team4.server;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.LinkedHashMap;

enum AttendanceStatus {
  PRESENT, ABSENT, TARDY, EXCUSED, DEFAULT
}

public class TrackCtrl {
  final BufferedReader inputReader;
  final PrintStream outputWriter;

  public TrackCtrl(BufferedReader inputReader, PrintStream outputWriter) {
    this.inputReader = inputReader;
    this.outputWriter = outputWriter;
  }
  
  public void haveClass(Lecture lecture){
    Attendance new_attendance = new Attendance(lecture.getId(), lecture.getName(), new LinkedHashMap<User, AttendanceStatus>());
    for (User student : lecture.getStudentList()) {
      String prompt = "Input the status for student: " + student.getName()+", P for present, A for absent, T for tardy, E for excused";
      outputWriter.println(prompt);
      String status = inputReader.readLine();
      while(!status.equals("P") && !status.equals("A") && !status.equals("T") && !status.equals("E")){
        outputWriter.println("Invalid input, please input again");
        status = inputReader.readLine();
      }
      new_attendance.recordAttendance(student, );
    }
  }
}
