package edu.duke.ece651.team4.server;

public class RegisterSecurity implements SecurityManager{
    public Boolean checkPassword(String password) {
        return password.length() >= 8;
    }

    public Boolean checkUserExist(String email) {
        return false;
    }

    public String hashPassword(String password) {
        return password;
    }
}
