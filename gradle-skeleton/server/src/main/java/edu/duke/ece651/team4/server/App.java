/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ece651.team4.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import edu.duke.ece651.team4.shared.MyName;

public class App {
  final InputFilter inputFilter;
  final BufferedReader fileReader;
  final BufferedReader consoleReader;
  final PrintStream out;
  final WeekReport weekReport;
  final AttendanceExport attendanceExp;

  public App(InputFilter inputFilter, BufferedReader fileReader, BufferedReader consoleReader, PrintStream out) {
    this.inputFilter = inputFilter;
    this.fileReader = fileReader;
    this.consoleReader = consoleReader;
    this.out = out;
    this.weekReport = new WeekReport();
    this.attendanceExp = new AttendanceExport(consoleReader, out);
  }

  public List<User> uploadRoaster() throws IOException {
    return inputFilter.uploadRoster();
  }

  public String getMessage() {
    return "Hello from the server for " + MyName.getName();
  }

  public static void main(String[] args) throws IOException {
    // 注册账号
    BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

    System.out.println("Please create your account.\n");
    System.out.print("What's your netid?");
    String registerUsername = consoleReader.readLine();

    System.out.print("What's your password");
    String registerPassword = consoleReader.readLine();

    // 假设User是一个有两个字符串参数的构造函数的类：用户名和密码
    AccountManager acm = new AccountManager();
    RegisterChecker rc = new RegisterChecker(acm);

    rc.tryAddAccount(registerUsername, registerPassword);

    // log in
    boolean loginSuccess = false;
    while (!loginSuccess) {
      LoginChecker lc = new LoginChecker(acm);
      System.out.println("Please log in to your account");
      System.out.print("What's your netid?");
      String loginUsername = consoleReader.readLine();

      System.out.print("What's your password?");
      String loginPassword = consoleReader.readLine();
      if (lc.checkPassword(loginUsername, loginPassword)) {
        loginSuccess = true;
        System.out.println("login succeed.");
      } else {
        System.out.println("Login failed, username or password is wrong. Please try again.");
      }
    }

    InputStream is = InputFilter.class.getResourceAsStream("/roster2.csv");
    BufferedReader fileReader = new BufferedReader(new InputStreamReader(is));
    InputFilter inputFilter = new InputFilter(fileReader, consoleReader, System.out);
    App app = new App(inputFilter, fileReader, consoleReader, System.out);
    ArrayList<User> student_list = new ArrayList<>(app.uploadRoaster());

    int courseId = 1;
    String courseName = "test";

    try{

    Lecture new_lecture = new Lecture(courseId, courseName, new LinkedHashMap<String, User>(),
        new LinkedHashMap<String, User>(), new LinkedHashMap<String, Attendance>(), new EmailSender());

    app.weekReport.addTask(new_lecture);

    
    for (User stu : student_list) {
      new_lecture.addStudent(stu);
    }

    boolean finishAction = false;
    while (!finishAction) {
      System.out.println("Hello professor! What do you want to do?");
      System.out.println("1. Start a new course");
      System.out.println("2. Modify student attendance status");
      System.out.println("3. Drop Student from Course");
      System.out.println("4. Export attendance record");
      System.out.println("5. Logout");


      // Reading professor's choice
      System.out.print("Please enter your choice (1, 2, 3, 4 or 5): ");
      String choice = consoleReader.readLine();
      TrackUpdater updater = new TrackUpdater(consoleReader, System.out);

      switch (choice) {
        case "1":
          // Here you add the logic for starting a new course
          System.out.println("You have chosen to start a new course.");          
          updater.haveClass(new_lecture);
          break;
        case "2":
          // Here you add the logic for modifying student attendance status
          System.out.println("You have chosen to modify student attendance status.");
          updater.changeCertainRecord(new_lecture);
          break;
        case "3":
          System.out.println("You have chosen to drop student from course.");
          updater.dropStudent(new_lecture);
          break;
        case "4":
          // export attendance record to 'server/src/main/resources' directory
          System.out.println("You have chosen to export an attendance record.");
          app.attendanceExp.exportOneAttendance(new_lecture);
          break;
        case "5":
          // Logout action
          System.out.println("Logging out...");
          finishAction = true; // Exit the loop to logout
          break;

        default:
          System.out.println("Invalid input, please enter 1 or 2.");
          continue;
      }
    }
    

    //System.out.println(app.getMessage());
    

    }catch(Exception e){
      System.out.println(e);
    }
    System.exit(0);
  }
}
