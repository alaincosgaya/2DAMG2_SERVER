/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cifrado;

import static com.mchange.v2.c3p0.impl.C3P0Defaults.user;

/**
 *
 * @author 2dam
 */
class PasswordAuthentication {

    String user = "lauserrig2@gmail.com";
    String pass = "abcd*1234";

    public static String getPasswordAuthenticator() {
        return null;

    }

    PasswordAuthentication(String user, String pass) {
    }

}
