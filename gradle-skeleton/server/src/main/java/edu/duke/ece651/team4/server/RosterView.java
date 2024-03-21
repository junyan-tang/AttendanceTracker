package edu.duke.ece651.team4.server;
import java.util.List;
import java.util.HashMap;

public class RosterView {
    private final Attendance toDisplay;
    private final HashMap<AttendanceStatus, String> status;

    public RosterView(Attendance toDisplay) {
        this.toDisplay = toDisplay;
        this.status = new HashMap<>();
        status.put(AttendanceStatus.PRESENT, "PRESENT");
        status.put(AttendanceStatus.ABSENT, "ABSENT");
        status.put(AttendanceStatus.TARDY, "TARDY");
        status.put(AttendanceStatus.EXCUSED, "EXCUSED");
        status.put(AttendanceStatus.DEFAULT, "DEFAULT");
    }

    public String displayRoster() {
        StringBuilder view = new StringBuilder("");
        view.append(makeHeader());
        List<User> studentList = toDisplay.getStudentList();
        for(User student:studentList) {
            view.append(student.getName());
            view.append("     ");
            String displayStatus = status.get(toDisplay.getAttendanceStatus(student));
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
        header.append(toDisplay.getCourseDate());
        header.append("\n");
        return header.toString();
    }
}
