package edu.duke.ece651.team4.server;

public class LoginSecurity implements SecurityManager{
    public Boolean checkPassword(String password) {
        return false;
    }

    public Boolean checkUserExist(String email) {
        return true;
    }
}
