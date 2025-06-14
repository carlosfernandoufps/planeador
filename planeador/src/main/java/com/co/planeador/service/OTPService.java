package com.co.planeador.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OTPService {

    private static final int EXPIRATION_MINUTES = 60;
    private final Map<String, OtpEntry> otpStore = new ConcurrentHashMap<>();
    private static final SecureRandom RANDOM = new SecureRandom();

    public String generateOtp(String email) {
        String otp = String.format("%06d", RANDOM.nextInt(999999));
        OtpEntry entry = new OtpEntry(email, otp, LocalDateTime.now().plusMinutes(EXPIRATION_MINUTES));
        otpStore.put(email, entry);
        return otp;
    }

    public boolean validateOtp(String email, String inputOtp) {
        OtpEntry entry = otpStore.get(email);
        if (entry == null) return false;

        boolean isValid = entry.code().equals(inputOtp) && entry.expiration().isAfter(LocalDateTime.now());
        if (isValid) {
            otpStore.remove(email);
        }
        return isValid;
    }


    private record OtpEntry(String email, String code, LocalDateTime expiration) {}
}

