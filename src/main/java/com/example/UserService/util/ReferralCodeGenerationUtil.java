package com.example.UserService.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class ReferralCodeGenerationUtil {

    public static String generateReferralCodeFromEmail(String email) {
        try {
            // Create SHA-256 Hash of the email
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(email.getBytes(StandardCharsets.UTF_8));

            // Encode the hash using Base64 and trim it to 8 characters
            String base64EncodedHash = Base64.getUrlEncoder().withoutPadding().encodeToString(hash);

            // Return the first 8 characters of the encoded string as the referral code
            return base64EncodedHash.substring(0, 8).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating referral code", e);
        }
    }
}
