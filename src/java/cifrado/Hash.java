/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cifrado;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 2dam
 */
public class Hash {
    
    public String cifrarTexto(String texto){
        String hash="";
        try {
            //
            System.out.println(texto+" cifrarTexto hash");
            MessageDigest messageDigest = null;
            messageDigest = messageDigest.getInstance("SHA-256");
            messageDigest.update(texto.getBytes());
            byte[] digest = messageDigest.digest();
            //
            System.out.println(new String(digest)+" digest hash");
            hash = new String(digest);
            //
            System.out.println(hash +" hash hash");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Hash.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hash;
    }
}
