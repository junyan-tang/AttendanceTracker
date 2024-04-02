package edu.duke.ece651.team4.server;

import java.io.BufferedReader;
import java.io.PrintStream;
// import java.util.LinkedHashMap;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;

public class TrackUpdater {
  final BufferedReader inputReader;
  final PrintStream outputWriter;

  public TrackUpdater(BufferedReader inputReader, PrintStream outputWriter) {
    this.inputReader = inputReader;
    this.outputWriter = outputWriter;
  }
  
  public void haveClass(Lecture lecture) throws IOException{
    Attendance new_attendance = new Attendance(lecture.getId(), lecture.getName(), lecture.getStudentList());
    RosterView rosterView = new RosterView(new_attendance);
    for (User student : lecture.getStudentList()) {
      outputWriter.println(rosterView.displayRoster());
      String prompt = "Input the status for student: " + student.getName()+", P for present, A for absent, T for tardy, E for excused";
      outputWriter.println(prompt);
      String status = inputReader.readLine().toUpperCase();
      while(!status.equals("P") && !status.equals("A") && !status.equals("T") && !status.equals("E")){
        outputWriter.println("Invalid input, please input again");
        status = inputReader.readLine().toUpperCase();
      }
      String excuse = "";
      if(status.equals("E")){
        outputWriter.println("Please input the excuse for student: " + student.getName());
        excuse = inputReader.readLine();
      }
      new_attendance.recordAttendance(student, AttendanceStatus.fromShortCode(status.toUpperCase()), excuse);
      lecture.notifySingle("Your attendance status for class "+lecture.getName()+" on "+new_attendance.getCourseDateStr()+" has been updated as "+AttendanceStatus.fromShortCode(status.toUpperCase()), student);
    }
    lecture.addAttendance(new_attendance);
    // automatically export attendance record into a csv file
    new_attendance.autoExportAttendance();
    outputWriter.println("Attendance record for this class saved successfully.");
  }


  public void changeCertainRecord(Lecture lecture) throws IOException{
    outputWriter.println("Please input the date of the class you want to change the record (format: yyyy-MM-dd), choose from the following:");
    lecture.getAttendaceDateList().forEach((date) -> outputWriter.println(date));
    String date = inputReader.readLine();
    try{
      LocalDate courseDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

      if(!lecture.hasAttendance(courseDate.toString())){
        outputWriter.println("Record not found, please input again");
        changeCertainRecord(lecture);
        return;
      }

      String prompt_name = "Input the name of the student you want to change the record from following:";
      LinkedHashMap<User, AttendanceStatus> a_map = lecture.getAttendance(courseDate.toString()).getAttendanceRecord();
      a_map.forEach((student, status) -> outputWriter.println(student.getName()+": "+status));
      outputWriter.println(prompt_name);
      String student_name = inputReader.readLine();

      if(!lecture.hasStudent(courseDate.toString(), student_name)){
        outputWriter.println("Student not found, please input again");
        changeCertainRecord(lecture);
        return;
      }

      String prompt = "Input the status for student: " + student_name +", P for present, A for absent, T for tardy, E for excused";
      outputWriter.println(prompt);
      String status = inputReader.readLine().toUpperCase();
      while(!status.equals("P") && !status.equals("A") && !status.equals("T") && !status.equals("E")){
        outputWriter.println("Invalid input, please input again");
        status = inputReader.readLine().toUpperCase();
      }
      String excuse = "";
      if(status.equals("E")){
        outputWriter.println("Please input the excuse for student: " + student_name);
        excuse = inputReader.readLine();
      }

      if(lecture.modifyCertainAttendance(courseDate.toString(), student_name, AttendanceStatus.fromShortCode(status.toUpperCase()), excuse)){
        outputWriter.println("Record changed successfully");
        // update attendance record file
        Attendance curAttendance = lecture.getAttendance(courseDate.toString());
        curAttendance.updateRecordFile();
        outputWriter.println("Attendance record for this class updated successfully.");

        lecture.notifySingle("Your attendance status for class "+lecture.getName()+" on "+courseDate.toString()+" has been updated as "+AttendanceStatus.fromShortCode(status.toUpperCase()), lecture.getAttendance(courseDate.toString()).getStudent(student_name));
        return;
      }else{
        outputWriter.println("Record not found, please input again");
        changeCertainRecord(lecture);
        return;
      }

    }catch(DateTimeParseException e){
      outputWriter.println("Invalid date format, please input again");
      changeCertainRecord(lecture);
    }

  }

  public Boolean dropStudent(Lecture lecture) throws IOException{
    String prompt_name = "Input the name of the student you want to drop from following:";
    lecture.getStudentList().forEach((student) -> outputWriter.println(student.getName()));
    outputWriter.println(prompt_name);
    String student_name = inputReader.readLine();

    if(lecture.removeStudent(student_name)){
      outputWriter.println("Student dropped successfully");
      return true;
    }else{
      outputWriter.println("Student not found, please input again");
      return false;
    }
  }

  public boolean enrollNewStudent(Lecture lecture) throws IOException {
    String promptName = "Enter the name of the student you want to enroll into the course:";
    String promptEmail = "Enter the email of the student you want to enroll into the course:";
    String promptPhoneNum = "Enter the phone number of the student you want to enroll into the course:";
    outputWriter.println(promptName);
    String name = inputReader.readLine();
    outputWriter.println(promptEmail);
    String email = inputReader.readLine();
    outputWriter.println(promptPhoneNum);
    String phoneNum = inputReader.readLine();
    User newEnroll = new Student(phoneNum, name, email);
    lecture.addStudent(newEnroll);
    outputWriter.println("The student enrolled in the course succesfully.");
    return true;
  }

  // public String getReport(){
    
  // }
}
