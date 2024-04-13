package edu.duke.ece651.team4.server;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class SecurityManager {
    public abstract Boolean checkUserExist(String netid);
    public String hashPassword(String password){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            StringBuilder hashedpwd = new StringBuilder();
            for (int i = 0; i < digest.length; i++) {
                hashedpwd.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
            }
            return hashedpwd.toString();
        } catch (NoSuchAlgorithmException e) {
            return password;
        }
    }
}
