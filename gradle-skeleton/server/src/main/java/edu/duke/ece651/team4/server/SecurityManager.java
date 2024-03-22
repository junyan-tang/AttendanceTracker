package edu.duke.ece651.team4.server;

public abstract class SecurityManager {
    public abstract Boolean checkUserExist(String netid);
    public String hashPassword(String password){
        return password;
    }
}
