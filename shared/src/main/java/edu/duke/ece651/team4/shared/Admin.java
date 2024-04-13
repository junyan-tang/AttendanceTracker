package edu.duke.ece651.team4.shared;

public class Admin extends User {

  public Admin(String netid, String password, String identity) {
    super(netid, null, null, null, password, identity, null);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Admin other = (Admin) obj;
    return (netid.equals(other.netid) && password.equals(other.password) && identity.equals(other.identity));
  } 
}
