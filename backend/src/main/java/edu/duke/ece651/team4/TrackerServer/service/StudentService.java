package edu.duke.ece651.team4.TrackerServer.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private final CheckinService checkinService;
    private final AttendanceService attendanceService;
    private final EmailSender emailSender;
    private final UserService userService;

    @Autowired
    public StudentService(CheckinService checkinService, AttendanceService attendanceService, EmailSender emailSender,
            UserService userService) {
        this.checkinService = checkinService;
        this.attendanceService = attendanceService;
        this.emailSender = emailSender;
        this.userService = userService;
    }

    public boolean checkin(String sectionId, String studentId, Location location) {
        return checkinService.checkinStatus(sectionId, studentId, location, Instant.now());
    }

    public boolean report(String studentId) {
        String message = attendanceService.getAttendanceStuScore(studentId);
        try{
            System.out.println(message);
            emailSender.sendMessage(userService.getUser(studentId).getEmail(),"Your report for attendance",message);
        }catch(Exception e){
            return false;
        }
        return true;
    }
}
