package edu.duke.ece651.team4.TrackerServer.dto;

public class UserProfileDto {
    private String fullName;
    private String preferName;
    private String netid;
    private String email;
    private String identity;
    private Boolean notifychoice;
    public Boolean getNotifychoice() {
        return notifychoice;
    }
    public void setNotifychoice(Boolean notifychoice) {
        this.notifychoice = notifychoice;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getPreferName() {
        return preferName;
    }
    public void setPreferName(String preferName) {
        this.preferName = preferName;
    }
    public String getNetid() {
        return netid;
    }
    public void setNetid(String netid) {
        this.netid = netid;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getIdentity() {
        return identity;
    }
    public void setIdentity(String identity) {
        this.identity = identity;
    }
}
