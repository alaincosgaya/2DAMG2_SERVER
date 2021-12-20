package cifrado;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Idoia Ormaetxea y Jonathan Camacho
 */
public class Hash {
    
    public String cifrarTexto(String texto){
        String hash="";
        try {
            MessageDigest messageDigest = null;
            messageDigest = messageDigest.getInstance("SHA-256");
            messageDigest.update(texto.getBytes());
            byte[] digest = messageDigest.digest();
            hash = new String(digest);
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Hash.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hash;
    }
}
