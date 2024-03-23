package edu.duke.ece651.team4.server;
import java.util.HashMap;

public class AccountManager {
    private HashMap<String, Account> accounts;
    public AccountManager() {
        this.accounts = new HashMap<String, Account>();
    }
    public Account getAccount(String netid) {
        return this.accounts.get(netid);
    }
    public void addAccount(Account account) {
        this.accounts.put(account.getNetid(), account);
    }
}
