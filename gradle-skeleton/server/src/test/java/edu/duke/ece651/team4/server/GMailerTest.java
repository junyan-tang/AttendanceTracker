package edu.duke.ece651.team4.server;

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

    // @Mock
    // private Gmail service;
    // @Mock
    // private Gmail.Users users;
    // @Mock
    // private Gmail.Users.Messages messages;
    // @Mock
    // private Gmail.Users.Messages.Send sendRequest;

    // private GMailer gMailer;

    // @Before
    // public void setUp() throws Exception {
    //     MockitoAnnotations.openMocks(this);
    //     this.gMailer = new GMailer(); // This still assumes GMailer can accept a mock service

    //     // Correctly chain mocks to avoid NullPointerException
    //     when(service.users()).thenReturn(users);
    //     when(users.messages()).thenReturn(messages);
    //     when(messages.send(anyString(), any(Message.class))).thenReturn(sendRequest);
    // }

    // @Test(expected = GoogleJsonResponseException.class)
    // public void testSendMailThrowsExceptionOn403Error() throws Exception {
    //     // Assuming you have a way to simulate or mock GoogleJsonResponseException being thrown
    //     // For example, using doThrow() for a mocked method:
    //     doThrow(new GoogleJsonResponseException(new MockHttpResponse(), new GoogleJsonError()))
    //             .when(sendRequest).execute();

    //     gMailer.sendMail("test@example.com", "Test Subject", "Test message");
    // }
}

