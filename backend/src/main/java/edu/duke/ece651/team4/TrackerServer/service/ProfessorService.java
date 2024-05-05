package edu.duke.ece651.team4.TrackerServer.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfessorService {
    private final CheckinService checkinService;
    private final AttendanceService attendanceService;
    private final EmailSender emailSender;
    private final UserService userService;

    @Autowired
    public ProfessorService(CheckinService checkinService, AttendanceService attendanceService, EmailSender emailSender,
            UserService userService) {
        this.checkinService = checkinService;
        this.attendanceService = attendanceService;
        this.emailSender = emailSender;
        this.userService = userService;
    }

    public boolean startCheckin(String sectionId, Location location, double radius, Instant signInTime) {
        return checkinService.setCheckinSettings(sectionId, location, radius, signInTime);
    }

    public boolean report(String netid, String sectionId) {
        String message = attendanceService.getAttendanceProScore(sectionId);
        try{
            System.out.println(message);
            emailSender.sendMessage(userService.getUser(netid).getEmail(),"Your report for attendance",message);
        }catch(Exception e){
            return false;
        }
        return true;
    }
}
