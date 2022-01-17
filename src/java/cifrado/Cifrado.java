package cifrado;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Cifrado {

    private static byte[] salt = "esta es la salt!".getBytes();

   
    public static String generarContra() {
        //new KeyGenerator().generate();
        
        String mensaje = randomPassword();
        String hash = hashearMensaje(mensaje);
        //byte[] encrypt = encrypt(mensaje);
        //System.out.println("Encriptación del mensaje: ");
        //System.out.println(new String(encrypt));
        //byte[] decrypt = decrypt(encrypt);
        //System.out.println("Tu mensaje era: ");
        //System.out.println(new String(decrypt));

        System.out.println(hash+"main asimetrico");
        return hash;
    }
    
    public static String randomPassword() {
    int leftLimit = 48; // numero '0'
    int rightLimit = 90; // letra 'Z'
    int targetStringLength = 8;
    String generatedString;
    char[] number = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
    Random random = new Random();
    
    do{
    generatedString = random.ints(leftLimit, rightLimit + 1)
      .filter(i -> (i <= 57 || i >= 65))
      .limit(targetStringLength)
      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
      .toString();
    }while(generatedString.contains(Arrays.toString(number)));
    
    //byte[] generated = generatedString.getBytes();
    System.out.println(generatedString);
    return generatedString;
}

    public static byte[] encrypt(byte[] plaintext) {
        Cipher cipher;
        byte[] bs = null;
        PrivateKey key;
        try {
            key = readPrivateKey("./src/files/private.key");
            cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            bs = cipher.doFinal(plaintext);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException | InvalidKeySpecException ex) {
            Logger.getLogger(Cifrado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bs;

    }

    public static byte[] decrypt(byte[] ciphertext){
        Cipher cipher;
        byte[] bs = null;
        PrivateKey key;
        try {
            key = readPrivateKey("./src/files/private.key");
            cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            bs =  cipher.doFinal(ciphertext);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException | InvalidKeySpecException ex) {
            Logger.getLogger(Cifrado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bs;
    }

    public static PrivateKey readPrivateKey(String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(fileReader(filename));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    public static PublicKey readPublicKey(String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(fileReader(filename));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(publicSpec);
    }

    private static byte[] fileReader(String path) {
        byte ret[] = null;
        File file = new File(path);
        try {
            ret = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            Logger.getLogger(Cifrado.class.getName()).log(Level.SEVERE, null, e);
        }
        return ret;
    }

    private static String hashearMensaje(String mensaje) {
        Hash hash = new Hash();
        String hasheado = hash.cifrarTexto(mensaje);
        System.out.println(hasheado+"hashearmensaje asimetrico");
        return hasheado;
    }
}
