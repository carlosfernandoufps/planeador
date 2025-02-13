package com.co.planeador.service.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswordUtil {

    private PasswordUtil(){}

    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_=+";
    private static final String ALL_CHARACTERS = UPPER + LOWER + DIGITS + SYMBOLS;
    private static final int PASSWORD_MINIMUM_LENGTH = 6;
    private static final int PASSWORD_GAP_LENGTH = 4;

    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateRandomPassword(){
        int length = PASSWORD_MINIMUM_LENGTH + RANDOM.nextInt(PASSWORD_GAP_LENGTH);
        List<Character> password = new ArrayList<>();

        password.add(UPPER.charAt(RANDOM.nextInt(UPPER.length())));
        password.add(LOWER.charAt(RANDOM.nextInt(LOWER.length())));
        password.add(DIGITS.charAt(RANDOM.nextInt(DIGITS.length())));
        password.add(SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length())));

        for(int i = 4; i < length; i++){
            password.add(ALL_CHARACTERS.charAt(RANDOM.nextInt(ALL_CHARACTERS.length())));
        }
        Collections.shuffle(password, RANDOM);

        StringBuilder passwordStr = new StringBuilder();
        for (char c : password) {
            passwordStr.append(c);
        }

        return passwordStr.toString();
    }

    public static String encodePassword(String password){
        return PASSWORD_ENCODER.encode(password);
    }

    public static boolean passwordMatch(String input, String encodedPassword){
        return PASSWORD_ENCODER.matches(input, encodedPassword);
    }

}
