package it.marconivr.microblog;

import java.security.SecureRandom;
import java.util.Random;

/**
 * 
 * Salt Generator
 * 
 * @author Alessio Trentin - 5^EI
 * @version 1.0.0 - 12/02/2020
 */
public class SaltGenerator {

    private static final Random RANDOM = new SecureRandom();

    /**
     * static utility class
     */
    private SaltGenerator() {
    }

    /**
     * Returns a random salt to be used to hash a password.
     *
     * @return a 16 bytes random salt
     */
    public static byte[] getNextSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return salt;
    }
}
