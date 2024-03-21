package edu.duke.ece651.team4.server;

public interface SecurityManager {
    public Boolean checkPassword(String password);
    public Boolean checkUserExist(String email);
}
