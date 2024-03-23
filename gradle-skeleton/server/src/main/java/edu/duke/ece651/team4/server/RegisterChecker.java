package edu.duke.ece651.team4.server;

public class RegisterChecker extends SecurityManager{
    AccountManager accountManager;
    public RegisterChecker(AccountManager acm){
        this.accountManager = acm;
    }
    public Boolean checkPassword(String password) {
        return password.length() >= 8;
    }

    public Boolean checkUserExist(String netid) {
        return true;
    }

    public Boolean tryAddAccount(String netid, String password) {
        if (checkUserExist(netid) == false) {
            return false;
        }
        String hashedPwd = hashPassword(password);
        accountManager.addAccount(new Account(netid, hashedPwd));
        return true;
    }
}
