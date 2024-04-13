package edu.duke.ece651.team4.server;

public enum AttendanceStatus {
  PRESENT("P"), 
  ABSENT("A"), 
  TARDY("T"), 
  EXCUSED("E"), 
  DEFAULT("D");

    private final String shortCode;

    private AttendanceStatus(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getShortCode() {
        return this.shortCode;
    }

    public static AttendanceStatus fromShortCode(String shortCode) {
      for (AttendanceStatus status : AttendanceStatus.values()) {
          if (status.getShortCode().equals(shortCode)) {
              return status;
          }
      }
      throw new IllegalArgumentException("No direction found with shortCode: " + shortCode);
    }
}
