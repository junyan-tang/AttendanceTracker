package edu.duke.ece651.team4.server;

import org.junit.jupiter.api.Test;

public class GMailerTest {
    @Test
    void testSendMail() throws Exception{
        new GMailer().sendMail("A new message", "Dear reader, you succeed.");
    }
}
