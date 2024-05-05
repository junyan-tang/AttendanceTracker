package edu.duke.ece651.team4.TrackerServer.service;

import java.util.List;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.duke.ece651.team4.TrackerServer.entity.User;
import edu.duke.ece651.team4.TrackerServer.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String netid) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername" + netid);
        User currUser = userRepository.findById(netid)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                currUser.getNetid(),
                currUser.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }


    public void createUser(User user){
        userRepository.save(user);
    }

    public void deleteUser(String netid){
        userRepository.deleteById(netid);
    }

    public void updateUser(User user){
        userRepository.save(user);
    }

    public User getUser(String netid){
        return userRepository.findById(netid).get();
    }

    public boolean isFaculty(String netid){
        User user = userRepository.findById(netid).get();
        if (user.getIdentity().equals("professor")){
            return true;
        }
        return false;
    }

    public boolean isStudent(String netid){
        User user = userRepository.findById(netid).get();
        if (user.getIdentity().equals("student")){
            return true;
        }
        return false;
    }

    public List<User> getAllProfessor(){
        return userRepository.findByIdentity("professor");
    }

    public List<User> getAllStudent(){
        return userRepository.findByIdentity("student");
    }

    public String getUsernameByID(String netid){
        User user = userRepository.findById(netid).get();
        return user.getFirstName() + " " + user.getLastName();
    }

    public void changeNotifyChoice(String netid, Boolean notifyChoice) {
        userRepository.updateNotifyChoice(netid, notifyChoice);
    }
}
