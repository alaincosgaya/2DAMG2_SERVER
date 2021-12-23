/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cifrado;

import static com.mchange.v2.c3p0.impl.C3P0Defaults.user;
import static java.awt.SystemColor.text;
import java.util.Properties;
import javax.enterprise.inject.New;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Idoia Ormaetxea
 */
public class Mail {

    String smtp_host  = "smtp.gmail.com";
    String smtp_port  = "25";
    String sender = "";
    String receiver = "";
    String subject = "";
    String user="lauserrig2@gmail.com";
    String pass="abcd*1234";

    public void enviarEmail() throws MessagingException {
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", smtp_host);
        properties.put("mail.smtp.port", smtp_port);
        properties.put("mail.smtp.ssl.trust", smtp_host);
        properties.put("mail.imap.partialfetch", false);

        Session session = Session.getInstance(properties, new Authenticator() {

            //@Override
            protected PasswordAuthentication getPasswordAuthenticator() {
                return new PasswordAuthentication(user, pass);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sender));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
        message.setSubject(subject);

        Multipart multipart = new MimeMultipart();

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(text, "text/html");
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);
    }

}
