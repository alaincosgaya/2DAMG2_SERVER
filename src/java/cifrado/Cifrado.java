package cifrado;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.launch;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import static javax.crypto.Cipher.DECRYPT_MODE;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class Cifrado {
    
    private static final ResourceBundle configFile = ResourceBundle.getBundle("cifrado.key");

    private static byte[] salt = "esta es la salt!".getBytes();
    private byte[] iv;
    private static String clave = "abcd1234";

    public static String generarContra() {
        String mensaje = randomPassword();
        return mensaje;
    }
     public String encriptarContra(String contra) {
        String hash = hashearMensaje(contra);
        return hash;
     }

    public static String randomPassword() {
        int leftLimit = 48; // numero '0'
        int rightLimit = 90; // letra 'Z'
        int targetStringLength = 8;
        String generatedString;
        char[] number = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        Random random = new Random();

        do {
            generatedString = random.ints(leftLimit, rightLimit + 1)
                    .filter(i -> (i <= 57 || i >= 65))
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
        } while (generatedString.contains(number.toString()));

        return generatedString;
    }

    private static String hashearMensaje(String mensaje) {
        Hash hash = new Hash();
        String hasheado = hash.cifrarTexto(mensaje);

        return hasheado;
    }

    
/*
    public static byte[] decrypt(byte[] ciphertext) {
        Cipher cipher;
        byte[] bs = null;
        PrivateKey key;
        try {
            key = readPrivateKey("./src/files/private.key");
            cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            bs = cipher.doFinal(ciphertext);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException | InvalidKeySpecException ex) {
            Logger.getLogger(Cifrado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bs;
    }*/
    
    public static String decrypt(String plaintext) {
        
        byte[] passwordContent = hexStringToByteArray(plaintext);
        byte[] bs = null;
        //PublicKey key;
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            //key = readPublicKey("./cifrado/public.key");
            
            cipher.init(Cipher.DECRYPT_MODE, readPrivateKey());
            bs = cipher.doFinal(passwordContent);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException | InvalidKeySpecException ex) {
            Logger.getLogger(Cifrado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new String(bs);

    }

    public static PrivateKey readPrivateKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
         PrivateKey pvKey = null;
        try {
            // Obtener los bytes del archivo donde este guardado la llave publica
            byte[] pvKeyBytes = hexStringToByteArray(configFile.getString("PRIVATEKEY"));
            //
            PKCS8EncodedKeySpec encPvKeySpec = new PKCS8EncodedKeySpec(pvKeyBytes);
            //
            pvKey = KeyFactory.getInstance("RSA").generatePrivate(encPvKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(Cifrado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pvKey;
    }
    
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
    
    public static String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
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
/*
    private static String mensaje = "lauserrig2@gmail.com";
    private static String clave = "abcd1234";

    public String cipherData(String clave, String mensaje) {
        String data = null;
        byte[] ret = null;

        KeySpec keySpec = null;
        SecretKeyFactory secretKeyFactory = null;
        try {

            // Obtenemos el keySpec
            keySpec = new PBEKeySpec(clave.toCharArray(), salt, 65536, 128); // AES-128

            // Obtenemos una instancide de SecretKeyFactory con el algoritmo "PBKDF2WithHmacSHA1"
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            // Generamos la clave
            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();

            // Creamos un SecretKey usando la clave + salt
            SecretKey privateKey = new SecretKeySpec(key, 0, key.length, "AES");

            // Obtenemos una instancide de Cipher con el algoritmos que vamos a usar "AES/CBC/PKCS5Padding"
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");

            // Iniciamos el Cipher en ENCRYPT_MODE y le pasamos la clave privada
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            // Le decimos que cifre (método doFinal())
            byte[] encodedMessage = cipher.doFinal(mensaje.getBytes());

            // Obtenemos el vector CBC del Cipher (método getIV())
            this.iv = cipher.getIV();

            // Guardamos el mensaje codificado: IV (16 bytes) + Mensaje
            byte[] combined = concatArrays(iv, encodedMessage);

            // Escribimos el fichero cifrado 
            File f = new File("C:/Users/2dam/Documents/NetBeansProjects/2DAMG2_SERVER/src/java/cifrado/config.properties");
            f.getParentFile().mkdirs();
            fileWriter(f.getAbsolutePath(), combined);

            // Retornamos el texto cifrado
            data = DatatypeConverter.printHexBinary(combined);
            //ret = new String(encodedMessage);
            //ret = combined;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void main(String[] args) {
        //launch(args);

        Cifrado ejemploAES = new Cifrado();
        String mensajeCifrado = ejemploAES.cipherData(clave, mensaje);
        System.out.println(mensajeCifrado);
    }*/

    private byte[] concatArrays(byte[] array1, byte[] array2) {
        byte[] ret = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, ret, 0, array1.length);
        System.arraycopy(array2, 0, ret, array1.length, array2.length);
        return ret;
    }

    private void fileWriter(String path, byte[] text) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public byte[] descifrarTexto(byte[] data) {
    byte[] decodedMessage = null;
        // Fichero leíd
        KeySpec keySpec = null;
        SecretKeyFactory secretKeyFactory = null;
        try {
            // Creamos un SecretKey usando la clave + salt
            keySpec = new PBEKeySpec(clave.toCharArray(), salt, 65536, 128); // AES-128
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();
            SecretKey privateKey = new SecretKeySpec(key, 0, key.length, "AES");

            // Creamos un Cipher con el algoritmos que vamos a usar
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParam = new IvParameterSpec(Arrays.copyOfRange(data, 0, 16)); // La IV est� aqu�
            cipher.init(Cipher.DECRYPT_MODE, privateKey, ivParam);
            decodedMessage = cipher.doFinal(Arrays.copyOfRange(data, 16, data.length));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decodedMessage;    
    }

   
    

}
