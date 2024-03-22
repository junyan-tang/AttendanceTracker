package edu.duke.ece651.team4.server;

public class Account {
    private final String netid;
    private final String password;

    public Account(String netid, String password){
        this.netid = netid;
        this.password = password;
    }

    public String getNetid() {
        return this.netid;
    }
}
