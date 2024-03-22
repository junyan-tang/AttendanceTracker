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
        String expected1 = "     551:C++ Programming     2024-03-21\n";
        assertEquals(expected1, ans1);

        String ans2 = testView.displayRoster();
        String expected2 = "     551:C++ Programming     2024-03-21\n" 
                          +"Brian     DEFAULT\nAfsaneh     DEFAULT\n";
        assertEquals(expected2, ans2);
    }
    
}
