package edu.duke.ece651.team4.server;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RegisterTest {
    @Test
    public void test_register() {
        AccountManager acm = new AccountManager();
        RegisterChecker rc = new RegisterChecker(acm);
        Boolean ans = rc.tryAddAccount("user1", "password1");
        assertEquals(ans, true);
        LoginChecker lc = new LoginChecker(acm);
        Boolean ans1 = lc.checkPassword("user1", "password1");
        assertEquals(ans1, true);
    }
    
}
