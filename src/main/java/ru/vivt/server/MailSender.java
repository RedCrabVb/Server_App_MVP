package ru.vivt.server;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;

public class MailSender {
    private final Log logger = LogFactory.getLog(getClass());

    private final Session session;
    private final String emailServer;

    public MailSender(String username, String password) {
        emailServer = username;

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.mail.ru");
        props.put("mail.smtp.port", "587");

        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
        });
    }

    //header - Password recovery
    //body - "Your new password %s, <a href="%s">click here</a> to reset your old password"
    public boolean sendMessage(String recipient, String header, String body) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailServer));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipient));
            message.setSubject(header);
            message.setContent(body, "text/html");

            Transport.send(message);

            logger.info("Send message to email " + recipient);
            return true;
        } catch (MessagingException e) {
            logger.error("Error send message to email", e);
            return false;
        }
    }
}
