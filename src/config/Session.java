/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

/**
 *
 * @author ph2tn
 */
public class Session {
    public static String userEmail = null;

    public static boolean isLoggedIn() {
        return userEmail != null;
    }

    public static void logout() {
        userEmail = null;
    }
    
    public static String getEmail() {
        return userEmail;
    }
    
}

