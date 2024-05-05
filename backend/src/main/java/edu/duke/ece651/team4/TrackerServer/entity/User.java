package edu.duke.ece651.team4.TrackerServer.entity;

import jakarta.persistence.*;
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(length = 20)
    private String netid;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "preferred_name", length = 100)
    private String preferredName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    private String identity;

    @Column(nullable = false)
    private String email;

    @Column(length = 50)
    private Boolean notifyChoice;

    public User(){}

    public User(String netid, String firstName, String lastName, String preferredName, String password, String identity, String email) {
        this.netid = netid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.preferredName = preferredName;
        this.password = password;
        this.identity = identity;
        this.email = email;
        this.notifyChoice = false;
    }
    
    public Boolean getNotifyChoice() {
        return notifyChoice;
    }

    public void setNotifyChoice(Boolean notifyChoice) {
        this.notifyChoice = notifyChoice;
    }

    public String getNetid() {
        return netid;
    }
    public void setNetid(String netid) {
        this.netid = netid;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getPreferredName() {
        return preferredName;
    }
    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getIdentity() {
        return identity;
    }
    public void setIdentity(String identity) {
        this.identity = identity;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
