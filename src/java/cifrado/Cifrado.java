package cifrado;

import java.util.Random;



/**
 *
 * @author Idoia Ormaetxea y Jonathan Camacho
 */

public class Cifrado {

    private static byte[] salt = "esta es la salt!".getBytes();
    
    public static String generarContra() {
        String mensaje = randomPassword();
        String hash = hashearMensaje(mensaje);
        
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
    }while(generatedString.contains(number.toString()));
    
    return generatedString;
}

    private static String hashearMensaje(String mensaje) {
        Hash hash = new Hash();
        String hasheado = hash.cifrarTexto(mensaje);
        
        return hasheado;
    }
}
