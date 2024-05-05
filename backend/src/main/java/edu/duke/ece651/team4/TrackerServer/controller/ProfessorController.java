package edu.duke.ece651.team4.TrackerServer.controller;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.duke.ece651.team4.TrackerServer.dto.ProfessorReportDto;
import edu.duke.ece651.team4.TrackerServer.dto.StartCheckinRequestDto;
import edu.duke.ece651.team4.TrackerServer.service.Location;
import edu.duke.ece651.team4.TrackerServer.service.ProfessorService;

@RestController
@RequestMapping("/professor")
public class ProfessorController {
    private final ProfessorService professorService;
    private final double RADIUS = 100.0; // checkin radius
    private final int CHECKIN_DURATION = 3; // checkin duration in minutes

    @Autowired
    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @PostMapping("/startCheckin")
    public ResponseEntity<String> startCheckin(@RequestBody StartCheckinRequestDto request) {

        // Instant signInTime = Instant.parse(request.getSignInTime());
        Instant signInTime = Instant.now().plus(CHECKIN_DURATION, ChronoUnit.MINUTES);

        System.out.println("sectionId: " + request.getSectionId() + " latitude: " + request.getLatitude()
                + " longitude: " + request.getLongitude());

        boolean checkinStarted = professorService.startCheckin(request.getSectionId(),
                new Location(request.getLatitude(), request.getLongitude()),
                RADIUS,
                signInTime);
        if (checkinStarted) {
            return ResponseEntity.ok("Check-in started successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to start check-in. Check-in already started for the day.");
        }
    }

    @PostMapping("/report")
    public ResponseEntity<String> report(Authentication authentication, @RequestBody ProfessorReportDto request) {
        String netid = (String) authentication.getPrincipal();
        boolean report = professorService.report(netid, request.getSectionId());
        return ResponseEntity.ok("Report successfully");
        // if (report) {
        //     return ResponseEntity.ok("Report successfully");
        // } else {
        //     return ResponseEntity.badRequest().body("Failed to report.");
        // }
    }

    

    // private Instant parseDate(String date_){
    // try{
    // LocalDate date = LocalDate.parse(date_);
    // return date.atStartOfDay(ZoneId.of("America/New_York")).toInstant();
    // }catch(Exception e){
    // return null;
    // }
    // }
    // private final StudentService studentService;

    // @GetMapping
    // public List<Student> getAllStudents() {
    // return studentService.getAllStudents();
    // }

    // @PostMapping
    // public void addStudent(@RequestBody Student student) {
    // studentService.addStudent(student);
    // }
}
