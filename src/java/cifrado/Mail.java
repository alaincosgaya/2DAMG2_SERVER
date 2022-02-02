package cifrado;

import java.util.Properties;
import java.util.ResourceBundle;
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
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Idoia Ormaetxea
 */
public class Mail {

    private final static String SMTP_HOST = "smtp.gmail.com";
    private final static String SMTP_PORT = "587";//25,465,587
    //private final static String MAIL_FROM_USER = "lauserrig2@gmail.com";//email desde el que se envía el correo. Tienes que crearlo y configurar la cuenta de acuerdo al documento que subieron de PSP
    //private final static String MAIL_PASS = "abcd*1234";//contraseña del email que te has creado
    //private final static String MAIL_TO_USER = "idoia.ormaetxea2000@gmail.com";//email al que vas a enviar en correo
    private final static String SUBJECT = "Prueba email Java";
    //private final static String MESSAGE_WITH_FORMAT = "<p>Estimado usuario,<br/><br/>Recientemente ha solicitado un reseteo de su contraseña.</p><br/><p>Su nueva contraseña es: %s</p><br/><p>Un saludo,<br/><br/>Administrador JavaGaming.</p>";
    private final static String MESSAGE_WITH_FORMAT ="<p>%s</p>";
    private static final ResourceBundle configFile = ResourceBundle.getBundle("cifrado.email");
    
    private static final String user = configFile.getString("USER");
    private static final String password = configFile.getString("CON");
    //Mail properties
    public static void sendEmail(String mail,String genPassword ){
        String MAIL_TO_USER = mail;
        Cifrado cifrado = new Cifrado();

        //Pasar de hexadecimal a byte
        byte[] usr = DatatypeConverter.parseHexBinary(user);
        byte[] pass = DatatypeConverter.parseHexBinary(password);
        //desencriptar
        byte[] usr2 = cifrado.descifrarTexto(usr);
        byte[] pass2 = cifrado.descifrarTexto(pass);
        //convertir byte to String para usarla 
        String MAIL_FROM_USER = new String(usr2);
        String MAIL_PASS = new String(pass2);
        
        
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
    /*
    public static void main(String[] args){
        String MAIL_TO_USER = "idoia.ormaetxea2000@gmail.com";
        sendEmail(MAIL_TO_USER);
    }
*/
}