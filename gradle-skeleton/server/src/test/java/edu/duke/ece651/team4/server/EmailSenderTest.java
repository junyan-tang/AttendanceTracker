package edu.duke.ece651.team4.server;

import org.junit.jupiter.api.Test;

public class EmailSenderTest {
    @Test
    void testGenerateMessage() {

    }

    @Test
    void testSendMessage() {
        MessageSender messageSender = new EmailSender();
        //messageSender.sendMessage("example@example.com", "Hello, this is a test message.");
        //messageSender.sendMessage("invalidemail", "This message should not be sent.");
    }
}
