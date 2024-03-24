package edu.duke.ece651.team4.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class RosterViewTest {
    @Test
    public void test_AttendanceView() {
        Student s1 = new Student("123-456-7890", "Brian", "Brian", "exapmle@duke.edu");
        Student s2 = new Student("123-456-7890", "Afsaneh", "Afsaneh", "test@duke.edu");
        List<User> students = new ArrayList<User>();
        students.add(s1);
        students.add(s2);

        Attendance test = new Attendance(551, "C++ Programming", students);
        RosterView testView = new RosterView(test);
        String ans1 = testView.makeHeader();
        String expected1 = "     551:C++ Programming     "+test.getCourseDateStr()+"\n";
        assertEquals(expected1, ans1);

        String ans2 = testView.displayRoster();
        String expected2 = "     551:C++ Programming     "+test.getCourseDateStr()+"\n" 
                          +"Brian          \nAfsaneh          \n";
        assertEquals(expected2, ans2);
        AttendanceStatus PRESENT = AttendanceStatus.PRESENT;
        test.recordAttendance(s2, PRESENT, "");
        String ans3 = testView.displayRoster();
        String expected3 = "     551:C++ Programming     "+test.getCourseDateStr()+"\n" 
                          +"Brian          \nAfsaneh     PRESENT     \n";
        assertEquals(ans3, expected3);
    }
    
}
