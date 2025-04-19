package com.prakash;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Hash the password
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // Check password
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
