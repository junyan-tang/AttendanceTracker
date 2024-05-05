package edu.duke.ece651.team4.TrackerServer.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.duke.ece651.team4.TrackerServer.dto.CourseSelectionDto;
import edu.duke.ece651.team4.TrackerServer.dto.UserProfileDto;
import edu.duke.ece651.team4.TrackerServer.entity.Section;
import edu.duke.ece651.team4.TrackerServer.entity.User;
import edu.duke.ece651.team4.TrackerServer.service.SectionService;
import edu.duke.ece651.team4.TrackerServer.service.UserService;

@RestController
public class UserController {
    private final UserService userService;
    private final SectionService  sectionService;

    @Autowired
    public UserController(UserService userService, SectionService sectionService) {
        this.userService = userService;
        this.sectionService = sectionService;
    }

    @GetMapping("/profile")
    public UserProfileDto getProfile(Authentication authentication) {
        UserProfileDto userProfile = new UserProfileDto();
        String netid = (String) authentication.getPrincipal();
        User user = userService.getUser(netid);
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String fullName = firstName + " " + lastName;
        System.out.println("Full Name: " + fullName);
        System.out.println("Email: " + user.getEmail());
        userProfile.setFullName(fullName);
        userProfile.setEmail(user.getEmail());
        userProfile.setPreferName(user.getPreferredName());
        userProfile.setIdentity(user.getIdentity());
        userProfile.setNetid(netid);
        userProfile.setNotifychoice(user.getNotifyChoice());
        return userProfile;
    }

    // @GetMapping("/courses")
    // public String getCourseAll(Authentication authentication) {
    //     System.out.println("Get courses");
    //     return "Hello";
    // }

    @PostMapping("/profile")
    public void updateProfile(Authentication authentication, @RequestBody UserProfileDto userProfileDto) {
        String netid = (String) authentication.getPrincipal();
        User user = userService.getUser(netid);
        user.setNotifyChoice(userProfileDto.getNotifychoice());
        userService.updateUser(user);
    }

    // @PostMapping("/profile/changenotify")
    // public void changeNotify(Authentication authentication, @RequestBody UserProfileDto userProfileDto) {
    //     String netid = (String) authentication.getPrincipal();
    //     User user = userService.getUser(netid);
    //     user.setNotifyChoice(userProfileDto.getNotifychoice());
    //     userService.updateUser(user);
    // }
}
