package edu.duke.ece651.team4.server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EmailSender implements MessageSender{
    private GMailer gmailsender;
    private static final String subject = "test Mail";
    
    public EmailSender() throws Exception{
        this.gmailsender = new GMailer();
    }

    public String generateMessage() {
        return null;
    }

    // public void setGmailer(GMailer gmailer){
    //     this.gmailsender = gmailer;
    // }

    public boolean sendMessage(String destination, String message)throws Exception {
        if (!isValidEmail(destination)) {
            System.out.println("Invalid email address");
            return false;
        }
        System.out.println("Sending message to " + destination + ": " + message);
        // send email
        gmailsender.sendMail(destination, subject, message);
        // finish sending email
        return true;
    }

    private boolean isValidEmail(String email) {

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}
