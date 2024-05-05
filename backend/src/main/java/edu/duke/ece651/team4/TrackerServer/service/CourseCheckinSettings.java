package edu.duke.ece651.team4.TrackerServer.service;

import java.time.Instant;

public class CourseCheckinSettings {
    private Location location;
    private double radius;
    private Instant signInTime;
    private boolean isOpen;

    public double getRadius() {
        return radius;
    }
    public void setRadius(double radius) {
        this.radius = radius;
    }
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
    
    public Instant getSignInTime() {
        return signInTime;
    }
    public void setSignInTime(Instant signInTime) {
        this.signInTime = signInTime;
    }
    public boolean isOpen() {
        return isOpen;
    }
    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    
}
