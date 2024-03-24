package edu.duke.ece651.team4.server;

import org.junit.jupiter.api.Test;

// import static org.mockito.Mockito.*;

// import javax.mail.Message;

// import org.junit.Before;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;

// import com.google.api.client.googleapis.json.GoogleJsonError;
// import com.google.api.client.googleapis.json.GoogleJsonResponseException;
// import com.google.api.services.gmail.Gmail;

public class GMailerTest {
    @Test
    void testSendMail() throws Exception{
        new GMailer().sendMail("attendancefor651@gmail.com","A new message", "Dear reader, you succeed.");
    }
}

