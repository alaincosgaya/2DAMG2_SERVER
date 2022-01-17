package cifrado;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KeyGenerator {

    private void fileWriter(String path, byte[] text) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(text);
        } catch (IOException e) {
            Logger.getLogger(KeyGenerator.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void generate() {
        final String algorithm = "RSA";
        KeyPairGenerator keyPairGenerator;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            File filePublic = new File("./src/files/public.key");
            fileWriter(filePublic.getPath(), keyPair.getPublic().getEncoded());
            File filePrivate = new File("./src/files/private.key");
            fileWriter(filePrivate.getPath(), keyPair.getPrivate().getEncoded());
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(KeyGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
