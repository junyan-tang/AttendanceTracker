package edu.duke.ece651.team4.TrackerServer.service;

import java.time.Instant;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CheckinScheduler {
    private final CheckinService checkinService;

    @Autowired
    public CheckinScheduler(CheckinService checkinService) {
        this.checkinService = checkinService;
    }

    @Scheduled(fixedRate = 30000) // 30 seconds
    public void checkSigninExpiration() {
        Instant currentTime = Instant.now();
        Map<String, CourseCheckinSettings> courseSettingsMap = checkinService.getCourseSettingsMap();
        
        for (Map.Entry<String, CourseCheckinSettings> entry : courseSettingsMap.entrySet()) {
            String sectionId = entry.getKey();
            CourseCheckinSettings settings = entry.getValue();
            Instant signInTime = settings.getSignInTime();

            if (currentTime.isAfter(signInTime) && settings.isOpen()) {
                settings.setOpen(false);
                System.out.println("Section " + sectionId + " check-in is closed.");
                //set all students not checked in to absent
                checkinService.setAbsent(sectionId, signInTime);
            }
        }
    }

}
