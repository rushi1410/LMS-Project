package com.bridgelabz.adminservice.util;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.stereotype.Component;

@Component
public class Jms {
    public static void sendEmail(String toEmail, String subject, String body) {

        System.out.println(toEmail);
        System.out.println(subject);
        System.out.println(body);
        String fromEmail = System.getenv("email");
        System.out.println(fromEmail);
        String password = System.getenv("password");
        System.out.println(password);
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        Authenticator auth = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(prop, auth);

        send(session, fromEmail, toEmail, subject, body);
    }


    private static void send(Session session, String fromEmail, String toEmail, String subject, String body) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail,"siva"));
//			message.setFrom(new InternetAddress(fromEmail, body));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("exception occurred while sending mail");
        }
    }

}
