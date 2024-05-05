package edu.duke.ece651.team4.TrackerServer.dto;

public class StartCheckinRequestDto {
    private String sectionId;
    private double latitude;
    private double longitude;
    // private double radius;
    // private String signInTime;

    public StartCheckinRequestDto() {
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // public double getRadius() {
    //     return radius;
    // }

    // public void setRadius(double radius) {
    //     this.radius = radius;
    // }

    // public String getSignInTime() {
    //     return signInTime;
    // }

    // public void setSignInTime(String signInTime) {
    //     this.signInTime = signInTime;
    // }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }
    
}

