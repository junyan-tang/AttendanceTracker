package edu.duke.ece651.team4.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Instant;
import java.util.List;

import edu.duke.ece651.team4.shared.AttendanceDAO;
import edu.duke.ece651.team4.shared.AttendanceDAOImpl;
import edu.duke.ece651.team4.shared.RosterDAO;
import edu.duke.ece651.team4.shared.RosterDAOImpl;
import edu.duke.ece651.team4.shared.Section;
import edu.duke.ece651.team4.shared.SectionDAO;
import edu.duke.ece651.team4.shared.SectionDAOImpl;
import edu.duke.ece651.team4.shared.UserDAO;
import edu.duke.ece651.team4.shared.UserDAOImpl;

public class Server {
    private int port;
    // private ServerSocket serverSocket;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Server(int port) {
        this.port = port;
    }

    public static class ClientHandler extends Thread {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        public ClientHandler(Socket socket, BufferedReader in, PrintWriter out) {
            this.socket = socket;
            this.in = in;
            this.out = out;
        }

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                // in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                // out = new PrintWriter(socket.getOutputStream(), true);
                handleClient();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cleanup();
            }
        }

        public void handleClient() {
            UserDAO userDAO = new UserDAOImpl();
            try {
                while (true) {
                    String userID = login();
                    if (userDAO.isFaculty(userID)) {
                        // faculty
                        facultyHandler(userID);
                    } else {
                        // student
                        studentHandler(userID);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void startCourse(String sectionID) throws Exception {
            SectionDAO sectionDAO = new SectionDAOImpl();
            Section section = sectionDAO.getSection(sectionID);
            AttendanceDAO attendanceDAO = new AttendanceDAOImpl();

            for (edu.duke.ece651.team4.shared.Student student : sectionDAO.getAllStudentsInSection(sectionID)) {
                out.println(
                        "Please input the status for student: " + student.firstName + " " + student.lastName + ", P for present, A for absent, T for tardy\n");
                String status = in.readLine().toUpperCase();
                while (!status.equals("P") && !status.equals("A") && !status.equals("T") && !status.equals("E")) {
                    out.println("Invalid input, please input again");
                    status = in.readLine().toUpperCase();
                }
                switch(status){
                    case "P":
                        status = "PRESENT";
                        break;
                    case "A":
                        status = "ABSENT";
                        break;
                    case "T":
                        status = "TARDY";
                        break;
                }
                edu.duke.ece651.team4.shared.Attendance new_attendance = new edu.duke.ece651.team4.shared.Attendance(
                        section.courseID, sectionID, student.netid, Instant.now(), status);
                attendanceDAO.addAttendance(new_attendance);
            }

        }

        public void startnewCourse(String teacherID) throws Exception {
            out.println("Please enter the section ID of the section you want to start an attendance.\n");
            String sectionID = in.readLine();
            SectionDAO sectionDAO = new SectionDAOImpl();
            while (true) {
                if (sectionDAO.isTeacherHasThatSection(teacherID, sectionID)) {
                    break;
                } else {
                    out.println("Invalid section ID. Please try again.\n");
                    sectionID = in.readLine();
                }
            }
            startCourse(sectionID);
        }

        public void startSpecCourse(String sectionID) throws Exception {
            SectionDAO sectionDAO = new SectionDAOImpl();
            Section section = sectionDAO.getSection(sectionID);
            AttendanceDAO attendanceDAO = new AttendanceDAOImpl();

            out.println("Please input the date of the attendance in the format of yyyy-MM-ddTHH:mm:ssZ\n");
            String date = in.readLine();
            Instant instant = Instant.parse(date);

            for (edu.duke.ece651.team4.shared.Student student : sectionDAO.getAllStudentsInSection(sectionID)) {
                out.println(
                        "Please input the status for student: " + student.firstName + " " + student.lastName + ", P for present, A for absent, T for tardy\n");
                String status = in.readLine().toUpperCase();
                while (!status.equals("P") && !status.equals("A") && !status.equals("T") && !status.equals("E")) {
                    out.println("Invalid input, please input again");
                    status = in.readLine().toUpperCase();
                }
                switch(status){
                    case "P":
                        status = "PRESENT";
                        break;
                    case "A":
                        status = "ABSENT";
                        break;
                    case "T":
                        status = "TARDY";
                        break;
                }
                edu.duke.ece651.team4.shared.Attendance new_attendance = new edu.duke.ece651.team4.shared.Attendance(
                        section.courseID, sectionID, student.netid, instant, status);
                attendanceDAO.addAttendance(new_attendance);
            }
        }

        public void recordPreviousAttendance(String teacherID) throws Exception {
            out.println(
                    "Please enter the section ID of the section you want to record attendance for a previous day.\n");
            String sectionID = in.readLine();
            SectionDAO sectionDAO = new SectionDAOImpl();
            while (true) {
                if (sectionDAO.isTeacherHasThatSection(teacherID, sectionID)) {
                    break;
                } else {
                    out.println("Invalid section ID. Please try again.\n");
                    sectionID = in.readLine();
                }
            }
            startSpecCourse(sectionID);
        }

        public void facultyChooseSection(String teacherID) throws Exception {
            out.println(
                    "Hello professor! Please enter your section ID.\n");
            while (true) {
                String sectionID = in.readLine();
                SectionDAO sectionDAO = new SectionDAOImpl();
                if (sectionDAO.teacherSelectSection(teacherID, sectionID)) {
                    return;
                } else {
                    out.println("Invalid section ID. Please try again.\n");
                }
            }

        }

        public void modifyAttendaneStu(String sectionID, Instant date) throws Exception {
            AttendanceDAO attendanceDAO = new AttendanceDAOImpl();
            UserDAO userDAO = new UserDAOImpl();
            List<String> students = attendanceDAO.getAttendanceStulist(sectionID, date);
            StringBuilder studentInfoBuilder = new StringBuilder();
            int sequenceNumber = 1;
            List<String> studentsNames = userDAO.getUsernameListByID(students);
            for (String student : studentsNames) {
                String studentRecord = String.format("%d. Student: %s\n", sequenceNumber, student);
                studentInfoBuilder.append(studentRecord);
                sequenceNumber++;
            }
            studentInfoBuilder.append("Please input the number of the student you want to modify.\n");
            String studentInfo = studentInfoBuilder.toString();
            out.println(studentInfo);
            while (true) {
                int selectedNumber = Integer.parseInt(in.readLine());
                if (selectedNumber < 1 || selectedNumber > students.size()) {
                    out.println("Invalid input. Please try again.\n");
                    continue;
                }
                String selectedStudent = students.get(selectedNumber - 1);
                out.println("Please input the status for this student, P for present, A for absent, T for tardy\n");
                String status = in.readLine().toUpperCase();
                while (!status.equals("P") && !status.equals("A") && !status.equals("T")) {
                    out.println("Invalid input, please input again");
                    status = in.readLine().toUpperCase();
                }
                edu.duke.ece651.team4.shared.Attendance attendance = attendanceDAO.getAttendance(selectedStudent,
                        sectionID,
                        date);
                
                switch(status){
                    case "P":
                        attendance.status = "PRESENT";
                        break;
                    case "A":
                        attendance.status = "ABSENT";
                        break;
                    case "T":
                        attendance.status = "TARDY";
                        break;
                }
                //attendance.status = status;
                attendanceDAO.updateAttendance(attendance);
                return;
            }
        }

        public void modifyAttendane(String sectionID) throws Exception {
            // SectionDAO sectionDAO = new SectionDAOImpl();
            AttendanceDAO attendanceDAO = new AttendanceDAOImpl();
            List<String> attendances = attendanceDAO.getAttendanceDatelist(sectionID);
            StringBuilder attendanceInfoBuilder = new StringBuilder();
            int sequenceNumber = 1;
            for (String attendance : attendances) {
                String attendanceRecord = String.format("%d. Time: %s\n",
                        sequenceNumber,
                        attendance);
                attendanceInfoBuilder.append(attendanceRecord);
                sequenceNumber++;
            }
            attendanceInfoBuilder.append("Please input the number of the attendance record you want to modify.\n");
            String attendanceInfo = attendanceInfoBuilder.toString();
            out.println(attendanceInfo);
            while (true) {
                int selectedNumber = Integer.parseInt(in.readLine());
                if (selectedNumber < 1 || selectedNumber > attendances.size()) {
                    out.println("Invalid input. Please try again.\n");
                    continue;
                }
                String selectedAttendance = attendances.get(selectedNumber - 1);
                Instant date = Instant.parse(selectedAttendance);
                modifyAttendaneStu(sectionID, date);
                return;
            }
        }

        public void modifyStudentAttendanceStatus(String teacherID) throws Exception {
            String prompt = "Please enter the section ID of the section you want to modify student attendance status.\n";
            String sectionID = facultyChooseFromSection(teacherID, prompt);
            modifyAttendane(sectionID);
        }

        public String facultyChooseFromSection(String teacherID, String prompt) throws Exception {
            out.println(prompt);
            String sectionID = in.readLine();
            SectionDAO sectionDAO = new SectionDAOImpl();
            while (true) {
                if (sectionDAO.isTeacherHasThatSection(teacherID, sectionID)) {
                    return sectionID;
                } else {
                    out.println("Invalid section ID. Please try again.\n");
                    sectionID = in.readLine();
                }
            }
        }

        public void enrollStudentToCourse(String teacherID) throws Exception {

            RosterDAO rosterDAO = new RosterDAOImpl();
            UserDAO userDAO = new UserDAOImpl();

            String prompt = "Please enter the section ID of the section you want to enroll student.\n";
            String sectionID = facultyChooseFromSection(teacherID, prompt);
            out.println("Please enter the netID of the student you want to enroll.\n");
            while (true) {
                String studentID = in.readLine();
                if (userDAO.isStudent(studentID)) {
                    out.println("Invalid student ID. Please try again.\n");
                    continue;
                }
                if (rosterDAO.isStudentInSection(studentID, sectionID)) {
                    out.println("Student already enrolled in the course.\n");
                    return;
                }
                rosterDAO.addStudentToSection(studentID, sectionID);
                return;
            }
        }

        public void dropStudentFromCourse(String teacherID) throws Exception {
            RosterDAO rosterDAO = new RosterDAOImpl();
            UserDAO userDAO = new UserDAOImpl();

            String prompt = "Please enter the section ID of the section you want to drop student.\n";
            String sectionID = facultyChooseFromSection(teacherID, prompt);
            out.println("Please enter the netID of the student you want to drop.\n");
            while (true) {
                String studentID = in.readLine();
                if (userDAO.isStudent(studentID)) {
                    out.println("Invalid student ID. Please try again.\n");
                    continue;
                }
                if (!rosterDAO.isStudentInSection(studentID, sectionID)) {
                    out.println("Student not enrolled in the course.\n");
                    return;
                }
                rosterDAO.removeStudentFromSection(studentID, sectionID);
                return;
            }
        }

        // need to modify
        public void exportAttendanceRecord(String teacherID) throws Exception {
            String prompt = "Please enter the section ID of the section you want to export attendance record.\n";
            String sectionID = facultyChooseFromSection(teacherID, prompt);
            AttendanceDAO attendanceDAO = new AttendanceDAOImpl();
            String attendances = attendanceDAO.getAttendanceStuScoreInSec(sectionID);
            out.println(attendances);
        }

        public void facultyHandler(String teacherID) throws Exception {
            while (true) {
                out.println(
                        "Hello professor! What do you want to do?\n1. Choose a new section to teach\n2. Start a new attendance\n3. Modify student attendance status\n4. Enroll new student to the section\n5. Drop student from the section\n6. Export attendance record to json file\n7. Logout\n");
                String choice = in.readLine();
                switch (choice) {
                    case "1":
                        facultyChooseSection(teacherID);
                        break;
                    case "2":
                        startnewCourse(teacherID);
                        break;
                    case "3":
                        modifyStudentAttendanceStatus(teacherID);
                        break;
                    case "4":
                        recordPreviousAttendance(teacherID);
                        break;
                    // case "5":
                    // dropStudentFromCourse(teacherID);
                    // break;
                    case "5":
                        exportAttendanceRecord(teacherID);
                        break;
                    case "6":
                        return;
                    default:
                        out.println("Invalid input. Please try again.\n");
                }

            }
        }

        public void viewAttendanceRecord(String studentID) {
            AttendanceDAO attendanceDAO = new AttendanceDAOImpl();
            String attendances = attendanceDAO.getAttendanceStuScore(studentID);
            out.println(attendances);
        }

        public void changeNotificationPreferences(String studentID) throws Exception {
            out.println("Do you want to turn on or off email notifications?\n");
            out.println("1. on\n2. off\n");
            String choice = in.readLine();
            while (!choice.equals("1") && !choice.equals("2")) {
                out.println("Invalid input. Please try again.\n");
                choice = in.readLine();
            }
            UserDAO userDAO = new UserDAOImpl();
            userDAO.changeNotificationPreference(studentID, choice);
        }

        public void studentHandler(String studentID) throws Exception {
            while (true) {
                out.println(
                        "Hello student! What do you want to do?\n1. View attendance record\n2.Change notification preferences\n3. Logout\n");
                String choice = in.readLine();
                switch (choice) {
                    case "1":
                        viewAttendanceRecord(studentID);
                        break;
                    case "2":
                        changeNotificationPreferences(studentID);
                        break;
                    case "3":
                        return;
                    default:
                        out.println("Invalid input. Please try again.\n");
                }
            }
        }

        public String login() throws Exception {
            while (true) {
                out.println("Please enter your NetID.\n");
                String netID = in.readLine();
                System.out.println(netID);
                out.println("Please enter your password.\n");
                String password = in.readLine();
                System.out.println(password);
                // login function
                UserDAO userDAO = new UserDAOImpl();
                int returnValue = userDAO.userLogin(netID, password);
                if (returnValue == 0 || returnValue == 1) {
                    System.out.println("Login successful.\n");
                    if (returnValue == 0) {
                        out.println("Welcome, student.\n");
                        return netID;
                    } else if (returnValue == 1) {
                        out.println("Welcome, professor.\n");
                        return netID;
                    }
                } else {
                    out.println("Login failed. Please try again.\n");
                }
            }

        }

        private void cleanup() {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
                System.out.println("Connection with client closed.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void startServer() throws Exception {

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {

                socket = serverSocket.accept();
                System.out.println("New client connected");

                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                new ClientHandler(socket, in, out).start();

                // socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopServer() throws Exception {
        in.close();
        out.close();
        socket.close();
    }

    public String receiveMessage() throws Exception {
        return in.readLine();
    }

    public void sendMessage(String msg) throws Exception {
        out.println(msg);
    }
}
