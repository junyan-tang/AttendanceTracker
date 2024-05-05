package edu.duke.ece651.team4.TrackerServer.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.duke.ece651.team4.TrackerServer.entity.Attendance;

@Service
public class CheckinService {

    private final Map<String, CourseCheckinSettings> courseSettingsMap = new HashMap<>();
    private AttendanceService attendanceService;
    private SectionService sectionService;
    private UserService userService;
    private EmailSender emailSender;
    private RosterService rosterService;

    @Autowired
    public CheckinService(AttendanceService attendanceService, SectionService sectionService, UserService userService,
            EmailSender emailSender, RosterService rosterService) {
        this.attendanceService = attendanceService;
        this.sectionService = sectionService;
        this.userService = userService;
        this.emailSender = emailSender;
        this.rosterService = rosterService;
    }

    public boolean setCheckinSettings(String sectionId, Location location, double radius, Instant signInTime) {

        CourseCheckinSettings settings = courseSettingsMap.get(sectionId);

        // 如果courseSettingsMap已经有这个键
        if (settings != null) {
            // 获取已存在设置的签到时间
            Instant existingSignInTime = settings.getSignInTime();

            // 判断日期是否相同
            if (isSameDay(existingSignInTime, signInTime)) {
                // 如果日期相同，则返回false
                return false;
            } else {
                // 如果日期不同，则更新值
                settings.setLocation(location);
                settings.setRadius(radius);
                settings.setSignInTime(signInTime);
                settings.setOpen(true);
            }
        } else {
            // 如果没有这个键，则放入新的键值对
            settings = new CourseCheckinSettings();
            settings.setLocation(location);
            settings.setRadius(radius);
            settings.setSignInTime(signInTime);
            settings.setOpen(true);
            courseSettingsMap.put(sectionId, settings);
        }

        // 返回true表示设置成功
        return true;
    }
    // CourseCheckinSettings settings = new CourseCheckinSettings();
    // settings.setLocation(location);
    // settings.setRadius(radius);
    // settings.setSignInTime(signInTime); // end time
    // settings.setOpen(true); // default: open

    // courseSettingsMap.put(sectionId, settings);
    // }

    private boolean isSameDay(Instant instant1, Instant instant2) {
        LocalDate date1 = instant1.atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate date2 = instant2.atZone(ZoneId.systemDefault()).toLocalDate();
        return date1.equals(date2);
    }

    public Map<String, CourseCheckinSettings> getCourseSettingsMap() {
        return courseSettingsMap;
    }

    public boolean checkinStatus(String sectionId, String studentId, Location studentLocation, Instant currentTime) {

        CourseCheckinSettings settings = courseSettingsMap.get(sectionId);
        if (settings == null || !settings.isOpen()) {
            // return "Check-in is not open.";
            return false;
        }

        double studentLat = studentLocation.getLatitude();
        double studentLon = studentLocation.getLongitude();

        double professorLat = settings.getLocation().getLatitude();
        double professorLon = settings.getLocation().getLongitude();

        // calculate distance between student and professor
        double distance = calculateDistance(studentLat, studentLon, professorLat, professorLon);

        double radius = settings.getRadius();
        if (distance > radius) {
            //return "Out of check-in range. Distance: " + distance + " meters.";
            return false;
        }

        // current time is instant to seconds
        Attendance attendance = attendanceService.getAttendance(studentId, sectionId,
                currentTime.truncatedTo(ChronoUnit.DAYS));

        if (attendance != null) {
            //return "Already checked in.";
            return true;
        }

        addNewAttendance(sectionId, studentId, currentTime.truncatedTo(ChronoUnit.DAYS), "PRESENT");

        //return "Check-in successful.";
        return true;
    }

    private void addNewAttendance(String sectionId, String studentId, Instant currentTime, String status) {
        Attendance attendance = new Attendance();
        attendance.setCourse(sectionService.getCourse(sectionId));
        attendance.setSection(sectionService.getSection(sectionId));
        attendance.setStudent(userService.getUser(studentId));
        attendance.setAttendanceTime(currentTime.truncatedTo(ChronoUnit.DAYS));
        attendance.setStatus(status);
        attendanceService.addAttendance(attendance);
        try {
            emailSender.sendMessage(userService.getUser(studentId).getEmail(), "Check-in successful",
                    "You have successfully checked in to the course." + attendance.getCourse().getCourseName()
                            + " section " + attendance.getSection().getSectionID() + " at "
                            + attendance.getAttendanceTime());
        } catch (Exception e) {
            System.out.println("Email sending failed");
        }
    }

    // calculate distance between two points
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371; // earth radius in km

        // to radians
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        // calculate distance using Haversine formula
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                        * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        return distance;
    }

    public void setAbsent(String sectionId, Instant signInTime) {
        Instant storedTime = signInTime.truncatedTo(ChronoUnit.DAYS);
        for (String studentId : rosterService.getStudentsInSection(sectionId)) {
            if (attendanceService.getAttendance(studentId, sectionId, storedTime) == null) {
                addNewAttendance(sectionId, studentId, storedTime, "ABSENT");
            }
        }
    }
}