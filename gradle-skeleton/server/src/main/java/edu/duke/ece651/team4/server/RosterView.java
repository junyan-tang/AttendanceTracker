package edu.duke.ece651.team4.server;
import java.util.List;

public class RosterView {
    private final Attendance toDisplay;

    public RosterView(Attendance toDisplay) {
        this.toDisplay = toDisplay;
    }

    public String displayRoster() {
        StringBuilder view = new StringBuilder("");
        view.append(makeHeader());
        List<User> studentList = toDisplay.getStudentList();
        for(User student:studentList) {
            view.append(student.getName());
            view.append("     ");
            String displayStatus = toDisplay.getAttendanceStatus(student).toString();
            if(displayStatus == null) {
                view.append("     ");
            }
            else {
                view.append(displayStatus);
            }
            view.append("\n");
        }
        return view.toString();
    }

    public String makeHeader() {
        StringBuilder header = new StringBuilder("     ");
        header.append(toDisplay.getCourseId());
        header.append(":");
        header.append(toDisplay.getCourseName());
        header.append("     ");
        header.append(toDisplay.getCourseDateStr());
        header.append("\n");
        return header.toString();
    }
}
