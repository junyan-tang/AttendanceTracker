package edu.duke.ece651.team4.shared;

public class Professor extends User {

  public Professor(String netid, String firstName, String lastName, String preferredName, String password, String identity, String email) {
    super(netid, firstName, lastName, preferredName, password, identity, email);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Professor other = (Professor) obj;
    return (netid.equals(other.netid) && firstName.equals(other.firstName) && lastName.equals(other.lastName) &&
            password.equals(other.password) && preferredName.equals(other.preferredName) &&
            identity.equals(other.identity) && email.equals(other.email));
  } 
}
