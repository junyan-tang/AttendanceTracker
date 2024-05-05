package edu.duke.ece651.team4.TrackerServer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.duke.ece651.team4.TrackerServer.dto.StudentCheckinDto;
import edu.duke.ece651.team4.TrackerServer.service.Location;
import edu.duke.ece651.team4.TrackerServer.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/checkin")
    public ResponseEntity<String> checkin(@RequestBody StudentCheckinDto request) {
        boolean checkin = studentService.checkin(request.getSectionId(), request.getStudentId(), new Location(request.getLatitude(), request.getLongitude()));
        if (checkin) {
            System.out.println("Check-in successful");
            return ResponseEntity.ok("Check-in successfully");
        } else {
            System.out.println("Check-in failed");
            return ResponseEntity.badRequest().body("Failed to check-in. Check-in not started, out of range, or exceeded time limit.");
        }
    }

    @PostMapping("/report")
    public ResponseEntity<String> report(Authentication authentication) {
        String netid = (String) authentication.getPrincipal();
        boolean report = studentService.report(netid);
        return ResponseEntity.ok("Report successfully");
        // if (report) {
        //     System.out.println("Report successful");
        //     return ResponseEntity.ok("Report successfully");
        // } else {
        //     System.out.println("Report failed");
        //     return ResponseEntity.badRequest().body("Failed to report. Check-in not started, out of range, or exceeded time limit.");
        // }
    }

}
