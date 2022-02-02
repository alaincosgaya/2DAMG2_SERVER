/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cifrado;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Idoia Ormaetxea
 */
public class Mail {

    private final static String SMTP_HOST = "smtp.gmail.com";
    private final static String SMTP_PORT = "25";//25,465,587
    private final static String MAIL_FROM_USER = "lauserrig2@gmail.com";//email desde el que se envía el correo. Tienes que crearlo y configurar la cuenta de acuerdo al documento que subieron de PSP
    private final static String MAIL_PASS = "abcd*1234";//contraseña del email que te has creado
    private final static String MAIL_TO_USER = "idoia.ormaetxea2000@gmail.com";//email al que vas a enviar en correo
    private final static String SUBJECT = "Prueba email Java";
    //private final static String MESSAGE_WITH_FORMAT = "<p>Estimado usuario,<br/><br/>Recientemente ha solicitado un reseteo de su contraseña.</p><br/><p>Su nueva contraseña es: %s</p><br/><p>Un saludo,<br/><br/>Administrador JavaGaming.</p>";
    private final static String MESSAGE_WITH_FORMAT ="<p>%s</p>";
    //Mail properties
    public static void sendEmail() {
        String genPassword = "";
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);
        properties.put("mail.smtp.ssl.trust", true);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.imap.partialfetch", false);
        properties.put("mail.smtp.ssl.enable", false);

        //Nos autenticamos 
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MAIL_FROM_USER, MAIL_PASS);
            }
        });
        session.setDebug(true);
        //Mensaje MIME
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(MAIL_FROM_USER));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(MAIL_TO_USER));
            message.setSubject(SUBJECT);
            //un mail puede tener varias partes incluyendo un archivo
            MimeMultipart multiPart = new MimeMultipart();
            //parte de un mensaje
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            genPassword = Cifrado.randomPassword();
            String messageBody = String.format(MESSAGE_WITH_FORMAT, genPassword);
            mimeBodyPart.setContent(messageBody, "text/html");
            multiPart.addBodyPart(mimeBodyPart);
            //añadimos las partes al mensaje MIME
            message.setContent(multiPart);
            //enviamos el mensaje
            Transport.send(message);
        } catch (AddressException ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args){
        sendEmail();
    }

}
