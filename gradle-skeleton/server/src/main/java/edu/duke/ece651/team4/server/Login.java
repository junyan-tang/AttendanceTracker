package edu.duke.ece651.team4.server;

public class Login {
    private final String username;
    private final String password;

    public Login(String user, String password) {
        this.username = user;
        this.password = password;
    }
    public String getUser() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }
}