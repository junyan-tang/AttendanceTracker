package edu.duke.ece651.team4.CourseManagementApp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.IOException;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.time.Instant;
import java.io.File;

import edu.duke.ece651.team4.shared.LoginManager;
import edu.duke.ece651.team4.shared.Roster;
import edu.duke.ece651.team4.shared.RosterDAO;
import edu.duke.ece651.team4.shared.RosterDAOImpl;

public class AdminInterface {
  // final InputFilter inputFilter;
  final BufferedReader fileReader;
  final BufferedReader consoleReader;
  final PrintStream out;

  // final WeekReport weekReport;
  // final AttendanceExport attendanceExp;

  public AdminInterface(BufferedReader fileReader, BufferedReader consoleReader, PrintStream out) {
    this.fileReader = fileReader;
    this.consoleReader = consoleReader;
    this.out = out;
  }

  public void addCourse() throws IOException {
    out.println("Please enter the course ID: ");
    String courseID = consoleReader.readLine();
    out.println("Please enter the course name: ");
    String courseName = consoleReader.readLine();
    if (!CourseController.addCourse(courseID, courseName)) {
      out.println(
          "CourseID or CourseName already exists. Please press c to enter the course info again or press e to exit: ");
      String choice = consoleReader.readLine();
      while (!choice.toUpperCase().equals("C") && !choice.toUpperCase().equals("E")) {
        out.println("Invalid input. Please press c to enter the course info again or press e to exit: ");
        choice = consoleReader.readLine();
      }
      if (choice.toUpperCase().equals("E")) {
        return;
      }
      if (choice.toUpperCase().equals("C")) {
        addCourse();
      }
    }
  }

  public void removeCourse() throws IOException {
    CourseController.getAllCourses().stream().map(course -> course.courseName).forEach(out::println);
    out.println("Please enter the course name from the list above: ");
    String courseName = consoleReader.readLine();
    out.println(
        "Double check: You have chosen to remove course: " + courseName + ". Press y to confirm or n to cancel: ");
    String confirm = consoleReader.readLine();
    while (!confirm.toUpperCase().equals("Y") && !confirm.toUpperCase().equals("N")) {
      out.println("Invalid input. Please press y to confirm or n to cancel: ");
      confirm = consoleReader.readLine();
    }
    if (confirm.toUpperCase().equals("N")) {
      return;
    }
    if (!CourseController.removeCourse(courseName)) {
      out.println("Course does not exist. Please press c to enter the course name again or press e to exit: ");
      String choice = consoleReader.readLine();
      while (!choice.toUpperCase().equals("C") && !choice.toUpperCase().equals("E")) {
        out.println("Invalid input. Please press c to enter the course name again or press e to exit: ");
        choice = consoleReader.readLine();
      }
      if (choice.toUpperCase().equals("E")) {
        return;
      }
      if (choice.toUpperCase().equals("C")) {
        removeCourse();
      }
    }
  }

  public void updateCourse() throws IOException {
    // print every course name and id
    CourseController.getAllCourses().stream().map(course -> course.courseName + " " + course.courseID)
        .forEach(out::println);
    out.println("Please enter the course ID you want to modify: ");
    String courseID = consoleReader.readLine();
    out.println("Please enter the new course name: ");
    String courseName = consoleReader.readLine();
    if (!CourseController.updateCourse(courseID, courseName)) {
      out.println("Course does not exist. Please press c to enter the course info again or press e to exit: ");
      String choice = consoleReader.readLine();
      while (!choice.toUpperCase().equals("C") && !choice.toUpperCase().equals("E")) {
        out.println("Invalid input. Please press c to enter the course info again or press e to exit: ");
        choice = consoleReader.readLine();
      }
      if (choice.toUpperCase().equals("E")) {
        return;
      }
      if (choice.toUpperCase().equals("C")) {
        updateCourse();
      }
    }
  }

  public void addSection() throws IOException {
    Set<String> rec = CourseController.getAllCourses().stream()
        .peek(course -> out.println(course.courseName + " " + course.courseID)).map(course -> course.courseID)
        .collect(Collectors.toCollection(HashSet::new));
    // TODO: also need a list of teacher id to check
    out.println("Please enter the course ID you want to add a section to: ");
    String courseID = consoleReader.readLine();
    while (!rec.contains(courseID)) {
      out.println("Course does not exist. Please input again: ");
      courseID = consoleReader.readLine();
    }
    out.println("Please enter the section ID for new Section: ");
    String sectionID = consoleReader.readLine();
    out.println("Please enter the teacher ID for new Section: ");
    String teacherID = consoleReader.readLine();
    // TODO: check if teacher id exists
    if (!SectionController.addSection(sectionID, courseID, teacherID)) {
      out.println("Section ID already exists. Please press c to enter the section info again or press e to exit: ");
      String choice = consoleReader.readLine();
      while (!choice.toUpperCase().equals("C") && !choice.toUpperCase().equals("E")) {
        out.println("Invalid input. Please press c to enter the section info again or press e to exit: ");
        choice = consoleReader.readLine();
      }
      if (choice.toUpperCase().equals("E")) {
        return;
      }
      if (choice.toUpperCase().equals("C")) {
        addSection();
      }
    }
  }

  public void removeSection() throws IOException {
    Set<String> rec_c = CourseController.getAllCourses().stream()
        .peek(course -> out.println(course.courseName + " " + course.courseID)).map(course -> course.courseID)
        .collect(Collectors.toCollection(HashSet::new));
    out.println("Please enter the Course ID the Section belongs to: ");
    String courseID = consoleReader.readLine();
    while (!rec_c.contains(courseID)) {
      out.println("Section does not exist. Please input again: ");
      courseID = consoleReader.readLine();
    }
    Set<String> rec_s = SectionController.getAllSectionsCour(courseID).stream()
        .peek(section -> out.println(section.sectionID)).map(section -> section.sectionID)
        .collect(Collectors.toCollection(HashSet::new));
    out.println("Please enter the section ID you want to remove from list above: ");
    String sectionID = consoleReader.readLine();
    while (!rec_s.contains(sectionID)) {
      out.println("Section does not exist. Please input again: ");
      sectionID = consoleReader.readLine();
    }

    out.println(
        "Double check: You have chosen to remove section: " + sectionID + ". Press y to confirm or n to cancel: ");
    String confirm = consoleReader.readLine();
    while (!confirm.toUpperCase().equals("Y") && !confirm.toUpperCase().equals("N")) {
      out.println("Invalid input. Please press y to confirm or n to cancel: ");
      confirm = consoleReader.readLine();
    }
    if (confirm.toUpperCase().equals("N")) {
      return;
    }

    if (!SectionController.deleteSection(sectionID)) {
      out.println("Section does not exist. Please press c to enter the section ID again or press e to exit: ");
      String choice = consoleReader.readLine();
      while (!choice.toUpperCase().equals("C") && !choice.toUpperCase().equals("E")) {
        out.println("Invalid input. Please press c to enter the section ID again or press e to exit: ");
        choice = consoleReader.readLine();
      }
      if (choice.toUpperCase().equals("E")) {
        return;
      }
      if (choice.toUpperCase().equals("C")) {
        removeSection();
      }
    }
  }

  public void updateSection() throws IOException {
    Set<String> rec_c = CourseController.getAllCourses().stream()
        .peek(course -> out.println(course.courseName + " " + course.courseID)).map(course -> course.courseID)
        .collect(Collectors.toCollection(HashSet::new));
    out.println("Please enter the Course ID the Section belongs to: ");
    String courseID = consoleReader.readLine();
    while (!rec_c.contains(courseID)) {
      out.println("Section does not exist. Please input again: ");
      courseID = consoleReader.readLine();
    }
    Set<String> rec_s = SectionController.getAllSectionsCour(courseID).stream()
        .peek(section -> out.println(section.sectionID)).map(section -> section.sectionID)
        .collect(Collectors.toCollection(HashSet::new));
    out.println("Please enter the section ID you want to update from list above: ");
    String sectionID = consoleReader.readLine();
    while (!rec_s.contains(sectionID)) {
      out.println("Section does not exist. Please input again: ");
      sectionID = consoleReader.readLine();
    }
    // TODO: also need a list of teacher id to check
    out.println("Please enter the new teacher ID for the section: ");
    String teacherID = consoleReader.readLine();
    // TODO: check if teacher id exists
    if (!SectionController.updateSection(sectionID, teacherID)) {
      out.println("Section does not exist. Please press c to enter the section info again or press e to exit: ");
      String choice = consoleReader.readLine();
      while (!choice.toUpperCase().equals("C") && !choice.toUpperCase().equals("E")) {
        out.println("Invalid input. Please press c to enter the section info again or press e to exit: ");
        choice = consoleReader.readLine();
      }
      if (choice.toUpperCase().equals("E")) {
        return;
      }
      if (choice.toUpperCase().equals("C")) {
        updateSection();
      }
    }
  }

  public void addAttendance() throws IOException {
    Set<String> rec_c = CourseController.getAllCourses().stream()
        .peek(course -> out.println(course.courseName + " " + course.courseID)).map(course -> course.courseID)
        .collect(Collectors.toCollection(HashSet::new));
    out.println("Please enter the Course ID the Section belongs to: ");
    String courseID = consoleReader.readLine();
    while (!rec_c.contains(courseID)) {
      out.println("Course does not exist. Please input again: ");
      courseID = consoleReader.readLine();
    }
    Set<String> rec_s = SectionController.getAllSectionsCour(courseID).stream()
        .peek(section -> out.println(section.sectionID)).map(section -> section.sectionID)
        .collect(Collectors.toCollection(HashSet::new));
    out.println("Please enter the section ID you want to add attendance to from list above: ");
    String sectionID = consoleReader.readLine();
    while (!rec_s.contains(sectionID)) {
      out.println("Section does not exist. Please input again: ");
      sectionID = consoleReader.readLine();
    }
    Set<String> rec_d = AttendanceController.getAttendenceDatelist(sectionID).stream().peek(date -> out.println(date))
        .map(date -> date).collect(Collectors.toCollection(HashSet::new));
    out.println("Please enter the date for the attendance: ");
    String date = consoleReader.readLine();
    while (!rec_d.contains(date)) {
      out.println("Invalid date. Please enter the date again: ");
      date = consoleReader.readLine();
    }
    // TODO: also need a list of student id to check
    out.println("Please enter the student ID for the attendance: ");
    String studentID = consoleReader.readLine();
    // TODO: check if student id exists
    out.println("Please enter the status for the attendance: ");
    String status = consoleReader.readLine();
    while (!status.toUpperCase().equals("PRESENT") && !status.toUpperCase().equals("ABSENT")
        && !status.toUpperCase().equals("EXCUSED") && !status.toUpperCase().equals("TARDY")) {
      out.println("Invalid input. Please enter PRESENT, ABSENT, TARDY or EXCUSED: ");
      status = consoleReader.readLine();
    }

    if (!AttendanceController.addAttendanceDated(studentID, sectionID, AttendanceController.parseDate(date), status)) {
      out.println("Attendance already exists. Please press c to enter the attendance info again or press e to exit: ");
      String choice = consoleReader.readLine();
      while (!choice.toUpperCase().equals("C") && !choice.toUpperCase().equals("E")) {
        out.println("Invalid input. Please press c to enter the attendance info again or press e to exit: ");
        choice = consoleReader.readLine();
      }
      if (choice.toUpperCase().equals("E")) {
        return;
      }
      if (choice.toUpperCase().equals("C")) {
        addAttendance();
      }
    }

  }

  public void removeAttendance() throws IOException {
    Set<String> rec_c = CourseController.getAllCourses().stream()
        .peek(course -> out.println(course.courseName + " " + course.courseID)).map(course -> course.courseID)
        .collect(Collectors.toCollection(HashSet::new));
    out.println("Please enter the Course ID the Section belongs to: ");
    String courseID = consoleReader.readLine();
    while (!rec_c.contains(courseID)) {
      out.println("Course does not exist. Please input again: ");
      courseID = consoleReader.readLine();
    }

    Set<String> rec_s = SectionController.getAllSectionsCour(courseID).stream()
        .peek(section -> out.println(section.sectionID)).map(section -> section.sectionID)
        .collect(Collectors.toCollection(HashSet::new));
    out.println("Please enter the section ID you want to remove attendance from list above: ");
    String sectionID = consoleReader.readLine();
    while (!rec_s.contains(sectionID)) {
      out.println("Section does not exist. Please input again: ");
      sectionID = consoleReader.readLine();
    }

    Set<String> rec_d = AttendanceController.getAttendenceDatelist(sectionID).stream().peek(date -> out.println(date))
        .map(date -> date).collect(Collectors.toCollection(HashSet::new));
    out.println("Please enter the date for the attendance: ");
    String date = consoleReader.readLine();
    while (!rec_d.contains(date)) {
      out.println("Invalid date. Please enter the date again: ");
      date = consoleReader.readLine();
    }

    Set<String> rec_su = AttendanceController.getAttendenceStulist(sectionID, AttendanceController.parseDate(date))
        .stream()
        .peek(SID -> out.println(SID)).map(SID -> SID).collect(Collectors.toCollection(HashSet::new));
    out.println("Please enter the student ID for the attendance: ");
    String studentID = consoleReader.readLine();
    while (!rec_su.contains(studentID)) {
      out.println("Student does not exist. Please input again: ");
      studentID = consoleReader.readLine();
    }

    out.println("Double check: You have chosen to remove attendance for student: " + studentID + " on date: " + date
        + ". Press y to confirm or n to cancel: ");
    String confirm = consoleReader.readLine();
    while (!confirm.toUpperCase().equals("Y") && !confirm.toUpperCase().equals("N")) {
      out.println("Invalid input. Please press y to confirm or n to cancel: ");
      confirm = consoleReader.readLine();
    }
    if (confirm.toUpperCase().equals("N")) {
      return;
    }

    if (!AttendanceController.removeAttendence(studentID, sectionID, AttendanceController.parseDate(date))) {
      out.println("Attendance does not exist. Please press c to enter the attendance info again or press e to exit: ");
      String choice = consoleReader.readLine();
      while (!choice.toUpperCase().equals("C") && !choice.toUpperCase().equals("E")) {
        out.println("Invalid input. Please press c to enter the attendance info again or press e to exit: ");
        choice = consoleReader.readLine();
      }
      if (choice.toUpperCase().equals("E")) {
        return;
      }
      if (choice.toUpperCase().equals("C")) {
        removeAttendance();
      }
    }

  }

  public void updateAttendance() throws IOException {
    Set<String> rec_c = CourseController.getAllCourses().stream()
        .peek(course -> out.println(course.courseName + " " + course.courseID)).map(course -> course.courseID)
        .collect(Collectors.toCollection(HashSet::new));
    out.println("Please enter the Course ID the Section belongs to: ");
    String courseID = consoleReader.readLine();
    while (!rec_c.contains(courseID)) {
      out.println("Course does not exist. Please input again: ");
      courseID = consoleReader.readLine();
    }

    Set<String> rec_s = SectionController.getAllSectionsCour(courseID).stream()
        .peek(section -> out.println(section.sectionID)).map(section -> section.sectionID)
        .collect(Collectors.toCollection(HashSet::new));
    out.println("Please enter the section ID you want to update attendance from list above: ");
    String sectionID = consoleReader.readLine();
    while (!rec_s.contains(sectionID)) {
      out.println("Section does not exist. Please input again: ");
      sectionID = consoleReader.readLine();
    }

    Set<String> rec_d = AttendanceController.getAttendenceDatelist(sectionID).stream().peek(date -> out.println(date))
        .map(date -> date).collect(Collectors.toCollection(HashSet::new));
    out.println("Please enter the date for the attendance: ");
    String date = consoleReader.readLine();
    while (!rec_d.contains(date)) {
      out.println("Invalid date. Please enter the date again: ");
      date = consoleReader.readLine();
    }

    Set<String> rec_su = AttendanceController.getAttendenceStulist(sectionID, AttendanceController.parseDate(date))
        .stream()
        .peek(SID -> out.println(SID)).map(SID -> SID).collect(Collectors.toCollection(HashSet::new));
    out.println("Please enter the student ID for the attendance: ");
    String studentID = consoleReader.readLine();
    while (!rec_su.contains(studentID)) {
      out.println("Student does not exist. Please input again: ");
      studentID = consoleReader.readLine();
    }

    out.println("Please enter the new status for the attendance: ");
    String status = consoleReader.readLine();
    while (!status.toUpperCase().equals("PRESENT") && !status.toUpperCase().equals("ABSENT")
        && !status.toUpperCase().equals("EXCUSED") && !status.toUpperCase().equals("TARDY")) {
      out.println("Invalid input. Please enter PRESENT, ABSENT, TARDY or EXCUSED: ");
      status = consoleReader.readLine();
    }

    if (!AttendanceController.updateAttendence(studentID, sectionID, AttendanceController.parseDate(date), status)) {
      out.println("Attendance does not exist. Please press c to enter the attendance info again or press e to exit: ");
      String choice = consoleReader.readLine();
      while (!choice.toUpperCase().equals("C") && !choice.toUpperCase().equals("E")) {
        out.println("Invalid input. Please press c to enter the attendance info again or press e to exit: ");
        choice = consoleReader.readLine();
      }
      if (choice.toUpperCase().equals("E")) {
        return;
      }
      if (choice.toUpperCase().equals("C")) {
        updateAttendance();
      }
    }
  }

  public void readFromFolder(String type) throws IOException {
    out.println("Will read from the folder \"records\". Please make sure the folder exists and contains the csv file.");
    String folderPath = "./records";
    File folder = new File(folderPath);
    File[] listOfFiles = folder.listFiles();
    out.println("\"records\" has following files: ");
    if (listOfFiles != null) {
      for (File file : listOfFiles) {
        if (file.isFile()) {
          out.println(file.getName());
        }
      }
    } else {
      out.println("No files in the folder. ");
      return;
    }
    for (File file : listOfFiles) {
      if (file.isFile()) {
        out.println("Reading from file: " + file.getName());
        try {
          List<List<String>> records = InputCourseFilter.readCSVFile(folderPath + "/" + file.getName());
          if (type.equals("course")) {
            InputCourseFilter.insertIntoDB(records, out);
          } else if (type.equals("roaster")) {
            InputCourseFilter.insertIntoDBRoaster(records, out);
          }
          //InputCourseFilter.insertIntoDB(records, out);
        } catch (IOException e) {
          out.println("Error reading from file: " + file.getName());
          e.printStackTrace();
        }
      }
    }
  }

  public void loginAdmin() {
    try {
      out.println("Please enter your netID: ");
      String netID = consoleReader.readLine();
      out.println("Please enter your password: ");
      String password = consoleReader.readLine();
      String res = LoginManager.logUser(netID, password, "admin");
      while (!res.equals("Login successful")) {
        out.println(res);
        out.println("Please enter your netID: ");
        netID = consoleReader.readLine();
        out.println("Please enter your password: ");
        password = consoleReader.readLine();
        res = LoginManager.logUser(netID, password, "admin");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void enrollSingleStuToSec() throws IOException {

    RosterDAO rosterDAO = new RosterDAOImpl();
    Set<String> rec_c = CourseController.getAllCourses().stream()
        .peek(course -> out.println(course.courseName + " " + course.courseID)).map(course -> course.courseID)
        .collect(Collectors.toCollection(HashSet::new));
    out.println("Please enter the Course ID the Section belongs to: ");
    String courseID = consoleReader.readLine();
    while (!rec_c.contains(courseID)) {
      out.println("Course does not exist. Please input again: ");
      courseID = consoleReader.readLine();
    }

    Set<String> rec_s = SectionController.getAllSectionsCour(courseID).stream()
        .peek(section -> out.println(section.sectionID)).map(section -> section.sectionID)
        .collect(Collectors.toCollection(HashSet::new));
    out.println("Please enter the section ID you want to remove attendance from list above: ");
    String sectionID = consoleReader.readLine();
    while (!rec_s.contains(sectionID)) {
      out.println("Section does not exist. Please input again: ");
      sectionID = consoleReader.readLine();
    }

    Set<String> rec_su = StuController.getAllStudent().stream()
        .peek(student -> out.println(student.firstName + " " + student.lastName + " " + student.netid))
        .map(student -> student.netid).collect(Collectors.toCollection(HashSet::new));
    out.println("Please enter the student ID for the attendance: ");
    String studentID = consoleReader.readLine();
    while (!rec_su.contains(studentID)) {
      out.println("Student does not exist. Please input again: ");
      studentID = consoleReader.readLine();
    }
    while (true) {
      if (rosterDAO.isStudentInSection(studentID, sectionID)) {
        out.println("Student already enrolled in the course.\n");
        return;
      }
      rosterDAO.addStudentToSection(studentID, sectionID);
      return;
    }

  }

  public void dropSingleStuFromSec() throws IOException{
    RosterDAO rosterDAO = new RosterDAOImpl();
    Set<String> rec_c = CourseController.getAllCourses().stream()
        .peek(course -> out.println(course.courseName + " " + course.courseID)).map(course -> course.courseID)
        .collect(Collectors.toCollection(HashSet::new));
    out.println("Please enter the Course ID the Section belongs to: ");
    String courseID = consoleReader.readLine();
    while (!rec_c.contains(courseID)) {
      out.println("Course does not exist. Please input again: ");
      courseID = consoleReader.readLine();
    }

    Set<String> rec_s = SectionController.getAllSectionsCour(courseID).stream()
        .peek(section -> out.println(section.sectionID)).map(section -> section.sectionID)
        .collect(Collectors.toCollection(HashSet::new));
    out.println("Please enter the section ID you want to remove from roaster list above: ");
    String sectionID = consoleReader.readLine();
    while (!rec_s.contains(sectionID)) {
      out.println("Section does not exist. Please input again: ");
      sectionID = consoleReader.readLine();
    }

    Set<String> rec_su = StuController.getStudentSections(sectionID).stream()
    .peek(SID -> out.println(SID)).map(SID -> SID).collect(Collectors.toCollection(HashSet::new));
    out.println("Please enter the student ID to remove from roaster: ");
    String studentID = consoleReader.readLine();
    while (!rec_su.contains(studentID)) {
      out.println("Student does not exist. Please input again: ");
      studentID = consoleReader.readLine();
    }
    while (true) {
      if (!rosterDAO.isStudentInSection(studentID, sectionID)) {
        out.println("Student not enrolled in the course.\n");
        return;
      }
      rosterDAO.removeStudentFromSection(studentID, sectionID);
      return;
    }
  }

  public static void main(String[] args) {
    BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
    PrintStream output = System.out;
    boolean finishAction = false;
    AdminInterface updater = new AdminInterface(null, consoleReader, output);
    updater.loginAdmin();
    while (!finishAction) {
      System.out.println("Hello Admin! What do you want to do?");
      System.out.println("1. add a new course");
      System.out.println("2. remove a course");
      System.out.println("3. change course name");
      System.out.println("4. add a new section to a course");
      System.out.println("5. remove an exsisting section from a course");
      System.out.println("6. update a section");
      System.out.println("7. add attendance to exsisting lecture");
      System.out.println("8. remove attendance");
      System.out.println("9. update attendance");
      System.out.println("10. read from folder to setup course and section.");
      System.out.println("11. enroll students to a section from files.");
      System.out.println("12. enroll student to a section");
      System.out.println("13. drop student from a section");
      System.out.println("14. Logout");

      // Reading professor's choice
      System.out.print("Please enter your choice (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14): ");
      try {
        String choice = consoleReader.readLine();
        // TrackUpdater updater = new TrackUpdater(consoleReader, output);

        switch (choice) {
          case "1":
            // Here you add the logic for starting a new course
            output.println("You have chosen to add a new course.");
            updater.addCourse();
            break;
          case "2":
            // Here you add the logic for modifying student attendance status
            output.println("You have chosen to remove a course.");
            updater.removeCourse();
            break;
          case "3":
            output.println("You have chosen modify course name.");
            updater.updateCourse();
            break;
          case "4":
            output.println("You have chosen to add new sections for courses.");
            updater.addSection();
            break;
          case "5":
            output.println("You have chosen to remove a section from a course.");
            updater.removeSection();
            break;
          case "6":
            output.println("You have chosen to update a section.");
            updater.updateSection();
            break;
          case "7":
            output.println("You have chosen to add attendance to an exsisting section.");
            updater.addAttendance();
            break;
          case "8":
            output.println("You have chosen to remove attendance.");
            updater.removeAttendance();
            break;
          case "9":
            output.println("You have chosen to update attendance.");
            updater.updateAttendance();
            break;
          case "10":
            // Logout action
            output.println("You have chosen to read from folder .");
            updater.readFromFolder("course");
            break;
          case "11":
            // Logout action
            output.println("You have chosen to read from folder and enroll students to a section.");
            updater.readFromFolder("roaster");
            break;
          case "12":
            // Logout action
            output.println("You have chosen to enroll student to a section.");
            updater.enrollSingleStuToSec();
            break;
          case "13":
            // Logout action
            output.println("You have chosen to drop student from a section.");
            updater.dropSingleStuFromSec();
            break;
          case "14":
            // Logout action
            output.println("You have chosen to logout.");
            finishAction = true;
            break;

          default:
            output.println("Invalid input, please enter 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 or 11.");
            continue;
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
