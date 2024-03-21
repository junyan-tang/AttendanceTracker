package edu.duke.ece651.team4.server;

public class Login {
    private final User user;
    private final String password;

    public Login(User user, String password) {
        this.user = user;
        this.password = password;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}